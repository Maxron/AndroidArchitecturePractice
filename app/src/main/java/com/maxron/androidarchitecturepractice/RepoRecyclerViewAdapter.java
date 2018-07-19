package com.maxron.androidarchitecturepractice;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.maxron.androidarchitecturepractice.model.RepoItemModel;

import java.util.LinkedList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class RepoRecyclerViewAdapter extends RecyclerView.Adapter<RepoRecyclerViewAdapter.RepoViewHolder> {

    private static final String TAG = "RepoRecyclerViewAdapter";
    private List<RepoItemModel> repoItemModels = new LinkedList<>();

    @NonNull
    @Override
    public RepoViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        final View itemView = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_repolist, parent, false);
        return new RepoViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull RepoViewHolder holder, int position) {
        holder.setItem(repoItemModels.get(position));
    }

    @Override
    public int getItemCount() {
        return repoItemModels.size();
    }

    public void updateItems(List<RepoItemModel> repoItemModels) {
        this.repoItemModels = repoItemModels;
        notifyDataSetChanged();
    }

    public class RepoViewHolder extends RecyclerView.ViewHolder {

        @BindView(R.id.repo_id)
        TextView id;

        @BindView(R.id.repo_name)
        TextView name;

        public RepoViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
        }

        public void setItem(RepoItemModel repoItemModel) {
            id.setText(Long.toString(repoItemModel.getId()));
            name.setText(repoItemModel.getName());
        }
    }
}
