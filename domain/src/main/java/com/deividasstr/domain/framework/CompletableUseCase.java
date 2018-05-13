package com.deividasstr.domain.framework;

import io.reactivex.Completable;

public interface CompletableUseCase {

    Completable execute();
}
