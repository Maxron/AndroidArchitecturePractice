package com.maxron.domain.interactor;

import com.maxron.domain.interactor.type.SingleUseCaseWithParameter;
import com.maxron.domain.repository.UserRepository;

import io.reactivex.Single;

public class ListUserRepositoriesUseCase implements SingleUseCaseWithParameter {

    private final UserRepository repository;

    public ListUserRepositoriesUseCase(UserRepository repository) {
        this.repository = repository;
    }

    @Override
    public Single execute(Object parameter) {
        return null;
    }
}
