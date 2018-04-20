package org.deer.mma.neuron.persistence.jpa;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = TestConfig.class)
public class NeuronRepositoryTest {

  @Autowired
  private NeuronRepository repository;

  @Test
  public void testAnnotations() {
    //TODO - add real tests, this is just to verify annotations
    repository.findAll();
  }
}