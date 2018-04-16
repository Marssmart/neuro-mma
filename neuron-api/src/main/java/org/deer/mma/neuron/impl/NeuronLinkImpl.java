package org.deer.mma.neuron.impl;

import java.util.Objects;
import javax.annotation.Nonnull;
import org.deer.mma.neuron.api.Neuron;
import org.deer.mma.neuron.api.NeuronLink;

class NeuronLinkImpl implements NeuronLink {

  private final Neuron from;
  private final Neuron to;
  private double weight;

  NeuronLinkImpl(@Nonnull final Neuron from,
      @Nonnull final Neuron to,
      final double weight) {
    this.from = from;
    this.to = to;
    this.weight = weight;
  }

  @Nonnull
  public Neuron from() {
    return from;
  }

  @Nonnull
  public Neuron to() {
    return to;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }

  public double weight() {
    return weight;
  }

  @Override
  public boolean equals(Object input) {
    if (this == input) {
      return true;
    }
    if (input == null || getClass() != input.getClass()) {
      return false;
    }
    NeuronLinkImpl that = (NeuronLinkImpl) input;
    return Objects.equals(from, that.from)
        && Objects.equals(to, that.to);
  }

  @Override
  public int hashCode() {
    return Objects.hash(from, to);
  }

  @Override
  public String toString() {
    return "NeuronLinkImpl{"
        + "from=" + from
        + ", to=" + to
        + ", weight=" + weight
        + '}';
  }
}
