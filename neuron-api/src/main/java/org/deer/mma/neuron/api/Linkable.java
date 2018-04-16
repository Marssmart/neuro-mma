package org.deer.mma.neuron.api;

import javax.annotation.Nonnull;

/**
 * Adds capability to be linkable to other neurons
 */
public interface Linkable {

  /**
   * Links this to other neuron
   */
  void linkTo(@Nonnull final Neuron neuron);
}
