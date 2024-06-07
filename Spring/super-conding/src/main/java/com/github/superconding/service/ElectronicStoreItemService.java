package com.github.superconding.service;

import com.github.superconding.repository.Items.ElectronicStoreItemRepository;
import com.github.superconding.repository.Items.ItemEntity;
import com.github.superconding.repository.storeSales.StoreSales;
import com.github.superconding.repository.storeSales.StoreSalesRepository;
import com.github.superconding.service.mapper.ItemMapper;
import com.github.superconding.web.controller.dto.BuyOrder;
import com.github.superconding.web.controller.dto.Item;
import com.github.superconding.web.controller.dto.ItemBody;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class ElectronicStoreItemService {
    private final ElectronicStoreItemRepository electronicStoreItemRepository;
    private final StoreSalesRepository storeSalesRepository;

    public List<Item> findAllItem() {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream().map(ItemMapper.INSTANCE::itemEntityToItem).collect(Collectors.toList());
    }

    public Integer saveItem(ItemBody itemBody) {
        ItemEntity itemEntity = new ItemEntity(null, itemBody.getName(), itemBody.getType(),
                itemBody.getPrice(), itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());

        return electronicStoreItemRepository.saveItem(itemEntity);
    }

    public Item findItemById(String id) {
        Integer idInt = Integer.parseInt(id);
        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(idInt);
        Item item = new Item(itemEntity);
        return item;
    }

    public List<Item> findItemsByIds(List<String> ids) {
        List<ItemEntity> itemEntities = electronicStoreItemRepository.findAllItems();
        return itemEntities.stream()
                .map(Item::new)
                .filter((item -> ids.contains(item.getId())))
                .collect(Collectors.toList());
    }

    public void deleteItem(String id) {
        Integer idInt = Integer.parseInt(id);
        electronicStoreItemRepository.deleteItem(idInt);
    }

    public Item updateItem(String id, ItemBody itemBody) {
        Integer idInt = Integer.valueOf(id);
        ItemEntity itemEntity = new ItemEntity(idInt, itemBody.getName(),
                itemBody.getType(), itemBody.getPrice(),
                itemBody.getSpec().getCpu(), itemBody.getSpec().getCapacity());
        ItemEntity itemEntityUpdated = electronicStoreItemRepository.updateItemEntity(idInt, itemEntity);

        return ItemMapper.INSTANCE.itemEntityToItem(itemEntityUpdated);
    }

    @Transactional(transactionManager = "tm1")
    public Integer buyItems(BuyOrder buyOrder) {
        Integer itemId = buyOrder.getItemId();
        Integer itemNums = buyOrder.getItemNums();

        System.out.println("itemId: " + itemId);
        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(itemId);

        if (itemEntity.getStoreId() == null ) throw new RuntimeException("매장을 찾을 수 없습니다.");
        if (itemEntity.getStock() <= 0 ) throw new RuntimeException("상품의 재고가 없습니다.");

        Integer sucessBuyItemNums;
        if ( itemNums >= itemEntity.getStock() ) sucessBuyItemNums = itemEntity.getStock();
        else sucessBuyItemNums = itemNums;

        Integer totalPrice = sucessBuyItemNums * itemEntity.getPrice();

        electronicStoreItemRepository.updateItemStock(itemId, itemEntity.getStock() - sucessBuyItemNums);

        StoreSales storeSales = storeSalesRepository.findStoreSalesById(itemEntity.getStoreId());
        storeSalesRepository.updateSalesAmount(itemEntity.getStoreId(), storeSales.getAmount() + totalPrice);

        return sucessBuyItemNums;
    }
}
