package org.deer.mma.neuron.api;

import org.slf4j.Logger;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.function.BiConsumer;

public interface Async {

    default <T> BiConsumer<T, Throwable> report(@Nonnull final Logger logger,
                                                @Nonnull final Reportable successMsg,
                                                @Nonnull final Reportable errorMsg) {
        return (result, throwable) -> {
            if (throwable != null) {
                logger.error(errorMsg.msg, errorMsg.params, throwable);
            } else {
                logger.trace(successMsg.msg, successMsg.params);
            }
        };
    }

    final class Reportable {
        private final String msg;
        private final Object[] params;

        private Reportable(String msg, Object[] params) {
            this.msg = msg;
            this.params = params;
        }

        public static Reportable message(@Nonnull final String msg,
                                         @Nullable final Object... params) {
            return new Reportable(msg, params != null ? params : new Object[0]);
        }
    }
}
