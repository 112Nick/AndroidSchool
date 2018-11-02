package ru.mail.park.tfshw3;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class WorkerListAdapter extends RecyclerView.Adapter<WorkerListAdapter.WorkerListViewHolder> implements ItemTouchHelperAdapter{

    private final LayoutInflater layoutInflater;
    private final List<Worker> data;
//    private final OnItemClickListener<Worker> onItemClickListener;

//    public WorkerListAdapter(Context context, OnItemClickListener<Worker> onItemClickListener) {
    public WorkerListAdapter(Context context) {
        layoutInflater = LayoutInflater.from(context);
        this.data = new ArrayList<>();
//        this.onItemClickListener = onItemClickListener;

    }

    @NonNull
    @Override
    public WorkerListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new WorkerListViewHolder(layoutInflater.inflate(R.layout.item, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull WorkerListViewHolder holder, int position) {
//        holder.bind(data.get(position), onItemClickListener);
        holder.bind(data.get(position));

    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    @Override
    public void onItemDismiss(int position) {
        data.remove(position);
        notifyItemRemoved(position);
    }

    @Override
    public void onItemMove(int fromPosition, int toPosition) { //TODO
        if (fromPosition < toPosition) {
            for (int i = fromPosition; i < toPosition; i++) {
                Collections.swap(data, i, i + 1);
            }
        } else {
            for (int i = fromPosition; i > toPosition; i--) {
                Collections.swap(data, i, i - 1);
            }
        }
        notifyItemMoved(fromPosition, toPosition);
//        return true;
    }

    public void add(Worker newData) {
        data.add(0, newData);
        notifyItemInserted(0);
    }

//    public void addToPosition(Worker newData) {
//        data.add(data.indexOf(newData), newData);
//        notifyItemInserted(data.indexOf(newData));
//    }

    public void remove(Worker w) {
        notifyItemRemoved(data.indexOf(w));
        data.remove(w);
    }





    final static class WorkerListViewHolder extends RecyclerView.ViewHolder {
        private final TextView name;
        private final TextView age;
        private final TextView position;
        private final ImageView preview;


        WorkerListViewHolder(View itemView) {
            super(itemView);
            name = itemView.findViewById(R.id.name);
            age = itemView.findViewById(R.id.age);
            position = itemView.findViewById(R.id.position);
            preview = itemView.findViewById(R.id.preview);
        }

//        void bind(ResourceItem resourceItem, OnItemClickListener<ResourceItem> onItemClickListener) {
        void bind(Worker worker) {
                preview.setImageResource(worker.getPhoto());
                name.setText(worker.getName());
                age.setText(worker.getAge());
                position.setText(worker.getPosition());


        }
    }
}