package com.disa.categories;

import android.support.v7.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    public static IItem createItem(ItemData data) {
        return new Item(data);
    }

    public static IItem createCategory(ItemData data, List<IItem> items) {
        List<Item> itemsCasted = new ArrayList<>();
        for (IItem item : items) {
            itemsCasted.add((Item)item);
        }
        return new Category(data, itemsCasted);
    }

    public static boolean isCategory(IItem item)
    {
        return item instanceof Category;
    }

    public static class Adapter {

        private final CategoriesAdapter adapter;

        public Adapter(List<IItem> categories, CategoriesAdapterData data) {
            List<Category> categoriesCasted = new ArrayList<>();
            for (IItem item : categories) {
                categoriesCasted.add((Category)item);
            }
            this.adapter = new CategoriesAdapter(categoriesCasted, data);
        }

        public void set(RecyclerView recyclerView)
        {
            recyclerView.setAdapter(adapter);
        }
    }
}
