package com.example.commanderorganizerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView

/**
 * A fragment representing a list of Items.
 */
class DeckListFragment : Fragment() {

        var commanderKey : Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        
        commanderKey = (arguments?.get("commanderKey") as Int?)!!

    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val viewList = view.findViewById<RecyclerView>(R.id.card_list)
        val commanderNameView = view.findViewById<TextView>(R.id.deck_list_commander_name)

        //get commander name from the key passed and set it to the textview
        val commanderName = GetStuff.getCommandersInDecks(view.context).filter { it.key == commanderKey }[0].value
        commanderNameView.text = commanderName
        //create background gradient for title
        commanderNameView.background = CreateGradient(view.context, commanderName).getBackgroundGradient()

        val cardsInAllDecks = GetStuff.getCardsInDecks(context)
        val cardsInCurrentDeck = ArrayList<Card>()
        for (card in cardsInAllDecks){
            if (commanderKey in card.listOfCommanders){
                cardsInCurrentDeck.add(card)
            }
        }
        val allCardsManaCost = ArrayList<ArrayList<Int>>()
        for(card in cardsInCurrentDeck){
            allCardsManaCost.add(GetStuff.getManaCostIdArray(card.cardManaCost))
        }
        viewList.adapter = DeckListRecyclerViewAdapter(cardsInCurrentDeck, allCardsManaCost)

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.fragment_deck_list, container, false)



        return view
    }

    companion object {

        // TODO: Customize parameter argument names
        const val ARG_COLUMN_COUNT = "column-count"

        // TODO: Customize parameter initialization
        @JvmStatic
        fun newInstance(columnCount: Int) =
            DeckListFragment().apply {
                arguments = Bundle().apply {
                    putInt(ARG_COLUMN_COUNT, columnCount)
                }
            }
    }
}