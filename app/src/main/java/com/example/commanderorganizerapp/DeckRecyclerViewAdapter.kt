package com.example.commanderorganizerapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.navigation.findNavController

import com.example.commanderorganizerapp.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class DeckRecyclerViewAdapter(
    private val cardsInThisDeck: List<Card>
) : RecyclerView.Adapter<DeckRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_deck_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cardsInThisDeck[position]
        holder.cardNameView.text = card.cardName

        holder.cardNameView.setOnClickListener {

            val action = DeckListFragmentDirections.actionCardListFragmentToCardDetailFragment(cardsInThisDeck[position])
            it.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int = cardsInThisDeck.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardNameView: TextView = view.findViewById(R.id.card_name)

        override fun toString(): String {
            return super.toString() + " '"
        }
    }
}