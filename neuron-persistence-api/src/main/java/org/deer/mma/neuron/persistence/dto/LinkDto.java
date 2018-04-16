package org.deer.mma.neuron.persistence.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

@Entity
@Table(name = "LINK")
public class LinkDto {

  @Id
  @Column
  private long id;

  @ManyToOne
  @JoinColumn(name = "from_id", nullable = false)
  private NeuronDto from;

  @ManyToOne
  @JoinColumn(name = "to_id", nullable = false)
  private NeuronDto to;

  @Column(nullable = false)
  private double weight;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public NeuronDto getFrom() {
    return from;
  }

  public void setFrom(NeuronDto from) {
    this.from = from;
  }

  public NeuronDto getTo() {
    return to;
  }

  public void setTo(NeuronDto to) {
    this.to = to;
  }

  public double getWeight() {
    return weight;
  }

  public void setWeight(double weight) {
    this.weight = weight;
  }
}
