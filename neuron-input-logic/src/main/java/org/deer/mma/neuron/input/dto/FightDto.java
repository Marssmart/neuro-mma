package org.deer.mma.neuron.input.dto;

import java.sql.Time;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import org.deer.mma.neuron.input.dto.enumerated.FightEnd;

@Entity
@Table(name = "FIGHT")
public class FightDto {

  @Id
  @Column
  private long id;

  @ManyToOne
  @JoinColumn(name = "blue_corner_fighter_id", nullable = false)
  private FighterDto blueCornerFighter;

  @ManyToOne
  @JoinColumn(name = "red_corner_fighter_id", nullable = false)
  private FighterDto redCornerFighter;

  @Column
  private String event;

  @Column(nullable = false)
  @Enumerated(EnumType.STRING)
  private FightEnd fightEnd;

  @Column
  private String fightEndType;

  @Column
  private int stopageRound;

  @Column
  private Time stopageTime;

  public long getId() {
    return id;
  }

  public void setId(long id) {
    this.id = id;
  }

  public FighterDto getBlueCornerFighter() {
    return blueCornerFighter;
  }

  public void setBlueCornerFighter(FighterDto blueCornerFighter) {
    this.blueCornerFighter = blueCornerFighter;
  }

  public FighterDto getRedCornerFighter() {
    return redCornerFighter;
  }

  public void setRedCornerFighter(FighterDto redCornerFighter) {
    this.redCornerFighter = redCornerFighter;
  }

  public String getEvent() {
    return event;
  }

  public void setEvent(String event) {
    this.event = event;
  }

  public FightEnd getFightEnd() {
    return fightEnd;
  }

  public void setFightEnd(FightEnd fightEnd) {
    this.fightEnd = fightEnd;
  }

  public String getFightEndType() {
    return fightEndType;
  }

  public void setFightEndType(String fightEndType) {
    this.fightEndType = fightEndType;
  }

  public int getStopageRound() {
    return stopageRound;
  }

  public void setStopageRound(int stopageRound) {
    this.stopageRound = stopageRound;
  }

  public Time getStopageTime() {
    return stopageTime;
  }

  public void setStopageTime(Time stopageTime) {
    this.stopageTime = stopageTime;
  }
}
