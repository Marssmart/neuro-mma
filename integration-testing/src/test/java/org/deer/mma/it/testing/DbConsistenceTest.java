package org.deer.mma.it.testing;

import org.deer.mma.it.testing.common.DbTestConfig;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = DbTestConfig.class)
public class DbConsistenceTest {

  private static final Logger LOG = LoggerFactory.getLogger(DbConsistenceTest.class);

  /**
   * This test just verify whether db can be assembled,aka whether the annotations in DTO's are
   * correct
   */
  @Test
  public void testAnnotations() {
    LOG.info("Annotations are valid");
  }
}