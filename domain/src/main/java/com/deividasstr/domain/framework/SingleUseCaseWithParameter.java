package com.deividasstr.domain.framework;

import io.reactivex.Single;

public interface SingleUseCaseWithParameter<R, P> {

    Single<R> execute(P parameter);
}
