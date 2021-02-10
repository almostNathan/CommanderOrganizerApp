package com.example.commanderorganizerapp

import android.app.Application
import android.content.Context
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.View.inflate
import android.view.ViewGroup
import android.widget.Button
import android.widget.PopupMenu
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.core.content.res.ColorStateListInflaterCompat.inflate
import androidx.navigation.Navigation
import androidx.navigation.findNavController

import com.example.commanderorganizerapp.dummy.DummyContent.DummyItem
import kotlinx.coroutines.job
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
        holder.commander.text = theCommander.value
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

        holder.optionsMenu.setOnClickListener {
            val popupMenu = PopupMenu(holder.commander.context, holder.optionsMenu)
            popupMenu.inflate(R.menu.commander_three_dot_menu)
            popupMenu.setOnMenuItemClickListener {
                when (it.itemId){
                    R.id.delete_deck -> {
                        buildConfirmDialog(theCommander.key, holder.commander.context)
                        true
                    }

                    else -> {
                        println("miss")
                        true
                    }
                }
            }
            popupMenu.show()
        }

    }

    override fun getItemCount(): Int = commanderList.size

    private fun buildConfirmDialog(commanderId : Int, context: Context){
        val cmdrNameAlertDialog = AlertDialog.Builder(context).create()
        //inflate the enter_cmdr_dialog View
        val dialogView = LayoutInflater.from(context).inflate(R.layout.confirmation_dialog, null)
        //get autocompleteView

        val buttonSubmit: Button = dialogView.findViewById(R.id.delete_confirmation_submit)
        val buttonCancel: Button = dialogView.findViewById(R.id.delete_confirmation_cancel)

        buttonSubmit.setOnClickListener {
            GetStuff.deleteCommander(commanderId, context)
            cmdrNameAlertDialog.dismiss()
        }
        buttonCancel.setOnClickListener {
            cmdrNameAlertDialog.dismiss()
        }


        cmdrNameAlertDialog.setView(dialogView)
        cmdrNameAlertDialog.show()
    }




    //inner class
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val commander : TextView = view.findViewById(R.id.commander_name)
        val optionsMenu: TextView = view.findViewById<TextView>(R.id.commander_options_menu)

        override fun toString(): String {
            return super.toString() + " '"
        }

    }
}