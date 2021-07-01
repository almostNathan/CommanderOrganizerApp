package com.example.commanderorganizerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup

/**
 * A fragment representing a list of Items.
 */
class CommanderListFragment : Fragment() {


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        val view = inflater.inflate(R.layout.fragment_commander_list, container, false)
        val viewList = view.findViewById<RecyclerView>(R.id.commander_list)
        val commanderList = GetStuff.getCommandersInDecks(context)



        val theAdapter = CommanderRecyclerViewAdapter(commanderList)
        viewList.adapter= theAdapter


        return view
    }

    companion object {


    }
}