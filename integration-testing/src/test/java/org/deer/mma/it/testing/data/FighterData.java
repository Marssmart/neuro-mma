package org.deer.mma.it.testing.data;

import java.util.HashMap;
import java.util.Map;
import org.deer.mma.neuron.input.dto.FighterDto;
import org.deer.mma.neuron.input.repository.FighterRepository;

public interface FighterData {

  default FighterDto newFighter(final long id) {
    FighterDto fighterDto = new FighterDto();
    fighterDto.setId(id);
    return fighterDto;
  }

  default Map<Long, FighterDto> fighterMap(FighterRepository fighterRepository) {
    Map<Long, FighterDto> fighterMap = new HashMap<>();
    fighterRepository.findAll()
        .forEach(fighterDto -> fighterMap.put(fighterDto.getId(), fighterDto));
    return fighterMap;
  }
}
