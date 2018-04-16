package org.deer.mma.scripts

import org.deer.mma.scripts.dto.ParsedFightLine
import org.deer.mma.scripts.dto.ParsedFighterLine
import org.slf4j.Logger
import org.slf4j.LoggerFactory

import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.atomic.AtomicLong
import java.util.stream.Collectors

import static java.nio.charset.StandardCharsets.UTF_8
import static java.nio.file.Files.readAllLines

class MongoToSqlScripts {

    private static final Logger LOG = LoggerFactory.getLogger(MongoToSqlScripts.class.getName())

    private static
    final String EXPORTS_FOLDER = "/home/jsrnicek/marssmart1992@gmail.com/data-exports"
    private static final String FIGHTS_CSV = "mma_stats.fight.csv"
    private static final String FIGHTERS_CSV = "mma_stats.fighter.csv"
    private static final String FIGHTER_INSERTS_OUT = "fighter.sql"
    private static final String FIGHT_INSERTS_OUT = "fight.sql"
    //counter
    AtomicLong totalFighters = new AtomicLong(0)
    AtomicLong validFighters = new AtomicLong(0)
    AtomicLong totalFights = new AtomicLong(0)
    AtomicLong validFights = new AtomicLong(0)

    static void main(String[] args) {
        new MongoToSqlScripts().convertMongoCsvToInserts()
    }

    void convertMongoCsvToInserts() {
        def fightersFilesPath = Paths.get(EXPORTS_FOLDER, FIGHTERS_CSV)
        def fightsFilesPath = Paths.get(EXPORTS_FOLDER, FIGHTS_CSV)

        def fighterIdCounter = 0
        def fightIdCounter = 0

        LOG.info "Reading content of $fightersFilesPath"
        def fighters = readAllLines(fightersFilesPath, UTF_8)
                .stream()
                .skip(1)//skip header
                .peek(incrementTotalFighters)
                .map(this.&splitLine)
                .filter(onlyValidFighterLines)
                .peek(incrementValidFighters)
                .map(parseFighterLine(fighterIdCounter))
                .toArray()
        LOG.info "Total fighters ${totalFighters.get()}/Valid fighters ${validFighters.get()}"

        LOG.info "Mapping ${fighters.length} fighters by ref"
        def byRefFighterMap = fighters.collectEntries {
            [it.ref, it]
        }

        LOG.info "Mapping ${fighters.length} fighters by id"
        def byIdFighterMap = fighters.collectEntries {
            [it.id, it]
        }

        LOG.info "Reading content of $fightsFilesPath"
        def fights = readAllLines(fightsFilesPath, UTF_8)
                .stream()
                .skip(1)
                .peek(incrementTotalFights)
                .map(this.&splitLine)
                .filter(onlyValidFightLines)
                .map(parseFightLine(fightIdCounter))
                .map {
            a -> a.remapAndReformat(byRefFighterMap)
        }
        .filter(onlyValidIdFights)
                .peek(incrementValidFights)
                .toArray()
        LOG.info "Total fights ${totalFights.get()} / Valid fights ${validFights.get()}"

        LOG.info "Generating inserts for fighters"
        def fighterInserts = fighters.toList()
                .stream()
                .map {
            fighter ->
                def insert = $/INSERT INTO mma_db.FIGHTER(Id,FirstName,LastName,Link) 
                            VALUES(${fighter.id},"${fighter.firstName}",
                            "${fighter.lastName}","${fighter.link}");/$
        }.collect(Collectors.toSet())

        LOG.info "Generating inserts for fights"
        def fightInserts = fights.toList()
                .stream()
                .map {
            fight ->
                def stopageTime = fight.stopageTime != null ?
                        "str_to_date('${fight.stopageTime}','%i:%s')" : null
                def stopageRound = fight.stopageRound != null ? "${fight.stopageRound}" : null
                def insert = $/INSERT INTO mma_db.FIGHT(Id,BlueCornerFighterId,RedCornerFighterId,
                            Event,FightEnd,FightEndType,StopageRound,StopageTime)
                            VALUES(${fight.id},${fight.fighterId},
                            ${fight.oponentId},"${fight.event}","${fight.fightEnd}",
                           "${fight.fightEndType}",$stopageRound,$stopageTime);/$
        }.collect(Collectors.toSet())

        def fighterOut = Paths.get(EXPORTS_FOLDER, FIGHTER_INSERTS_OUT)
        def fightOut = Paths.get(EXPORTS_FOLDER, FIGHT_INSERTS_OUT)

        LOG.info "Writing inserts for fighters"
        Files.write(fighterOut, fighterInserts, UTF_8)
        LOG.info "Writing inserts for fights"
        Files.write(fightOut, fightInserts, UTF_8)
    }

    private onlyValidIdFights = { def fight ->
        fight.isValid()
    }

    private incrementValidFights = { def input ->
        this.validFights.incrementAndGet()
    }

    private incrementValidFighters = { def input ->
        this.validFighters.incrementAndGet()
    }

    private incrementTotalFights = { def input ->
        this.totalFights.incrementAndGet()
    }

    private incrementTotalFighters = { def input ->
        this.totalFighters.incrementAndGet()
    }

    private parseFightLine = { def idCounter ->
        {
            data ->
                def id = idCounter++
                def date = data[1]
                def event = data[2]
                def fightEnd = data[3]
                def fightEndType = data[4]
                def fighterRef = data[5]
                def oponentFirstName = data[6]
                def oponentLastName = data[7]
                def oponentLink = data[8]
                def opponentRef = data[9]
                def stopageRound = data[10]
                def stopageTime = data[11]

                return new ParsedFightLine(id,
                        date,
                        event,
                        fightEnd,
                        fightEndType,
                        fighterRef,
                        oponentFirstName,
                        oponentLastName,
                        oponentLink,
                        opponentRef,
                        stopageRound,
                        stopageTime)
        }
    }

    //"_id";firstName;lastName;profileLink;ref
    private parseFighterLine = { def idCounter ->
        {
            data ->
                def firstName = data[1]
                def lastName = data[2]
                def link = data[3]
                def ref = data[4]

                new ParsedFighterLine(idCounter++, firstName, lastName, link, ref)
        }
    }


    private splitLine(line) {
        line.split "\\;"
    }

    private onlyValidFightLines = {
        lineParts -> checkHasExactNrOfFields(lineParts, 12)
    }

    private onlyValidFighterLines = {
        lineParts -> checkHasExactNrOfFields(lineParts, 5)
    }

    private checkHasExactNrOfFields(String[] lineParts, int limit) {
        if (lineParts.length != limit) {
            false
        } else
            true
    }
}
