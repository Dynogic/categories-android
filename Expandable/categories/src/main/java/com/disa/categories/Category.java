package com.disa.categories;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

class Category extends ExpandableGroup implements IItem {

    private final ItemData data;

    public Category(ItemData data, List<Item> items) {
        super(null, items);
        this.data = data;
    }

    @Override
    public ItemData getData() {
        return data;
    }
}
