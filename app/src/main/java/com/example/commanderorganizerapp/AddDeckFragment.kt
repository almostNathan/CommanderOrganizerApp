package com.example.commanderorganizerapp

import android.content.Context
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.FragmentManager
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs




class AddDeckFragment : Fragment() {
    private var commanderName :String= ""
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val theFragment = inflater.inflate(R.layout.fragment_add_deck,container,false)


        // Inflate the layout for this fragment
        return theFragment


        //DIALOG FOR ADDING COMMANDER NOT WORKING - FRAGMENT DRAWING BEFORE DIALOG CAN RESOLVE, CMDR NAME NOT BEING ADDED

////set up AlertDialog:dialogbuilder to be used later
//        val cmdrNameAlertDialog = AlertDialog.Builder(context as Context).create()
//        //inflate the enter_cmdr_dialog View
//        val dialogView = layoutInflater.inflate(R.layout.enter_cmdr_dialog, null)
//        //get autocompleteView
//        val enterCMDR: AutoCompleteTextView = dialogView.findViewById(R.id.cmdr_name)
//        val adapter =  ArrayAdapter(context as Context, android.R.layout.simple_list_item_1, GetStuff.getListOfAllCommanders(context))
//        enterCMDR.setAdapter(adapter)
//
//        val buttonSubmit: Button = dialogView.findViewById(R.id.buttonSubmit)
//        val buttonCancel: Button = dialogView.findViewById(R.id.buttonCancel)
//
//        buttonCancel.setOnClickListener {
//            findNavController().popBackStack()
//            cmdrNameAlertDialog.dismiss()
//        }
//
//        buttonSubmit.setOnClickListener {
//            commanderName = enterCMDR.text.toString()
//            println(commanderName)
//            cmdrNameAlertDialog.dismiss()
//        }
//        cmdrNameAlertDialog.setView(dialogView)
//        cmdrNameAlertDialog.show()
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val addCardView = view.findViewById<AutoCompleteTextView>(R.id.add_card_auto_complete)
        val addCardButton = view.findViewById<Button>(R.id.add_card_button)
        val deckListEditText = view.findViewById<EditText>(R.id.add_deck_edit_text)
        val submitButton = view.findViewById<Button>(R.id.submit_deck)
        val cardCountView = view.findViewById<TextView>(R.id.card_count_text_view)

        //set hint to add commander if no cards are in deck
        if ("*CMDR*" !in deckListEditText.text.toString()){
            addCardView.hint = "Add Your Commander"
        }else addCardView.hint = "Add Card"

        //set up adapter for autocomplete
        addCardView.setAdapter(ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, GetStuff.getListOfAllCards(context)))

        //add card to decklist listener
        addCardButton.setOnClickListener {
            if(deckListEditText.text.isEmpty()) deckListEditText.append("*CMDR* ${addCardView.text.toString()}")
            else deckListEditText.append("\n1x ${addCardView.text.toString()}")
            addCardView.setText("")
            var cardCount = 0
            for (line in deckListEditText.text.toString().split("\n")){
                when(val numberOfCard = line.split(" ")[0]){
                    "*CMDR*" ->  cardCount = cardCount.inc()
                    "1x" ->  cardCount = cardCount.inc()
                    else ->  cardCount = cardCount.plus(numberOfCard.substring(0, (numberOfCard.length-1)).toInt())
                }
            }
            println(cardCount)
            cardCountView.text = cardCount.toString()
        }

        submitButton.setOnClickListener {
            val decklist = deckListEditText.text.toString()

            DeckListAdder(decklist, view.context).addDeck()
            val action = AddDeckFragmentDirections.actionAddDeckFragmentToCommanderListFragment()
            view.findNavController().navigate(action)


        }

    }







}