package com.maxron.domain.interactor;

import com.maxron.domain.interactor.type.SingleUseCaseWithParameter;
import com.maxron.domain.model.RepoInfo;
import com.maxron.domain.repository.UserRepository;

import java.util.List;

import io.reactivex.Single;

public class ListUserRepositoriesUseCase implements SingleUseCaseWithParameter<String, List<RepoInfo>> {

    private final UserRepository repository;

    public ListUserRepositoriesUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single<List<RepoInfo>> execute(String user) {
        return repository.listUserRepository(user);
    }
}
