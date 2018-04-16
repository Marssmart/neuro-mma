package org.deer.mma.neuron.impl;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static org.junit.Assert.fail;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.util.concurrent.CompletionException;
import org.deer.mma.neuron.api.CalculationContext;
import org.deer.mma.neuron.api.Neuron;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

public class HiddenNeuronTest {

  @Mock
  private CalculationContext ctx;

  private HiddenNeuron hiddenNeuron;

  private static Neuron mockNeuron(final long id, final double activation) {
    final Neuron to = mock(Neuron.class);
    when(to.id()).thenReturn(id);
    when(to.activation()).thenReturn(activation);
    when(to.toString()).thenReturn("MockNeuron[id=" + id + ", activation=" + activation + "]");
    return to;
  }

  private static Neuron mockFailingNeuron(final long id) {
    final Neuron to = mock(Neuron.class);
    when(to.id()).thenReturn(id);
    when(to.activation())
        .thenThrow(new IllegalStateException("Activation for neuron " + id + " failed"));
    when(to.toString()).thenReturn("FailingMockNeuron[id=" + id + "]");
    return to;
  }

  @Before
  public void setUp() throws Exception {
    MockitoAnnotations.initMocks(this);

    hiddenNeuron = new HiddenNeuron(0, ctx);
  }

  @Test
  public void defaultActivation() {
    //bias 0
    //no links
    assertEquals(0.5D, hiddenNeuron.activation(), 0);
  }

  @Test
  public void singleLinkActivation() {
    //no bias
    //one link
    hiddenNeuron.linkTo(mockNeuron(1, 0.5));

    assertEquals(0.5621765008857981, hiddenNeuron.activation(), 0);
  }

  @Test
  public void singleLinkFailActivation() {
    //no bias
    //one link
    hiddenNeuron.linkTo(mockFailingNeuron(1));

    try {
      hiddenNeuron.activation();
    } catch (CompletionException e) {
      assertTrue(e.getCause() instanceof IllegalStateException);
      return;
    }
    fail();
  }

  @Test
  public void multiLinkActivation() {
    //no bias
    //3 links
    hiddenNeuron.linkTo(mockNeuron(1, 0.5));
    hiddenNeuron.linkTo(mockNeuron(2, 0.2));
    hiddenNeuron.linkTo(mockNeuron(3, 0.7));

    assertEquals(0.668187772168166, hiddenNeuron.activation(), 0);
  }

  @Test
  public void multiLinkFailedActivation() {
    //no bias
    //3 links
    hiddenNeuron.linkTo(mockNeuron(1, 0.5));
    hiddenNeuron.linkTo(mockNeuron(2, 0.2));
    hiddenNeuron.linkTo(mockFailingNeuron(3));

    try {
      hiddenNeuron.activation();
    } catch (CompletionException e) {
      assertTrue(e.getCause() instanceof CompletionException);
      assertTrue(e.getCause().getCause() instanceof IllegalStateException);
      return;
    }
    fail();
  }

  @Test
  public void multiLinkOneHiddenLayerActivation() {
    Neuron l0n1 = mockNeuron(1, 0.2);
    Neuron l0n2 = mockNeuron(2, 0.7);
    Neuron l0n3 = mockNeuron(3, 0.6);

    HiddenNeuron l1n1 = new HiddenNeuron(11, ctx);
    HiddenNeuron l1n2 = new HiddenNeuron(12, ctx);
    HiddenNeuron l1n3 = new HiddenNeuron(13, ctx);

    //L0 -> L1 links
    l1n1.linkTo(l0n1);
    l1n1.linkTo(l0n2);
    l1n1.linkTo(l0n3);

    l1n2.linkTo(l0n1);
    l1n2.linkTo(l0n3);

    l1n3.linkTo(l0n2);

    //L1 -> L2 links
    hiddenNeuron.linkTo(l1n1);
    hiddenNeuron.linkTo(l1n2);
    hiddenNeuron.linkTo(l1n3);

    assertEquals(0.717529910973181, hiddenNeuron.activation(), 0);

  }
}