package org.deer.mma.neuron.input.repository.criteria;

import org.deer.mma.neuron.input.dto.FightDto;
import org.springframework.data.jpa.domain.Specification;

public interface FightCriteria {

  default Specification<FightDto> fightsPerFighter(final long fighterId) {
    return (root, criteriaQuery, builder) ->
        builder.or(
            builder.equal(root.get("blueCornerFighter"), fighterId),
            builder.equal(root.get("redCornerFighter"), fighterId));
  }
}
