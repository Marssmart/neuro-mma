package org.deer.mma.scripts.dto;

class ParsedFightLine {

    public final def id
    public final def date
    public final def event
    public final def fightEnd
    public final def fightEndType
    public final def fighterRef
    public final def oponentFirstName
    public final def oponentLastName
    public final def oponentLink
    public final def opponentRef
    public def stopageRound
    public def stopageTime

    //calculated fields
    public def fighterId
    public def oponentId

    ParsedFightLine(id, date, event, fightEnd, fightEndType,
                    fighterRef, oponentFirstName, oponentLastName, oponentLink,
                    opponentRef, stopageRound, stopageTime) {
        this.id = id
        this.stopageTime = stopageTime.trim()
        this.date = date.trim()
        this.event = event.trim().replace("\"", "'")
        this.fightEnd = fightEnd.trim()
        this.fightEndType = fightEndType.trim()
        this.fighterRef = fighterRef.trim()
        this.oponentFirstName = oponentFirstName.trim()
        this.oponentLastName = oponentLastName.trim()
        this.oponentLink = oponentLink.trim()
        this.opponentRef = opponentRef.trim()
        this.stopageRound = stopageRound.trim()
    }

    public remapAndReformat(Map<String, ParsedFighterLine> registerByRef) {
        def fighter = registerByRef.get(fighterRef)
        def opponent = registerByRef.get(opponentRef)

        if (fighter != null)
            fighterId = fighter.id

        if (opponent != null)
            oponentId = opponent.id

        if (stopageTime == "0:00")
            stopageTime = null

        if (stopageRound == "" | stopageRound == "0")
            stopageRound = null

        this
    }

    public isValid() {
        fighterId != null && oponentId != null
    }
}
