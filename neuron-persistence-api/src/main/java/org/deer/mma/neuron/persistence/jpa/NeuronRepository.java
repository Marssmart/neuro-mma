package org.deer.mma.neuron.persistence.jpa;

import org.deer.mma.neuron.persistence.dto.NeuronDto;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface NeuronRepository extends PagingAndSortingRepository<NeuronDto, Long> {

}
