package com.disa.categories;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

class ItemHolder extends ChildViewHolder {

    private final View itemView;

    public ItemHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(final Item item) {
        ItemData data = item.getData();
        data.delegates.construct(itemView);
        data.delegates.bind(item, null);
    }
}
