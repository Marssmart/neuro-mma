package org.deer.mma.neuron.persistence.jpa;

import org.deer.mma.neuron.persistence.dto.LinkDto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LinkRepository extends PagingAndSortingRepository<LinkDto, Long> {

}
