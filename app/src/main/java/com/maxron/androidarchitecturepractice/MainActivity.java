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

import java.util.List;
import java.util.function.Consumer;

import butterknife.BindView;
import butterknife.ButterKnife;
import butterknife.OnClick;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
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
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        GitHubServiceApi service = retrofit.create(GitHubServiceApi.class);

        final Call<List<Repo>> repos = service.listRepo("maxron");
        repos.enqueue(new Callback<List<Repo>>() {
            @RequiresApi(api = Build.VERSION_CODES.N)
            @Override
            public void onResponse(Call<List<Repo>> call, Response<List<Repo>> response) {
                Log.d(TAG, "onResponse: ");
                response.body().forEach(new Consumer<Repo>() {
                    @Override
                    public void accept(Repo repo) {
                        Log.d(TAG, "accept: repo: " + repo.getName());
                    }
                });
            }

            @Override
            public void onFailure(Call<List<Repo>> call, Throwable t) {
                Log.d(TAG, "onFailure: ");
            }
        });
    }
}
