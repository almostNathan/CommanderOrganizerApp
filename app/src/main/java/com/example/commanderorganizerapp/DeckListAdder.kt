package com.example.commanderorganizerapp

import android.content.Context
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class DeckListAdder (decklist : String, context: Context) {

    val decklist = decklist
    val context = context

    public fun addDeck(){
        val deckListArray = decklist?.split("\n")
        val commanderName = findCommander(deckListArray)
        val commanderIndex = addCommander(commanderName)


        if(deckListArray != null){
            for(item in deckListArray){
                val newItem = item.split(" ", ignoreCase = false, limit = 2)
                if(newItem[0] != "*CMDR*"){
                    addCard(newItem[1], commanderIndex)

                }
            }
        }


    }

    private fun addCard(cardName: String, commanderKey:Int) {
        //load the listof cards
        val cardsInDecks = GetStuff.getCardsInDecks(context)


        println(commanderKey)
        //look for cards that match the name of passed card
        if (cardsInDecks != null) {
            for (item in cardsInDecks ){

                //if name matches only add commanderKey
                if (item.cardName == cardName){
                    item.addCommander(commanderKey)
                    println(item.listOfCommanders)
                    val newOutputStream = ObjectOutputStream(FileOutputStream("${context?.filesDir} cardsInDecks"))
                    newOutputStream.writeObject(cardsInDecks)
                    newOutputStream.close()
                    return
                }
            }//else add new card
            cardsInDecks.add(Card(cardName, commanderKey))
        }

        //save the listof cards
        val newOutputStream = ObjectOutputStream(FileOutputStream("${context?.filesDir} cardsInDecks"))
        newOutputStream.writeObject(cardsInDecks)
        newOutputStream.close()
    }

    private fun addCommander(commanderName: String) : Int {
        val commanderArrayList = GetStuff.getCommandersInDecks(context)
        outer@ for(i in 0..16){
            for (item in commanderArrayList) {
                if (item.key == i){
                    continue@outer
                }
            }
            commanderArrayList.add(Commander(i, commanderName))
            val newOutputStream = ObjectOutputStream(FileOutputStream("${context?.filesDir} commandersInDecks"))
            newOutputStream.writeObject(commanderArrayList)
            newOutputStream.close()
            return i
        }



        return -1
    }

    private fun findCommander(deckListArray: List<String>?): String {
        if (deckListArray != null) {
            for (item in deckListArray){
                val newItem = item.split(" ", ignoreCase = true, limit = 2)
                if (newItem[0] == "*CMDR*"){
                    return newItem[1]
                }
            }
        }
        return "null"
    }

}