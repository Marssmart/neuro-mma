package org.deer.mma.scripts.dto;

class ParsedFighterLine {

  public final def id
  public final def firstName
  public final def lastName
  public final def link
  public final def ref

  ParsedFighterLine(id, firstName, lastName, link, ref){
    this.id = id
    this.firstName = firstName
    this.lastName = lastName
    this.link = link
    this.ref = ref
  }

  @Override
  String toString() {
    return "ParsedFighterLine{" +
            "id=" + id +
            ", firstName=" + firstName +
            ", lastName=" + lastName +
            ", link=" + link +
            ", ref=" + ref +
            '}';
  }
}
