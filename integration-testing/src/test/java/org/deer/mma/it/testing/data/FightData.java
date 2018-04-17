package org.deer.mma.it.testing.data;

import java.util.Map;
import org.deer.mma.neuron.input.dto.FightDto;
import org.deer.mma.neuron.input.dto.FighterDto;
import org.deer.mma.neuron.input.dto.enumerated.FightEnd;

public interface FightData {

  default FightDto newFight(final long id,
      final long blueCornerId,
      final long redCornerId,
      final FightEnd end,
      final Map<Long, FighterDto> fighterMap) {

    FightDto fightDto = new FightDto();
    fightDto.setId(id);
    fightDto.setBlueCornerFighter(fighterMap.get(blueCornerId));
    fightDto.setRedCornerFighter(fighterMap.get(redCornerId));
    fightDto.setFightEnd(end);

    return fightDto;
  }
}
