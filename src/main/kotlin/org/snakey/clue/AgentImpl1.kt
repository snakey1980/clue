package org.snakey.clue

class AgentImpl1(private val player: Person, private val hand: Set<Card>) : Agent {

    private val possiblePeople = Person.values().toMutableSet()
    private val possibleRooms = Person.values().toMutableSet()
    private val possibleWeapons = Person.values().toMutableSet()
    private val refutations = Person.values().associate { Pair(it, mutableSetOf<Murder>()) }
    private val knownPositives = Person.values().associate { Pair(it, mutableSetOf<Card>()) }
    private val knownNegatives = Person.values().associate { Pair(it, mutableSetOf<Card>()) }
    private val shownCards = Person.values().toList().minus(player).associate { Pair(it, mutableSetOf<Card>()) }

    init {
        possiblePeople.removeAll(hand)
        possibleRooms.removeAll(hand)
        possibleWeapons.removeAll(hand)
        knownPositives[player]!!.addAll(hand)
        knownNegatives.filterKeys { it != player }.forEach { it.value.addAll(hand) }
    }

    override fun player(): Person {
        return player
    }

    override fun respond(player: Person, suggestion: Murder): Card? {
        return suggestion
                .intersect(hand)
                .sortedBy { it in shownCards[player]!! }
                .sortedBy { it in shownCards.values.flatten() }
                .lastOrNull()
    }

    private fun addPositive(player: Person, card: Card) {
        knownPositives[player]!!.add(card)
        possiblePeople.remove(card)
        possibleRooms.remove(card)
        possibleWeapons.remove(card)
        Person.values().toSet().minus(player).minus(this.player).forEach {
            addNegative(it, card)
        }
    }

    private fun addNegative(player: Person, card: Card) {
        knownNegatives[player]!!.add(card)
    }

    override fun show(player: Person, card: Card) {
        addPositive(player, card)
    }

    override fun notifyRefutation(player: Person, responder: Person, suggestion: Murder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun notifyDenial(player: Person, responder: Person, suggestion: Murder) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun knows(): Boolean {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun suggest(): Murder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun accuse(): Murder {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

}