package com.maxron.androidarchitecturepractice.view;

import android.arch.lifecycle.Observer;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;

import com.maxron.androidarchitecturepractice.R;
import com.maxron.androidarchitecturepractice.RepoRecyclerViewAdapter;
import com.maxron.androidarchitecturepractice.di.MainViewModelComponent;
import com.maxron.androidarchitecturepractice.model.RepoItemModel;
import com.maxron.androidarchitecturepractice.viewmodel.MainViewModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();

    private static final String USER = "maxron";

    @BindView(R.id.repos_recycler_view)
    RecyclerView reposRecyclerView;

    private RepoRecyclerViewAdapter adapter;
    MainViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecycleView();
        subscribeViewModel();
    }

    private void initRecycleView() {
        adapter = new RepoRecyclerViewAdapter();
        reposRecyclerView.setAdapter(adapter);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    private void subscribeViewModel() {
        viewModel = MainViewModelComponent.provideViewModel(this);
        viewModel.getRepoItemData().observe(this, new Observer<List<RepoItemModel>>() {
            @Override
            public void onChanged(@Nullable List<RepoItemModel> repoItemModels) {
                adapter.updateItems(repoItemModels);
            }
        });
    }

    @Override
    protected void onStart() {
        super.onStart();
        viewModel.fetchRepos(USER);
    }

}
