package org.deer.mma.it.testing.common;

import java.util.function.Function;
import org.hamcrest.BaseMatcher;
import org.hamcrest.Description;
import org.hamcrest.Matcher;

public interface DtoMatchers {

  default <T, U> Matcher<T> attributeIs(final Function<T, U> attributeExtractor,
      final U expectedValue) {
    return new BaseMatcher<T>() {

      private Object lastValue;

      @Override
      public boolean matches(Object o) {
        lastValue = attributeExtractor.apply((T) o);
        return lastValue.equals(expectedValue);
      }

      @Override
      public void describeTo(Description description) {
        description.appendText("Should be " + expectedValue + ", but was " + lastValue);
      }
    };
  }
}
