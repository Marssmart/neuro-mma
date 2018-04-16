package org.deer.mma.neuron.api;

/**
 * Represents neuron in network
 */
public interface Neuron extends Identifiable, Linkable {

    /**
     * Calculates current activation of profile
     */
    double activation();
}
