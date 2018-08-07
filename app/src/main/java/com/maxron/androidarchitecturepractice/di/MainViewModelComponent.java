package com.maxron.androidarchitecturepractice.di;

import android.app.Application;
import android.arch.lifecycle.ViewModelProviders;
import android.support.v4.app.FragmentActivity;

import com.maxron.androidarchitecturepractice.ApiClientProvider;
import com.maxron.androidarchitecturepractice.ApiConstant;
import com.maxron.androidarchitecturepractice.data.UserRepositoryImpl;
import com.maxron.androidarchitecturepractice.data.local.AppDatabase;
import com.maxron.androidarchitecturepractice.data.mapper.RepoModelMapperImpl;
import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.androidarchitecturepractice.viewmodel.MainViewModel;
import com.maxron.domain.interactor.ListUserRepositoriesUseCase;

import retrofit2.Retrofit;

public class MainViewModelComponent {

    public static MainViewModel provideViewModel(FragmentActivity activity) {
        MainViewModel viewModel = ViewModelProviders.of(activity).get(MainViewModel.class);
        viewModel.setListUserRepositoriesUseCase(
                new MainViewModelComponent().provideListUserRepositoriesUseCase(activity.getApplication())
        );
        return viewModel;
    }

    public MainViewModelComponent() {
    }

    private ListUserRepositoriesUseCase provideListUserRepositoriesUseCase(Application applicationContext) {
        UserRepositoryImpl repository = new UserRepositoryImpl(
                getRetrofit().create(GithubServiceApi.class),
                new RepoModelMapperImpl(),
                provideDatabase(applicationContext).repoDao());
        return  new ListUserRepositoriesUseCase(repository);
    }

    private AppDatabase provideDatabase(Application applicationContext) {
        return AppDatabase.getInstance(applicationContext);
    }

    private Retrofit getRetrofit() {
        return new ApiClientProvider.Builder(ApiConstant.BASE_URL)
                .withLogger()
                .build()
                .provide();
    }
}
