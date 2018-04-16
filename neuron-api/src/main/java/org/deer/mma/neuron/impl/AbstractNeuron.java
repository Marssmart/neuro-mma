package org.deer.mma.neuron.impl;

import org.deer.mma.neuron.api.NeuronLink;
import org.deer.mma.neuron.api.Neuron;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.annotation.Nonnegative;
import javax.annotation.Nonnull;
import java.util.Collections;
import java.util.LinkedHashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractNeuron implements Neuron {

    private static final Logger LOG = LoggerFactory.getLogger(AbstractNeuron.class);

    private final Set<NeuronLink> links;
    private final long id;

    protected AbstractNeuron(@Nonnegative final long id) {
        this.id = id;
        links = Collections.synchronizedSet(new LinkedHashSet<NeuronLink>());
    }

    public long id() {
        return id;
    }

    public void linkTo(@Nonnull Neuron neuron) {
        LOG.trace("Adding link from neuron {} to neuron {}", this, neuron);
        links.add(new NeuronLinkImpl(this, neuron, 0.5));
    }

    public Set<NeuronLink> getLinks() {
        return Collections.unmodifiableSet(links);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AbstractNeuron that = (AbstractNeuron) o;
        return id == that.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "AbstractNeuron{" +
                "links=[" + links.stream()
                .limit(5)//to prevent to long logs
                .map(link -> "[" + link.from().id() + " -> " + link.to().id() + "]")
                .collect(Collectors.joining(" , ")) +
                (links.size() > 5 ? "..." : "") +
                "], id=" + id +
                '}';
    }
}
