package com.example.commanderorganizer

import android.content.Intent
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView
import com.example.commanderorganizerapp.CardDetailFragmentDirections
import com.example.commanderorganizerapp.Commander
import com.example.commanderorganizerapp.R

class CardDetailRecyclerAdapter (private val commanderArrayList : ArrayList<Commander>) : RecyclerView.Adapter<CardDetailRecyclerAdapter.ViewHolder>(){

    class ViewHolder(val view : View) : RecyclerView.ViewHolder(view){
        val commanderName = view.findViewById<TextView?>(R.id.card_detail_commander_name)
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_card_detail_list_adapter, parent, false)
        return ViewHolder(view)
    }



    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.commanderName.text = commanderArrayList[position].value
        holder.commanderName.append(commanderArrayList[position].key.toString())

        holder.commanderName.setOnClickListener {

            val action = CardDetailFragmentDirections.actionCardDetailFragmentToDeckListFragment(commanderArrayList[position].key)
            it.findNavController().navigate(action)

        }

    }

    override fun getItemCount(): Int {
        return commanderArrayList.size
    }


}