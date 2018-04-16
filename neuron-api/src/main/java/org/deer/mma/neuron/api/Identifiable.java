package org.deer.mma.neuron.api;

import javax.annotation.Nonnegative;

/**
 * Adds capability to be uniquely identifiable
 */
public interface Identifiable {

    /**
     * Unique id
     */
    @Nonnegative
    long id();
}
