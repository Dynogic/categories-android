package com.disa.categories;

import android.view.View;

import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;
import com.thoughtbot.expandablerecyclerview.viewholders.GroupViewHolder;

class CategoryHolder extends GroupViewHolder {

    private final View itemView;

    private ItemData data;

    public CategoryHolder(View itemView) {
        super(itemView);
        this.itemView = itemView;
    }

    public void bind(final ExpandableGroup expandableGroup) {
        if (expandableGroup instanceof Category) {
            Category category = (Category)expandableGroup;
            data = category.getData();
            data.delegates.construct(itemView);
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
