package com.disa.categories;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.viewholders.ChildViewHolder;

class ItemHolder extends ChildViewHolder {

    private final View itemView;

    private ItemData data;
    private boolean hasCalledConstruct;

    public ItemHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(final Item item) {
        if (!hasCalledConstruct) {
            data = item.getData();
            data.delegates.construct(itemView);
            hasCalledConstruct = true;
        }
        data.delegates.bind(item, null);
    }
}
