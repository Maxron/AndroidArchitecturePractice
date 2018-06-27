package com.maxron.domain.interactor.type;

import io.reactivex.Completable;

public interface CompletableUseCase {

    Completable execute();
}
