package org.deer.mma.neuron.api;

import java.util.Optional;
import javax.annotation.Nonnull;

public interface CalculationContext {

  /**
   * Globally active bias for neuron activation
   */
  double globalBias();

  /**
   * Bias per neuron, if present, takes precedence over global bias
   */
  @Nonnull
  Optional<Double> localBias(@Nonnull final Neuron neuron);
}
