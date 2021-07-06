package com.example.commanderorganizerapp

import java.io.Serializable

class Card(val cardName: String,
           val cardColorIdentity : String,
           val cardManaCost : String,
           commanderValue: Int) : Serializable {

    var listOfCommanders = ArrayList<Int>()
    var manaSymbolArray = ArrayList<Int>()

    init{
        listOfCommanders.add(commanderValue)
        manaSymbolArray = GetStuff.getManaCostIdArray(cardManaCost)
    }


    fun addCommander(commanderValue: Int) {
        listOfCommanders.add(commanderValue)
    }

    fun removeCommander(commanderValue: Int) {
        listOfCommanders.remove(commanderValue)
    }

}