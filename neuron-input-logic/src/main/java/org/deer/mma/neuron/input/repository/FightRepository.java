package org.deer.mma.neuron.input.repository;

import org.deer.mma.neuron.input.dto.FightDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FightRepository extends PagingAndSortingRepository<FightDto, Long>,
    JpaSpecificationExecutor<FightDto> {

}
