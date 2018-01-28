package org.snakey.clue

import java.util.*

fun allPossibleMurders(): Set<Murder> = Person.values().flatMap { p -> Room.values().flatMap { r -> Weapon.values().map { w -> Murder(p, r, w) } }}.toSet()

fun allPossibleHands(): Set<Set<Card>> = allCards().flatMap { c1 -> allCards().minus(c1).flatMap { c2 -> allCards().minus(c1).minus(c2).map { c3 -> setOf(c1, c2, c3) } } }.toSet()

fun generateMurder(random: Random): Murder {
    fun <T> chooseRandom(array: Array<T>): T = array[random.nextInt(array.size)]
    return Murder(chooseRandom(Person.values()), chooseRandom(Room.values()), chooseRandom(Weapon.values()))
}

fun allCards(): List<Card> {
    return (Person.values() as Array<Card>).toList().plus(Room.values() as Array<Card>).plus(Weapon.values() as Array<Card>)
}

fun murderAndHands(random: Random) : Pair<Murder, Map<Person, Set<Card>>> {
    val murder = generateMurder(random)
    val restOfDeck = allCards().minus(murder).toMutableList()
    Collections.shuffle(restOfDeck, random)
    val hands = Person.values().mapIndexed { index, person ->
        Pair(person, restOfDeck.subList(index * 3, index * 3 + 3).toSet())
    }.toMap()
    return Pair(murder, hands)
}

fun main(args: Array<String>) {
    println(allPossibleHands().size)
    println(false.compareTo(true) < 0)
    println(listOf(true, false).sorted())
//    val random = Random(0)
//    val (murder, hands) = murderAndHands(random)
//    println("Murder is $murder")
//    val game = game(Person.values().map { Pair(it, AgentImpl1(it, hands[it]!!)) }.toMap())
//    while (!game.done()) {
//        println(game.next())
//    }
//    println("Murder was $murder")

}