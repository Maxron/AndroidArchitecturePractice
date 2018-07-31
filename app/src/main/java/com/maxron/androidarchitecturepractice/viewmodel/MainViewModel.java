package com.maxron.androidarchitecturepractice.viewmodel;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.maxron.androidarchitecturepractice.mapper.RepoItemModelMapper;
import com.maxron.androidarchitecturepractice.model.RepoItemModel;
import com.maxron.domain.interactor.ListUserRepositoriesUseCase;
import com.maxron.domain.model.RepoInfo;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;

public class MainViewModel extends AndroidViewModel {

    private static final String TAG = "MainViewModel";

    private ListUserRepositoriesUseCase listUserRepositoriesUseCase;

    private MutableLiveData<List<RepoItemModel>> repoItemData = new MutableLiveData<>();

    public MainViewModel(@NonNull Application application) {
        super(application);
    }

    public void setListUserRepositoriesUseCase(ListUserRepositoriesUseCase listUserRepositoriesUseCase) {
        this.listUserRepositoriesUseCase = listUserRepositoriesUseCase;
    }

    public LiveData<List<RepoItemModel>> getRepoItemData() {
        return repoItemData;
    }

    public void fetchRepos(String user) {
        listUserRepositoriesUseCase.execute(user)
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .map(new Function<List<RepoInfo>, List<RepoItemModel>>() {
                    @Override
                    public List<RepoItemModel> apply(List<RepoInfo> repoInfos) throws Exception {
                        List<RepoItemModel> itemModels = new ArrayList<>();
                        for (RepoInfo repoInfo : repoInfos) {
                            itemModels.add(new RepoItemModelMapper().from(repoInfo));
                        }

                        return itemModels;
                    }
                })
                .subscribe(new SingleObserver<List<RepoItemModel>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @Override
                    public void onSuccess(List<RepoItemModel> repoItemModels) {
                        Log.d(TAG, "onSuccess: ");
                        repoItemData.setValue(repoItemModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }
                });
    }

}
