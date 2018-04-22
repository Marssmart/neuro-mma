package org.deer.mma.neuron.input.repository;

import org.deer.mma.neuron.input.dto.FighterDto;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface FighterRepository extends PagingAndSortingRepository<FighterDto, Long>,
    JpaSpecificationExecutor<FighterDto> {

}
