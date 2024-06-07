package com.github.superconding.service.mapper;

import com.github.superconding.repository.Items.ItemEntity;
import com.github.superconding.web.controller.dto.Item;
import com.github.superconding.web.controller.dto.ItemBody;
import com.github.superconding.web.controller.dto.Item;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;

@Mapper
public interface ItemMapper {

    ItemMapper INSTANCE = Mappers.getMapper(ItemMapper.class);

    @Mapping(target = "spec.cpu", source = "cpu")
    @Mapping(target = "spec.capacity", source = "capacity")
    Item itemEntityToItem(ItemEntity itemEntity);
}
