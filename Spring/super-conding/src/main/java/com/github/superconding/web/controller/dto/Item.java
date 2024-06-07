package com.github.superconding.web.controller.dto;

import com.github.superconding.repository.Items.ItemEntity;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.Objects;

@Getter
@NoArgsConstructor
public class Item {
    private String id;
    private String name;
    private String type;
    private Integer price;

    private Spec spec;
 }



