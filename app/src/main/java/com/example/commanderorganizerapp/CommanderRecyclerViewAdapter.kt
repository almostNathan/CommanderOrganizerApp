package com.example.commanderorganizerapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController

import com.example.commanderorganizerapp.dummy.DummyContent.DummyItem
import kotlin.coroutines.coroutineContext

class CommanderRecyclerViewAdapter(
    private val commanderList: List<Commander>
) : RecyclerView.Adapter<CommanderRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_commander_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val theCommander = commanderList[position]
        holder.commander.text = theCommander.value.toString()
        holder.commander.append(" ${theCommander.key}")
        holder.commander.setOnClickListener {
            val action = CommanderListFragmentDirections.actionCommanderListFragmentToCardListFragment(theCommander.key)
            it.findNavController().navigate(action)
        }

        //alternate colors of backgrounds
        if (position % 2 == 1){
            holder.commander.setBackgroundColor(ContextCompat.getColor(holder.commander.context, R.color.card_background_1))
        }else{
            holder.commander.setBackgroundColor(ContextCompat.getColor(holder.commander.context, R.color.card_background_2))
        }


    }

    override fun getItemCount(): Int = commanderList.size


    //inner class
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commander : TextView = view.findViewById(R.id.commander_name)

        override fun toString(): String {
            return super.toString() + " '"
        }

    }
}