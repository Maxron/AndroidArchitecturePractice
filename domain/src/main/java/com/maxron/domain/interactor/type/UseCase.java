package com.maxron.domain.interactor.type;

import io.reactivex.Observable;

public interface UseCase<T> {

    Observable<T> execute();
}
