package com.disa.categories;

class Item implements IItem {

    private final ItemData data;

    public Item(ItemData data) {
        this.data = data;
    }

    @Override
    public ItemData getData() {
        return data;
    }
}
