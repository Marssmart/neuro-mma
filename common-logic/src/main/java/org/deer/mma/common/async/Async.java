package org.deer.mma.common.async;

import java.util.function.BiConsumer;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import org.slf4j.Logger;

/**
 * Async capabilities
 */
public interface Async {

  /**
   * BiConsumer for CompletableFuture that reports both on success(TRACE) and failure(ERROR)
   * */
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
