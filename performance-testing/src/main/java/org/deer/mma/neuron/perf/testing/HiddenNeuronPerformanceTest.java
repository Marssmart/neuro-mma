package org.deer.mma.neuron.perf.testing;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nonnull;
import org.deer.mma.neuron.api.CalculationContext;
import org.deer.mma.neuron.api.Neuron;
import org.deer.mma.neuron.impl.HiddenNeuron;
import org.openjdk.jmh.annotations.Benchmark;
import org.openjdk.jmh.annotations.BenchmarkMode;
import org.openjdk.jmh.annotations.Fork;
import org.openjdk.jmh.annotations.Measurement;
import org.openjdk.jmh.annotations.Mode;
import org.openjdk.jmh.annotations.OutputTimeUnit;
import org.openjdk.jmh.annotations.Param;
import org.openjdk.jmh.annotations.Scope;
import org.openjdk.jmh.annotations.Setup;
import org.openjdk.jmh.annotations.State;
import org.openjdk.jmh.annotations.Warmup;
import org.openjdk.jmh.infra.Blackhole;

@BenchmarkMode(Mode.All)
@State(Scope.Thread)
@OutputTimeUnit(TimeUnit.SECONDS)
@Fork(1)
@Measurement(iterations = 50)
@Warmup(iterations = 20)
public class HiddenNeuronPerformanceTest {

  @Param({"50", "100", "150"})
  private int inputLayerSize;

  private HiddenNeuron mainNeuron;

  @Setup
  public void init() {
    CalculationContext ctx = new CalculationContext() {
      @Override
      public double globalBias() {
        return 0;
      }

      @Nonnull
      @Override
      public Optional<Double> localBias(@Nonnull Neuron neuron) {
        return Optional.empty();
      }
    };

    AtomicInteger idGenerator = new AtomicInteger();

    final List<HiddenNeuron> layerOneNeurons = IntStream.range(0, inputLayerSize)
        .mapToObj(i -> new HiddenNeuron(idGenerator.getAndIncrement(), ctx))
        .collect(Collectors.toList());

    final List<HiddenNeuron> layerTwoNeurons = IntStream.range(0, inputLayerSize / 2)
        .mapToObj(i -> new HiddenNeuron(idGenerator.getAndIncrement(), ctx))
        .collect(Collectors.toList());

    mainNeuron = new HiddenNeuron(idGenerator.getAndIncrement(), ctx);

    layerTwoNeurons.forEach(lTwoNeuron ->
        layerOneNeurons.forEach(lOneNeuron ->
            lOneNeuron.linkTo(lTwoNeuron)));

    layerOneNeurons.forEach(lOneNeuron ->
        mainNeuron.linkTo(lOneNeuron));
  }

  @Benchmark
  public void testCalculationNoWeightAdjustment(Blackhole blackhole) {
    blackhole.consume(mainNeuron.activation());
  }
}
