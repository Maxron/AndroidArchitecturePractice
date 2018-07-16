package com.maxron.androidarchitecturepractice.data;

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

    private final GithubServiceApi githubService;
    private final RepoModelMapper mapper;

    public UserRepositoryImpl(GithubServiceApi githubService, RepoModelMapper mapper) {
        this.githubService = githubService;
        this.mapper = mapper;
    }

    @Override
    public Single<List<RepoInfo>> listUserRepository(final String userName) {
        return Single.defer(new Callable<SingleSource<? extends List<RepoInfo>>>() {
            @Override
            public SingleSource<? extends List<RepoInfo>> call() throws Exception {
                return githubService.listUserRepository(userName)
                        .map(new Function<List<RepoDetail>, List<RepoInfo>>() {
                            @Override
                            public List<RepoInfo> apply(List<RepoDetail> repoDetails) throws Exception {
                                List<RepoInfo> result = new ArrayList<>();
                                for( RepoDetail repoDetail : repoDetails) {
                                    result.add(mapper.repodetailToRepoInfo(repoDetail));
                                }
                                return result;
                            }
                        });
            }
        });
    }
}
