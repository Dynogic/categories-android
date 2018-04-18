package com.disa.expandable;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.animation.RotateAnimation;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.disa.categories.CategoriesAdapterData;
import com.disa.categories.Helper;
import com.disa.categories.IItem;
import com.disa.categories.ItemData;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static android.view.animation.Animation.RELATIVE_TO_SELF;

public class MainActivity extends AppCompatActivity {

    static class Item {
        enum Types { Category, Item }

        final Types type;
        final String name;

        ItemData data;
        List<Item> items;

        public Item(Types type, String name) {
            this.type = type;
            this.name = name;
        }
    }

    private static List<IItem> convertItemToIItem(List<Item> items) {
        ArrayList<IItem> iItems = new ArrayList<>();
        for (Item item : items) {
            List<IItem> subIItems = new ArrayList<>();
            if (item.items != null) {
                for (Item subItem : item.items) {
                    subIItems.add(Helper.createItem(subItem.data));
                }
            }
            IItem iItem = Helper.createCategory(item.data, subIItems);
            iItems.add(iItem);
        }
        return iItems;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_main);

        RecyclerView recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        List<Item> items = new ArrayList<>();

        items.add(new Item(Item.Types.Category, "Telegram"));
        items.add(new Item(Item.Types.Category, "Facebook"));
        items.add(new Item(Item.Types.Category, "Text"));
        Item media = new Item(Item.Types.Category, "Media");
        media.items = Arrays.asList(new Item(Item.Types.Item, "Tenor"), new Item(Item.Types.Item, "Giphy"));
        items.add(media);
        Item deprecated = new Item(Item.Types.Category, "Deprecated");
        deprecated.items = Arrays.asList(new Item(Item.Types.Item, "WhatsApp"));
        items.add(deprecated);

        for (final Item category : items) {
            category.data = new ItemData(new ItemData.IDelegates() {
                private TextView name;
                private ImageView arrow;
                private ImageView icon;
                private CheckBox check;
                private RelativeLayout button;
                private TextView description;

                @Override
                public void construct(View itemView) {
                    name = itemView.findViewById(R.id.list_item_tag_group_name);
                    arrow = itemView.findViewById(R.id.list_item_tag_group_arrow);
                    icon = itemView.findViewById(R.id.list_item_tag_group_icon);
                    check = itemView.findViewById(R.id.list_item_tag_group_check_box);
                    button = itemView.findViewById(R.id.list_item_tag_group_button);
                    description = itemView.findViewById(R.id.list_item_tag_group_description);
                }

                @Override
                public void bind(IItem item, final ItemData.IDispatchClick dispatchClick) {
                    name.setText(category.name);
                    if (category.items != null) {
                        button.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View view) {
                                dispatchClick.dispatchClick();
                            }
                        });
                    }
                }

                @Override
                public void expand() {
                    animateExpand();
                }

                @Override
                public void contract() {
                    animateCollapse();
                }

                private void animateExpand() {
                    RotateAnimation rotate =
                            new RotateAnimation(360, 180, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(300);
                    rotate.setFillAfter(true);
                    arrow.setAnimation(rotate);
                }

                private void animateCollapse() {
                    RotateAnimation rotate =
                            new RotateAnimation(180, 360, RELATIVE_TO_SELF, 0.5f, RELATIVE_TO_SELF, 0.5f);
                    rotate.setDuration(300);
                    rotate.setFillAfter(true);
                    arrow.setAnimation(rotate);
                }
            });

            if (category.items != null) {
                for (final Item item : category.items) {
                    item.data = new ItemData(new ItemData.IDelegates() {
                        private CheckBox childCheckBox;
                        private TextView childName;
                        private RelativeLayout childButton;
                        private ImageView childIcon;
                        private TextView childDescription;

                        @Override
                        public void construct(View itemView) {
                            childCheckBox = itemView.findViewById(R.id.list_item_tag_check_box);
                            childName = itemView.findViewById(R.id.list_item_tag_name);
                            childButton = itemView.findViewById(R.id.list_item_tag_button);
                            childIcon = itemView.findViewById(R.id.list_item_tag_icon);
                            childDescription = itemView.findViewById(R.id.list_item_tag_description);
                        }

                        @Override
                        public void bind(IItem iItem, ItemData.IDispatchClick dispatchClick) {
                            childName.setText(item.name);
                        }

                        @Override
                        public void expand() {
                        }

                        @Override
                        public void contract() {
                        }
                    });
                }
            }
        }

        CategoriesAdapterData adapterData = new CategoriesAdapterData(R.layout.list_item_tag_group, R.layout.list_item_tag);
        Helper.Adapter adapter = new Helper.Adapter(convertItemToIItem(items), adapterData);
        adapter.set(recyclerView);
    }
}
