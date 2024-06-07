package com.github.superconding.web.controller;

import com.github.superconding.service.ElectronicStoreItemService;
import com.github.superconding.web.controller.dto.BuyOrder;
import com.github.superconding.web.controller.dto.Item;
import com.github.superconding.web.controller.dto.ItemBody;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter

@RequiredArgsConstructor
@RestController
@Log4j
@RequestMapping("/api")
public class ElectronicStoreController {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final ElectronicStoreItemService electronicStoreItemService;



    private static int serialItemId = 1;
//    private List<Item> items = new ArrayList<>(Arrays.asList(
//            new Item(String.valueOf(serialItemId++),"Apple iPhone 15 Pro Max","Smartphone",1890000,"A17 Bionic", "256GB"),
//            new Item(String.valueOf(serialItemId++),"Samsung Galaxy S24 Ultra","Smartphone",1790000,"Exynos 2400", "256GB"),
//            new Item(String.valueOf(serialItemId++),"Google Pixel 7 Pro","Smartphone",1590000,"Google Tensor", "512GB"),
//            new Item(String.valueOf(serialItemId++),"Apple MacBook 16 Pro Max","Laptop",2590000,"M3 Max", "512GB"),
//            new Item(String.valueOf(serialItemId++),"Sony Alpha7 III","Smartphone",1890000,"BIONZ X", "1TB"),
//            new Item(String.valueOf(serialItemId++),"Microsoft Xbox Series X","Gaming Console",9990000,"Custom AMD Zen 2", "2TB SSD")));


    @GetMapping("/items")

    public List<Item> findAllItem() {
        logger.info("GET/item 요청이 들어왔습니다.");
        List<Item> items = electronicStoreItemService.findAllItem();
        return items;

    }

    @PostMapping("/items")
    public String registerItem(@RequestBody ItemBody itemBody){
        Integer itemId = electronicStoreItemService.saveItem(itemBody);

        return "ID: " + itemId;
    }

    @GetMapping("/items/{id}")
    public Item findItemByPathId(@PathVariable String id){


        return electronicStoreItemService.findItemById(id);
    }

    @GetMapping("/items-query")
    public Item findItmeByQueryID(@RequestParam ("id")String id){
//        Integer idInt = Integer.parseInt(id);
//        ItemEntity itemEntity = electronicStoreItemRepository.findItemById(idInt);
//        Item item = new Item(itemEntity);
//        return item;
        return electronicStoreItemService.findItemById(id);
    }

    @GetMapping("/items-queries")
    public List<Item> findItmeByQueryIds(@RequestParam ("id")List<String> ids){
        return electronicStoreItemService.findItemsByIds(ids);
    }

    @DeleteMapping("/items/{id}")
    public String deleteItemByPathId(@PathVariable String id){
        electronicStoreItemService.deleteItem(id);
        return "Object with id = " + id + " has been deleted";
    }

    @PutMapping("/items/{id}")
    public Item updateItem(@PathVariable String id,@RequestBody ItemBody itemBody){
           return electronicStoreItemService.updateItem(id, itemBody);

    }

    @PostMapping("/items/buy")
    public String buyItem(@RequestBody BuyOrder buyOrder) {
        Integer orderItemNums = electronicStoreItemService.buyItems(buyOrder);
        return "요청하신 Item 중 " + orderItemNums + "개를 구매 하였습니다." ;
    }

}
