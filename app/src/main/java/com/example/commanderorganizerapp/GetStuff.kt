package com.example.commanderorganizerapp

import android.annotation.SuppressLint
import android.content.Context
import java.io.*
import java.util.*
import kotlin.Comparator
import kotlin.collections.ArrayList

class GetStuff {



    @Suppress("UNCHECKED_CAST")
    companion object {
        fun getCardsInDecks(context : Context?): ArrayList<Card>{
            val cardsInDecks : ArrayList<Card>
            val cardInputStream = ObjectInputStream(FileInputStream("${context?.filesDir} cardsInDecks"))
            cardsInDecks = cardInputStream.readObject() as ArrayList<Card>
            cardInputStream.close()
            return sortCardsInDecks(cardsInDecks)
        }

        fun getCommandersInDecks(context : Context?): ArrayList<Commander>{
            val cmdrInputStream = ObjectInputStream(FileInputStream("${context?.filesDir} commandersInDecks"))
            val commandersInDecks = cmdrInputStream.readObject() as ArrayList<Commander>
            cmdrInputStream.close()
            return commandersInDecks
        }

        fun getListOfAllCards(context : Context?): ArrayList<String>{
            val returnList = ArrayList<String>()
            val inputStream = context?.resources?.openRawResource(R.raw.legal_allcards_coloridentity_manacost)!!

            inputStream.bufferedReader().useLines { lines -> lines.forEach { returnList.add(it.split("|")[0])} }

            inputStream.close()
            return returnList
        }

        fun getListOfAllCardsWholeLine(context : Context?): ArrayList<String>{
            val returnList = ArrayList<String>()
            val inputStream = context?.resources?.openRawResource(R.raw.legal_allcards_coloridentity_manacost)!!

            inputStream.bufferedReader().useLines { lines -> lines.forEach { returnList.add(it)} }

            inputStream.close()
            return returnList
        }

        fun getListOfAllCommanders(context : Context?): ArrayList<String>{
            val returnList = ArrayList<String>()
            val inputStream = context?.resources?.openRawResource(R.raw.legal_legendaries_coloridentity_cmc)!!

            inputStream.bufferedReader().useLines { lines -> lines.forEach { returnList.add(it.split("|")[0])} }

            inputStream.close()
            return returnList
        }

        @SuppressLint("Recycle")
        fun getManaCostIdArray(manaCostString : String): ArrayList<Int>{
            val manaSymbolConverter = mapOf("0" to R.drawable.manacost_zero, "1" to R.drawable.manacost_one, "2" to R.drawable.manacost_two,
                    "3" to R.drawable.manacost_three, "4" to R.drawable.manacost_four, "5" to R.drawable.manacost_five, "6" to R.drawable.manacost_six,
                    "7" to R.drawable.manacost_seven, "8" to R.drawable.manacost_eight, "9" to R.drawable.manacost_nine, "10" to R.drawable.manacost_ten,
                    "11" to R.drawable.manacost_eleven, "12" to R.drawable.manacost_twelve, "13" to R.drawable.manacost_thirteen, "14" to R.drawable.manacost_fourteen,
                    "15" to R.drawable.manacost_fifteen, "16" to R.drawable.manacost_six, "17" to R.drawable.manacost_seventeen, "18" to R.drawable.manacost_eight,
                    "19" to R.drawable.manacost_nineteen, "20" to R.drawable.manacost_twenty, "W" to R.drawable.manacost_white, "U" to R.drawable.manacost_blue,
                    "B" to R.drawable.manacost_black, "R" to R.drawable.manacost_red, "G" to R.drawable.manacost_green, "W/U" to R.drawable.manacost_white_blue,
                    "W/B" to R.drawable.manacost_white_black, "U/B" to R.drawable.manacost_blue_black, "U/R" to R.drawable.manacost_blue_red, "B/R" to R.drawable.manacost_black_red,
                    "B/G" to R.drawable.manacost_black_green, "R/W" to R.drawable.manacost_red_white, "R/G" to R.drawable.manacost_red_green, "G/W" to R.drawable.manacost_green_white,
                    "G/U" to R.drawable.manacost_green_blue, "2/W" to R.drawable.manacost_colorless_white, "2/U" to R.drawable.manacost_colorless_blue, "2/B" to R.drawable.manacost_colorless_black,
                    "2/R" to R.drawable.manacost_colorless_red, "2/G" to R.drawable.manacost_colorless_green, "P/W" to R.drawable.manacost_phyrexian_white, "P/U" to R.drawable.manacost_phyrexian_blue,
                    "P/B" to R.drawable.manacost_phyrexian_black, "P/R" to R.drawable.manacost_phyrexian_red, "P/G" to R.drawable.manacost_phyrexian_green, )

            if (!manaCostString.isEmpty()) {
                val splitStringArray = manaCostString.split("{") as ArrayList<String>
                splitStringArray.removeAt(0)
                val fixedArray = splitStringArray.map { it.replace("}", "") } as ArrayList<String>

                val manaSymbolIdArray = ArrayList<Int>()

                for (item in fixedArray) {
                    manaSymbolConverter[item]?.let { manaSymbolIdArray.add(it) }
                }
                return manaSymbolIdArray
            }else{
                return ArrayList<Int>()
            }
        }

        fun deleteCommander(commanderId: Int, context: Context) {
            val allCards = getCardsInDecks(context)
            val commanders = getCommandersInDecks(context)

            //look for cards with commanderId in their list and remove
            for (card in allCards.reversed()){
                if (card.listOfCommanders.contains(commanderId)){
                    card.listOfCommanders.remove(commanderId)
                    //if card is in no decks, remove card
                    if(card.listOfCommanders.size == 0){
                        allCards.remove(card)
                    }
                }
            }
            for (commander in commanders.reversed()){
                if (commander.key == commanderId){
                    commanders.remove(commander)
                    println("commander removed")

                }
            }

            //save file if they exist
            val deckFile = File("${context.filesDir} cardsInDecks")
            if (deckFile.exists()) {
                deckFile.createNewFile()
                val newOutputStream = ObjectOutputStream(FileOutputStream(deckFile))
                newOutputStream.writeObject(allCards)
                newOutputStream.close()
            }
            val commanderFile = File("${context.filesDir} commandersInDecks")
            if (commanderFile.exists()) {
                commanderFile.createNewFile()
                val outputStream = ObjectOutputStream(FileOutputStream(commanderFile))
                outputStream.writeObject(commanders)
                outputStream.close()

            }
        }

        private fun sortCardsInDecks(cardList: ArrayList<Card>): ArrayList<Card> {
            val cardSorter : Comparator<Card> = Comparator{ c1: Card, c2: Card -> c1.cardManaCost.compareTo(c2.cardName) }
            Collections.sort(cardList, cardSorter)
            return cardList
        }

    }
}