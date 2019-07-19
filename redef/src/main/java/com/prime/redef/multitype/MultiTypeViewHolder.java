package com.prime.redef.multitype;

import androidx.recyclerview.widget.RecyclerView;
import android.view.View;

public abstract class MultiTypeViewHolder<T extends IMultiTypeItem> extends RecyclerView.ViewHolder {

    public MultiTypeViewHolder(View itemView) {
        super(itemView);
    }

    public abstract void bindType(T item);
}
