package org.deer.mma.neuron.api;

import javax.annotation.Nonnull;

/**
 * Represents link between two neurons
 */
public interface NeuronLink {

    /**
     * Start of link
     */
    @Nonnull
    Neuron from();

    /**
     * End of the link
     */
    @Nonnull
    Neuron to();

    /**
     * Set the weight that represents how important this link is
     */
    void setWeight(final double weight);

    /**
     * Get the weight that represents how important this link is
     */
    double weight();
}
