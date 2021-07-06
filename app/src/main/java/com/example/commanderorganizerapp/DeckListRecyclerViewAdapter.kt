package com.example.commanderorganizerapp

import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController

import com.example.commanderorganizerapp.dummy.DummyContent.DummyItem

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class DeckListRecyclerViewAdapter(
    private val cardsInThisDeck: List<Card>,
) : RecyclerView.Adapter<DeckListRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_deck_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cardsInThisDeck[position]
        holder.cardNameView.text = card.cardName
        println(holder.holderLayout.childCount)


        //draw manacost  PROBLEM redraws manacost on scroll
        //if this rows # of children views less than 1
        //TODO mana costs are not lined up and shift
        if (card.manaSymbolArray.size >= 0 && holder.holderLayout.childCount == 1) {
            for (item in card.manaSymbolArray) {
                val image = ImageView(holder.itemView.context)
                //get the preset manaSymbol size
                val size = holder.holderLayout.resources.getDimensionPixelSize(R.dimen.manacost_icon_size)
                //set viewholder size
                image.layoutParams = LinearLayout.LayoutParams(size,size)
                //put image in the frame
                image.setImageResource(item)
                //add the view
                holder.holderLayout.addView(image)
                println("manaCostPosition $position")
            }
        }



        holder.cardNameView.setOnClickListener {

            val action = DeckListFragmentDirections.actionCardListFragmentToCardDetailFragment(cardsInThisDeck[position])
            it.findNavController().navigate(action)

        }
    }

    override fun getItemCount(): Int = cardsInThisDeck.size

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val cardNameView: TextView = view.findViewById(R.id.card_name)
        val holderLayout: LinearLayout = view.findViewById<LinearLayout>(R.id.deck_list_linear_layout)





        override fun toString(): String {
            return super.toString() + " '"
        }
    }
}