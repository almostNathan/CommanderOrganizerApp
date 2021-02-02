package com.example.commanderorganizerapp

import android.app.ActionBar
import android.util.Xml
import android.view.Gravity
import androidx.recyclerview.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.navigation.findNavController

import com.example.commanderorganizerapp.dummy.DummyContent.DummyItem
import org.xmlpull.v1.XmlPullParser
import java.lang.reflect.Parameter

/**
 * [RecyclerView.Adapter] that can display a [DummyItem].
 * TODO: Replace the implementation with code for your data type.
 */
class DeckRecyclerViewAdapter(
    private val cardsInThisDeck: List<Card>,
    private val allCardsManaCost : ArrayList<ArrayList<Int>>
) : RecyclerView.Adapter<DeckRecyclerViewAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.fragment_deck_list_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val card = cardsInThisDeck[position]
        holder.cardNameView.text = card.cardName

        if (allCardsManaCost[position].size >= 0) {
            for (item in allCardsManaCost[position]) {
                val image = ImageView(holder.itemView.context)
                val size = holder.holderLayout.resources.getDimensionPixelSize(R.dimen.manacost_icon_size)
                val imgParams = LinearLayout.LayoutParams(size,size)
                image.layoutParams = imgParams
                image.setImageResource(item)
                holder.holderLayout.addView(image)
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
        val holderLayout = view.findViewById<LinearLayout>(R.id.deck_list_linear_layout)





        override fun toString(): String {
            return super.toString() + " '"
        }
    }
}