package com.deividasstr.domain.framework;

import io.reactivex.Single;

public interface SingleUseCase<T> {

    Single<T> execute();
}
