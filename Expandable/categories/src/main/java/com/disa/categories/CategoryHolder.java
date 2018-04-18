package com.disa.categories;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

class CategoryHolder extends GroupViewHolder {

    private final View itemView;

    private ItemData data;
    private boolean hasCalledConstruct;

    public CategoryHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(final ExpandableGroup expandableGroup) {
        if (expandableGroup instanceof Category) {
            final Category category = (Category)expandableGroup;
            if (!hasCalledConstruct) {
                data = category.getData();
                data.delegates.construct(itemView);
                hasCalledConstruct = true;
            }
            data.delegates.bind(category, new ItemData.IDispatchClick() {
                @Override
                public void dispatchClick() {
                    CategoryHolder.super.dispatchClick();
                }
            });
        }
    }

    @Override
    public void expand() {
        data.delegates.expand();
    }

    @Override
    public void collapse() {
        data.delegates.contract();
    }
}
