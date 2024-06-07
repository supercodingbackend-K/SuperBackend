package com.github.superconding.repository.Items;

import java.util.List;

public interface ElectronicStoreItemRepository {
    List<ItemEntity> findAllItems();

    Integer saveItem(ItemEntity itemEntity);

    ItemEntity updateItemEntity(Integer idInt, ItemEntity itemEntity);

    void deleteItem(int idInt);

    ItemEntity findItemById(Integer idInt);

    void deleteItem(Integer idInt);

    void updateItemStock(Integer itemId, Integer stock);
}
