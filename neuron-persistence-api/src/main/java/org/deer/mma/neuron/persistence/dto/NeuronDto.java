package org.deer.mma.neuron.persistence.dto;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "NEURON")
public class NeuronDto {

  @Id
  @Column
  private long id;

  @Column(nullable = false)
  private double bias;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public double getBias() {
    return bias;
  }

  public void setBias(double bias) {
    this.bias = bias;
  }
}
