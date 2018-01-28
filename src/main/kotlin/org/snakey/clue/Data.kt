package org.snakey.clue

interface Card
interface Owner

object CaseFile: Owner

enum class Person: Card, Owner {
    REVEREND_GREEN, COLONEL_MUSTARD, MRS_PEACOCK, PROFESSOR_PLUM, MISS_SCARLET, MRS_WHITE;
    fun successor(): Person {
        return Person.values().first { it.ordinal == (this.ordinal + 1) % Person.values().size }
    }
}

enum class Room: Card {
    BALL_ROOM, BILLIARD_ROOM, CONSERVATORY, DINING_ROOM, HALL, KITCHEN, LIBRARY, LOUNGE, STUDY
}

enum class Weapon: Card {
    CANDLESTICK, DAGGER, PIPE, REVOLVER, ROPE, WRENCH
}

data class Murder private constructor(val person: Person, val room: Room, val weapon: Weapon, val set: Set<Card>): Set<Card> by set {

    constructor(person: Person, room: Room, weapon: Weapon): this(person, room, weapon, setOf(person, room, weapon))

}