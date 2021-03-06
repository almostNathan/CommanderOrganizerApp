package com.example.commanderorganizerapp

import android.content.Context
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class DeckListAdder (var decklist : String, var context: Context) {


    fun addDeck(){
        val deckListArray = decklist.split("\n")
        val commanderName = findCommander(deckListArray)
        val commanderIndex = addCommander(commanderName)


        for(item in deckListArray){
            val newItem = item.split(" ", ignoreCase = false, limit = 2)
            if(newItem[0] != "*CMDR*"){
                addCard(newItem[1], commanderIndex)

            }
        }


    }

    private fun addCard(cardName: String, commanderKey:Int) {
        //load the listof cards
        val cardsInDecks = GetStuff.getCardsInDecks(context)
        val listOfAllCards = GetStuff.getListOfAllCardsWholeLine(context)


        //look for cards that match the name of passed card
        for (item in cardsInDecks ){

            //if name matches only add commanderKey
            if (item.cardName == cardName){
                item.addCommander(commanderKey)
                val newOutputStream = ObjectOutputStream(FileOutputStream("${context.filesDir} cardsInDecks"))
                newOutputStream.writeObject(cardsInDecks)
                newOutputStream.close()
                return
            }
        }//else add new card
        //get cardName from listofallcards -> split -> add each row of card info
        //0 -> cardName 1-> colorIdentity 2-> manaCost
        for (cardLine in listOfAllCards) {
            val splitCardLine = cardLine.split("|")
            if (splitCardLine[0] == cardName) {
                cardsInDecks.add(Card(splitCardLine[0], splitCardLine[1], splitCardLine[2], commanderKey))
            }
        }

        //save the listof cards
        val newOutputStream = ObjectOutputStream(FileOutputStream("${context.filesDir} cardsInDecks"))
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
            val newOutputStream = ObjectOutputStream(FileOutputStream("${context.filesDir} commandersInDecks"))
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