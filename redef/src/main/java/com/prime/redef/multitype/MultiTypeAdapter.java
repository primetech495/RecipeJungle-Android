package com.prime.redef.multitype;

import android.content.Context;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import java.util.ArrayList;

public abstract class MultiTypeAdapter extends RecyclerView.Adapter<MultiTypeViewHolder>  {
    private Context context;
    private ArrayList<IMultiTypeItem> dataSet;

    public MultiTypeAdapter(@NonNull Context context, @NonNull ArrayList<IMultiTypeItem> dataSet) {
        this.context = context;
        this.dataSet = dataSet;
    }

    @NonNull
    protected abstract View createView(@NonNull LayoutInflater inflater, ViewGroup parent, int viewType);

    @NonNull
    protected abstract MultiTypeViewHolder createViewHolder(@NonNull View itemView, int viewType);

    @NonNull
    @Override
    public final MultiTypeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = createView(LayoutInflater.from(context), parent, viewType);
        return createViewHolder(view, viewType);
    }

    @Override
    public final void onBindViewHolder(@NonNull MultiTypeViewHolder holder, int position) {
        // noinspection unchecked
        holder.bindType(dataSet.get(position));
    }

    @Override
    public final int getItemViewType(int position) {
        return dataSet.get(position).getListItemType();
    }

    @Override
    public final int getItemCount() {
        return dataSet.size();
    }

    public final ArrayList<IMultiTypeItem> getDataSet() {
        return dataSet;
    }
}
