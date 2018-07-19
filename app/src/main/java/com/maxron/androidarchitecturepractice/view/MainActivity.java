package com.maxron.androidarchitecturepractice.view;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.maxron.androidarchitecturepractice.R;
import com.maxron.androidarchitecturepractice.RepoRecyclerViewAdapter;
import com.maxron.androidarchitecturepractice.data.UserRepositoryImpl;
import com.maxron.androidarchitecturepractice.data.mapper.RepoModelMapperImpl;
import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.androidarchitecturepractice.mapper.RepoItemModelMapper;
import com.maxron.androidarchitecturepractice.model.RepoItemModel;
import com.maxron.domain.interactor.ListUserRepositoriesUseCase;
import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;
import io.reactivex.SingleObserver;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.functions.Function;
import io.reactivex.schedulers.Schedulers;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String USER = "maxron";
    @BindView(R.id.repos_recycler_view)
    RecyclerView reposRecyclerView;

    private RepoRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);

        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new RepoRecyclerViewAdapter();
        reposRecyclerView.setAdapter(adapter);
        reposRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    @Override
    protected void onStart() {
        super.onStart();
        initUseCase();
    }

    private void initUseCase() {
        UserRepository repository =
                new UserRepositoryImpl(getGithubService(), new RepoModelMapperImpl());
        ListUserRepositoriesUseCase listUserRepositoriesUseCase =
                new ListUserRepositoriesUseCase(repository);

        listUserRepositoriesUseCase.execute(USER)
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
                        adapter.updateItems(repoItemModels);
                    }

                    @Override
                    public void onError(Throwable e) {
                        Log.d(TAG, "onError: " + e);
                    }
                });
    }

    private GithubServiceApi getGithubService() {
        return getRetrofit().create(GithubServiceApi.class);
    }

    public Retrofit getRetrofit() {
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);

        OkHttpClient httpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .client(httpClient)
                .addConverterFactory(GsonConverterFactory.create())
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .build();

        return retrofit;
    }
}
