package com.maxron.androidarchitecturepractice.data;

import android.support.annotation.NonNull;

import com.maxron.androidarchitecturepractice.data.local.RepoDao;
import com.maxron.androidarchitecturepractice.data.local.entity.RepoModel;
import com.maxron.androidarchitecturepractice.data.mapper.RepoModelMapper;
import com.maxron.androidarchitecturepractice.data.remote.GithubServiceApi;
import com.maxron.androidarchitecturepractice.data.remote.model.RepoDetail;
import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;

import io.reactivex.Single;
import io.reactivex.SingleSource;
import io.reactivex.functions.Function;

public class UserRepositoryImpl implements UserRepository {

    private static final String TAG = UserRepositoryImpl.class.getSimpleName();
    private final GithubServiceApi githubService;
    private final RepoModelMapper mapper;
    private final RepoDao repoDao;

    public UserRepositoryImpl(GithubServiceApi githubService, RepoModelMapper mapper, RepoDao dao) {
        this.githubService = githubService;
        this.mapper = mapper;
        this.repoDao = dao;
    }

    @Override
    public Single<List<RepoInfo>> listUserRepository(final String userName) {
        return Single.defer(new Callable<SingleSource<? extends List<RepoInfo>>>() {
            @Override
            public SingleSource<? extends List<RepoInfo>> call() throws Exception {
                if (isCached()) {
                    return loadFromCache();
                }
                return responseAfterSyncFromRemote(userName);
            }
        });
    }

    private Single<List<RepoInfo>> responseAfterSyncFromRemote(final String userName) {
        return Single.defer(new Callable<SingleSource<? extends List<RepoInfo>>>() {
            @Override
            public SingleSource<? extends List<RepoInfo>> call() throws Exception {
                return githubService.listUserRepository(userName)
                        .map(new Function<List<RepoDetail>, List<RepoInfo>>() {
                            @Override
                            public List<RepoInfo> apply(List<RepoDetail> repoDetails) throws Exception {
                                List<RepoInfo> response = new ArrayList<>();
                                for (RepoDetail repoDetail : repoDetails){
                                    response.add(mapper.repodetailToRepoInfo(repoDetail));
                                    repoDao.insert(mapper.repodetailToRepoModel(repoDetail));
                                }
                                return response;
                            }
                        });
            }
        });
    }

    private Single<List<RepoInfo>> loadFromCache() {
        return Single.defer(new Callable<SingleSource<? extends List<RepoInfo>>>() {
            @Override
            public SingleSource<? extends List<RepoInfo>> call() throws Exception {
                return Single.just(repoDao.getAllRepos())
                        .map(new Function<List<RepoModel>, List<RepoInfo>>() {
                            @Override
                            public List<RepoInfo> apply(List<RepoModel> repoModels) throws Exception {
                                return convertRepoModelToRepoInfo(repoModels);
                            }
                        });
            }
        });
    }

    @NonNull
    private List<RepoInfo> convertRepoModelToRepoInfo(List<RepoModel> repoModels) {
        List<RepoInfo> results = new ArrayList<>();
        for( RepoModel model : repoModels) {
            results.add(new RepoInfo(model.getId(), model.getName()));
        }

        return results;
    }

    private boolean isCached() {
        return !repoDao.getAllRepos().isEmpty();
    }
}
