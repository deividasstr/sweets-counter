package com.deividasstr.domain.framework;

import io.reactivex.Completable;

public interface CompletableUseCaseWithParameter<P> {

    Completable execute(P parameter);
}
