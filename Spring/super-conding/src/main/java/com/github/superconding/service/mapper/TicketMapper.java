package com.github.superconding.service.mapper;

import com.github.superconding.repository.airlineTicket.AirlineTicket;
import com.github.superconding.web.controller.dto.airline.Ticket;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;
import org.springframework.web.bind.annotation.Mapping;


@Mapper
public interface ItemMapper {

    // 싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    // 메소드
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);

    @Mapping(target = "cpu", source = "itemBody.spec.cpu")
    @Mapping(target = "capacity", source = "itemBody.spec.capacity")
    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "stock", expression = "java(0)")
    ItemEntity idAndItemBodyToItemEntity(Integer id, ItemBody itemBody);
}

@Mapper
public interface TicketMapper {

    // 싱글톤
    ItemMapper INSTANCE = Mappers.getMapper(TicketMapper.class);

    // 메소드
    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);

    @Mapping(target = "cpu", source = "itemBody.spec.cpu")
    @Mapping(target = "capacity", source = "itemBody.spec.capacity")
    @Mapping(target = "storeId", ignore = true)
    @Mapping(target = "stock", expression = "java(0)")
    ItemEntity idAndItemBodyToItemEntity(Integer id, ItemBody itemBody);
}
