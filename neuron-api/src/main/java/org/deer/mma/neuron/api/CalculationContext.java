package org.deer.mma.neuron.api;

import javax.annotation.Nonnull;
import java.util.Optional;

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
