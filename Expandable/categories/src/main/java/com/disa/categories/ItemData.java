package com.disa.categories;

import android.view.View;

public class ItemData {

    final IDelegates delegates;

    public ItemData(IDelegates delegates) {
        this.delegates = delegates;
    }

    public interface IDelegates {
        void construct(View view);
        void bind(IItem item, IDispatchClick dispatchClick);
        void expand();
        void contract();
    }

    public interface IDispatchClick {
        void dispatchClick();
    }
}
