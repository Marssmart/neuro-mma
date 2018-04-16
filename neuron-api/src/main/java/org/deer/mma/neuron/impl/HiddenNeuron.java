package org.deer.mma.neuron.impl;

import static java.util.concurrent.CompletableFuture.supplyAsync;
import static org.deer.mma.neuron.api.Async.Reportable.message;

import java.util.concurrent.CompletableFuture;
import javax.annotation.Nonnull;
import org.deer.mma.neuron.api.Async;
import org.deer.mma.neuron.api.CalculationContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HiddenNeuron extends AbstractNeuron implements Async {

  private static final Logger LOG = LoggerFactory.getLogger(HiddenNeuron.class);

  private final CalculationContext context;

  public HiddenNeuron(final long id, @Nonnull final CalculationContext context) {
    super(id);
    this.context = context;
  }

  private static double sigmoid(double x) {
    return (1 / (1 + Math.pow(Math.E, (-1 * x))));
  }

  public double activation() {
    double weightedSum = getLinks()
        .parallelStream()
        .map(link -> supplyAsync(() -> link.to().activation() * link.weight())
            .whenCompleteAsync((result, throwable) ->
                report(LOG,
                    message("Activation for {} successfully calculated as {}", this, result),
                    message("Activation calculation for {} failed", this))))
        .mapToDouble(CompletableFuture::join)
        .sum();

    Double bias = context.localBias(this)
        .orElse(context.globalBias());

    return sigmoid(weightedSum - bias);
  }
}
