package com.example.commanderorganizerapp

import android.app.Application
import android.content.Context
import android.content.res.Resources
import androidx.core.content.ContextCompat
import java.io.FileInputStream
import java.io.ObjectInputStream

class GetStuff {
    companion object {
        fun getCardsInDecks(context : Context?): ArrayList<Card>{
            val cardsInDecks : ArrayList<Card>
            val cardInputStream = ObjectInputStream(FileInputStream("${context?.filesDir} cardsInDecks"))
            cardsInDecks = cardInputStream.readObject() as ArrayList<Card>
            cardInputStream.close()
            return cardsInDecks
        }

        fun getCommandersInDecks(context : Context?): ArrayList<Commander>{
            val cmdrInputStream = ObjectInputStream(FileInputStream("${context?.filesDir} commandersInDecks"))
            val commandersInDecks = cmdrInputStream.readObject() as ArrayList<Commander>
            cmdrInputStream.close()
            return commandersInDecks
        }

        fun getListOfAllCards(context : Context?): ArrayList<String>{
            val returnList = ArrayList<String>()
            val inputStream = context?.resources?.openRawResource(R.raw.legal_allcards_coloridentity_cmc)!!

            inputStream.bufferedReader().useLines { lines -> lines.forEach { returnList.add(it.split("|")[0])} }

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

    }
}