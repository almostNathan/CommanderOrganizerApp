package com.example.commanderorganizerapp

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.navigation.findNavController
import androidx.recyclerview.widget.RecyclerView

class SearchRecyclerAdapter(
    private val searchResults: ArrayList<Card>
) : RecyclerView.Adapter<SearchRecyclerAdapter.ViewHolder>() {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.fragment_search_adapter, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val result = searchResults[position]
        holder.resultView.text = result.cardName
        holder.resultView.setOnClickListener {
            val action = SearchFragmentDirections.actionSearchCardsFragmentToCardDetailFragment(result)
            it.findNavController().navigate(action)
        }

        //alternate colors of backgrounds
        if (position % 2 == 1){
            holder.resultView.setBackgroundColor(ContextCompat.getColor(holder.resultView.context, R.color.card_background_1))
        }else{
            holder.resultView.setBackgroundColor(ContextCompat.getColor(holder.resultView.context, R.color.card_background_2))
        }


    }

    override fun getItemCount(): Int = searchResults.size


    //inner class
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val resultView : TextView = view.findViewById(R.id.search_result_text_view)

        override fun toString(): String {
            return super.toString() + " '"
        }

    }
}