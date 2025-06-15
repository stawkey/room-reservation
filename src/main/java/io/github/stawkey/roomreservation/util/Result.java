package io.github.stawkey.roomreservation.util;

import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;

public class Result<T> {
    private final T value;
    private final String error;
    private final boolean success;

    private Result(T value, String error, boolean success) {
        this.value = value;
        this.error = error;
        this.success = success;
    }

    public static <T> Result<T> success(T value) {
        return new Result<>(value, null, true);
    }

    public static <T> Result<T> failure(String error) {
        return new Result<>(null, error, false);
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isFailure() {
        return !success;
    }

    public T getValue() {
        if (!success) {
            throw new IllegalStateException("Cannot get value from failure result: " + error);
        }
        return value;
    }

    public Optional<T> getOptionalValue() {
        return Optional.ofNullable(value);
    }

    public String getError() {
        if (success) {
            throw new IllegalStateException("Cannot get error from success result");
        }
        return error;
    }

    public Result<T> onSuccess(Consumer<T> action) {
        if (success) {
            action.accept(value);
        }
        return this;
    }

    public Result<T> onFailure(Consumer<String> action) {
        if (!success) {
            action.accept(error);
        }
        return this;
    }

    public <U> Result<U> map(Function<T, U> mapper) {
        if (success) {
            return Result.success(mapper.apply(value));
        } else {
            return Result.failure(error);
        }
    }
}
