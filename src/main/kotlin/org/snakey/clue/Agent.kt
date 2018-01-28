package org.snakey.clue

interface Agent {

    fun player(): Person
    fun respond(player: Person, suggestion: Murder): Card?
    fun show(player: Person, card: Card)
    fun notifyRefutation(player: Person, responder: Person, suggestion: Murder)
    fun notifyDenial(player: Person, responder: Person, suggestion: Murder)
    fun knows(): Boolean
    fun suggest(): Murder
    fun accuse(): Murder

}