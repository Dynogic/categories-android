package com.disa.categories;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.thoughtbot.expandablerecyclerview.ExpandableRecyclerViewAdapter;
import com.thoughtbot.expandablerecyclerview.models.ExpandableGroup;

import java.util.List;

class CategoriesAdapter extends
        ExpandableRecyclerViewAdapter<CategoryHolder, ItemHolder> {

    private final CategoriesAdapterData data;

    public CategoriesAdapter(List<Category> groups, CategoriesAdapterData data) {
        super(groups);
        this.data = data;
    }

    @Override
    public CategoryHolder onCreateGroupViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(data.categoryLayout, parent, false);
        return new CategoryHolder(view);
    }

    @Override
    public ItemHolder onCreateChildViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(data.itemLayout, parent, false);
        return new ItemHolder(view);
    }

    @Override
    public void onBindChildViewHolder(ItemHolder holder, int flatPosition, ExpandableGroup group, int childIndex) {
        final Item tag = (Item) group.getItems().get(childIndex);
        holder.bind(tag);
    }

    @Override
    public void onBindGroupViewHolder(CategoryHolder holder, int flatPosition, ExpandableGroup group) {
        holder.bind(group);
    }
}

