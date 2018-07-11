package com.maxron.androidarchitecturepractice;

import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import com.maxron.data.remote.GitHubServiceApi;
import com.maxron.data.remote.model.Repo;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
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
    @BindView(R.id.button)
    Button button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
    }

    @OnClick(R.id.button)
    public void onSend(View view) {
        Log.d(TAG, "onSend: ");
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

        GitHubServiceApi service = retrofit.create(GitHubServiceApi.class);
        String user = "maxron";
        service.listRepo(user)
                .map(new Function<List<Repo>, List<RepoSimple>>() {
                    @Override
                    public List<RepoSimple> apply(List<Repo> repos) throws Exception {
                        List<RepoSimple> newRepos = new ArrayList<>();

                        for (Repo repo : repos) {
                            RepoSimple simple = new RepoSimple(repo.getName());
                            newRepos.add(simple);
                        }

                        return newRepos;
                    }
                })
                .subscribeOn(Schedulers.newThread())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new SingleObserver<List<RepoSimple>>() {
                    @Override
                    public void onSubscribe(Disposable d) {
                        Log.d(TAG, "onSubscribe: ");
                    }

                    @RequiresApi(api = Build.VERSION_CODES.N)
                    @Override
                    public void onSuccess(List<RepoSimple> repoSimples) {
                        Log.d(TAG, "onSuccess: ");
                        repoSimples.forEach(new Consumer<RepoSimple>() {
                            @Override
                            public void accept(RepoSimple repoSimple) {
                                Log.d(TAG, "accept: repo name:" + repoSimple.getName());
                            }
                        });
                    }

                    @Override
                    public void onError(Throwable e) {

                    }
                });

    }
}
