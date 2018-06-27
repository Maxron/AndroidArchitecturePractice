package com.maxron.domain.interactor.type;


import io.reactivex.Observable;

public interface UseCaseWithParameter<P, R> {

    Observable<R> execute(P parameter);
}
