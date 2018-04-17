package org.deer.mma.it.testing;

import static org.deer.mma.neuron.input.dto.enumerated.FightEnd.DRAW;
import static org.deer.mma.neuron.input.dto.enumerated.FightEnd.LOSS;
import static org.deer.mma.neuron.input.dto.enumerated.FightEnd.WIN;
import static org.hamcrest.CoreMatchers.hasItems;
import static org.junit.Assert.assertThat;

import java.util.List;
import java.util.Map;
import org.deer.mma.it.testing.common.DbTestConfig;
import org.deer.mma.it.testing.common.DtoMatchers;
import org.deer.mma.it.testing.data.FightData;
import org.deer.mma.it.testing.data.FighterData;
import org.deer.mma.neuron.input.dto.FightDto;
import org.deer.mma.neuron.input.dto.FighterDto;
import org.deer.mma.neuron.input.repository.FightRepository;
import org.deer.mma.neuron.input.repository.FighterRepository;
import org.deer.mma.neuron.input.repository.criteria.FightCriteria;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@ContextConfiguration(classes = DbTestConfig.class)
public class FightRepositoryTest implements FighterData, FightData, FightCriteria, DtoMatchers {

  @Autowired
  private FightRepository fightRepository;

  @Autowired
  private FighterRepository fighterRepository;

  @Test
  public void testFightsPerFighter() {
    fighterRepository.save(newFighter(1L));
    fighterRepository.save(newFighter(2L));
    fighterRepository.save(newFighter(3L));

    Map<Long, FighterDto> fighterMap = fighterMap(fighterRepository);
    fightRepository.save(newFight(1L, 1L, 2L, WIN, fighterMap));
    fightRepository.save(newFight(2L, 1L, 3L, WIN, fighterMap));
    fightRepository.save(newFight(3L, 2L, 3L, DRAW, fighterMap));
    fightRepository.save(newFight(4L, 3L, 2L, LOSS, fighterMap));
    fightRepository.save(newFight(5L, 3L, 1L, LOSS, fighterMap));

    List<FightDto> fightsPerFighterOne = fightRepository.findAll(fightsPerFighter(1L));
    assertThat(fightsPerFighterOne,
        hasItems(
            attributeIs(FightDto::getId, 1L),
            attributeIs(FightDto::getId, 2L)));

    List<FightDto> fightsPerFighterTwo = fightRepository.findAll(fightsPerFighter(2L));
    assertThat(fightsPerFighterTwo,
        hasItems(
            attributeIs(FightDto::getId, 3L)));

    List<FightDto> fightsPerFighterThree = fightRepository.findAll(fightsPerFighter(3L));
    assertThat(fightsPerFighterThree,
        hasItems(
            attributeIs(FightDto::getId, 4L),
            attributeIs(FightDto::getId, 5L)));
  }
}
