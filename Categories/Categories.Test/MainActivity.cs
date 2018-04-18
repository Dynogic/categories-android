using Android.App;
using Android.Widget;
using Android.OS;
using Com.Disa.Categories;
using System.Collections.Generic;
using Android.Support.V7.Widget;
using Android.Views;
using Android.Views.Animations;
using System;

namespace Categories.Test
{
    // Port of Java app code. C# naming conventions have not been applied to ease porting when Java app code is updated.
    [Activity(Label = "Categories.Test", MainLauncher = true, Theme = "@style/Theme.AppCompat.Light.NoActionBar")]
    public class MainActivity : Activity
    {
        private class Item
        {
            public enum Types { Category, Item }

            public readonly Types type;
            public readonly string name;

            public ItemData data;
            public List<Item> items;

            public Item(Types type, string name)
            {
                this.type = type;
                this.name = name;
            }
        }

        private static List<IItem> convertItemToIItem(List<Item> items)
        {
            List<IItem> iItems = new List<IItem>();
            foreach (Item item in items)
            {
                List<IItem> subIItems = new List<IItem>();
                if (item.items != null)
                {
                    foreach (Item subItem in item.items)
                    {
                        subIItems.Add(Helper.CreateItem(subItem.data));
                    }
                }
                IItem iItem = Helper.CreateCategory(item.data, subIItems);
                iItems.Add(iItem);
            }
            return iItems;
        }

        protected override void OnCreate(Bundle savedInstanceState)
        {
            base.OnCreate(savedInstanceState);

            SetContentView(Resource.Layout.Main);

            RecyclerView recyclerView = FindViewById<RecyclerView>(Resource.Id.recycler_view);
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.SetLayoutManager(layoutManager);

            List<Item> items = new List<Item>();

            items.Add(new Item(Item.Types.Category, "Telegram"));
            Item media = new Item(Item.Types.Category, "Media");
            media.items = new List<Item>() { new Item(Item.Types.Item, "Tenor"), new Item(Item.Types.Item, "Giphy") };
            items.Add(media);
            Item deprecated = new Item(Item.Types.Category, "Deprecated");
            deprecated.items = new List<Item>() { new Item(Item.Types.Item, "WhatsApp") };
            items.Add(deprecated);

            foreach (Item category in items)
            {
                category.data = new ItemData(new CategoryDelegates(category));
                if (category.items != null)
                {
                    foreach (Item item in category.items)
                    {
                        item.data = new ItemData(new ItemDelegates(item));
                    }
                }
            }

            CategoriesAdapterData adapterData = new CategoriesAdapterData(Resource.Layout.list_item_tag_group, Resource.Layout.list_item_tag);
            Helper.Adapter adapter = new Helper.Adapter(convertItemToIItem(items), adapterData);
            adapter.Set(recyclerView);
        }

        private class OnClick : Java.Lang.Object, View.IOnClickListener
        {
            private readonly Action onClick;

            public OnClick(Action onClick)
            {
                this.onClick = onClick;
            }

            void View.IOnClickListener.OnClick(View v)
            {
                onClick();
            }
        }

        private class CategoryDelegates : Java.Lang.Object, ItemData.IDelegates
        {
            private readonly Item category;

            private TextView name;
            private ImageView arrow;
            private ImageView icon;
            private CheckBox check;
            private RelativeLayout button;
            private TextView description;

            public CategoryDelegates(Item category)
            {
                this.category = category;
            }

            public void Bind(IItem item, ItemData.IDispatchClick dispatchClick)
            {
                name.Text = category.name;
                if (category.items != null)
                {
                    button.SetOnClickListener(new OnClick(() =>
                    {
                        dispatchClick.DispatchClick();
                    }));
                };
            }

            public void Construct(View p0)
            {
                name = p0.FindViewById<TextView>(Resource.Id.list_item_tag_group_name);
                arrow = p0.FindViewById<ImageView>(Resource.Id.list_item_tag_group_arrow);
                icon = p0.FindViewById<ImageView>(Resource.Id.list_item_tag_group_icon);
                check = p0.FindViewById<CheckBox>(Resource.Id.list_item_tag_group_check_box);
                button = p0.FindViewById<RelativeLayout>(Resource.Id.list_item_tag_group_button);
                description = p0.FindViewById<TextView>(Resource.Id.list_item_tag_group_description);
            }

            public void Contract()
            {
                animateCollapse();
            }

            public void Expand()
            {
                animateExpand();
            }

            private void animateExpand()
            {
                RotateAnimation rotate =
                        new RotateAnimation(360, 180, Dimension.RelativeToSelf, 0.5f, Dimension.RelativeToSelf, 0.5f);
                rotate.Duration = 300;
                rotate.FillAfter = true;
                arrow.Animation = rotate;
            }

            private void animateCollapse()
            {
                RotateAnimation rotate =
                        new RotateAnimation(180, 360, Dimension.RelativeToSelf, 0.5f, Dimension.RelativeToSelf, 0.5f);
                rotate.Duration = 300;
                rotate.FillAfter = true;
                arrow.Animation = rotate;
            }
        }

        private class ItemDelegates : Java.Lang.Object, ItemData.IDelegates
        {
            private readonly Item item;

            private CheckBox childCheckBox;
            private TextView childName;
            private RelativeLayout childButton;
            private ImageView childIcon;
            private TextView childDescription;

            public ItemDelegates(Item item)
            {
                this.item = item;
            }

            public void Bind(IItem iItem, ItemData.IDispatchClick p1)
            {
                childName.Text = item.name;
            }

            public void Construct(View itemView)
            {
                childCheckBox = itemView.FindViewById<CheckBox>(Resource.Id.list_item_tag_check_box);
                childName = itemView.FindViewById<TextView>(Resource.Id.list_item_tag_name);
                childButton = itemView.FindViewById<RelativeLayout>(Resource.Id.list_item_tag_button);
                childIcon = itemView.FindViewById<ImageView>(Resource.Id.list_item_tag_icon);
                childDescription = itemView.FindViewById<TextView>(Resource.Id.list_item_tag_description);
            }

            public void Contract()
            {
            }

            public void Expand()
            {
            }
        }
    }
}

