package org.snakey.clue

interface Game {
    fun done(): Boolean
    fun next(): String
}

fun game(agents: Map<Person, Agent>): Game {
    return GameImpl(agents)
}

private class GameImpl(val agents: Map<Person, Agent>): Game {

    var done = false
    var personWhoseTurnItIs = Person.MISS_SCARLET
    var round = 1
    var turn = 1

    override fun done(): Boolean {
        return done
    }

    override fun next(): String {
        if (done) {
            throw IllegalStateException()
        }
        val builder = StringBuilder("Round $round (turn $turn overall) ")
        val agentWhoseTurnItIs = agents[personWhoseTurnItIs]!!
        if (agentWhoseTurnItIs.knows()) {
            done = true
            builder.append("$personWhoseTurnItIs makes accusation: ${agentWhoseTurnItIs.accuse()}")
        }
        else {
            val suggestion = agentWhoseTurnItIs.suggest()
            builder.append("$personWhoseTurnItIs makes suggestion: $suggestion.  ")
            var found = false
            val nextPlayers = generateSequence(personWhoseTurnItIs.successor(), { if (!found && it.successor() != personWhoseTurnItIs) it.successor() else null })
            nextPlayers.forEach { respondent ->
                val respondingAgent = agents[respondent]!!
                val response = respondingAgent.respond(personWhoseTurnItIs, suggestion)
                if (response == null) {
                    builder.append("${respondingAgent.player()} denies knowledge.  ")
                    agents.values.minus(respondingAgent).forEach { it.notifyDenial(personWhoseTurnItIs, respondent, suggestion) }
                }
                else {
                    builder.append("${respondingAgent.player()} shows.  ")
                    agents.values.minus(agentWhoseTurnItIs).minus(respondingAgent).forEach { it.notifyRefutation(personWhoseTurnItIs, respondent, suggestion) }
                    agentWhoseTurnItIs.show(respondent, response)
                    found = true
                }
            }
            if (agentWhoseTurnItIs.knows()) {
                done = true
                builder.append("$personWhoseTurnItIs makes accusation: ${agentWhoseTurnItIs.accuse()}")
            }
            personWhoseTurnItIs = personWhoseTurnItIs.successor()
            turn++
            if (personWhoseTurnItIs == Person.MISS_SCARLET) {
                round++
            }
        }
        return builder.toString()
    }
}