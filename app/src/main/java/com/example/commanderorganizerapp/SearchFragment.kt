package com.example.commanderorganizerapp

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.recyclerview.widget.RecyclerView

// TODO: Rename parameter arguments, choose names that match
// the fragment initialization parameters, e.g. ARG_ITEM_NUMBER

/**
 * A simple [Fragment] subclass.
 * Use the [search.newInstance] factory method to
 * create an instance of this fragment.
 */
class SearchFragment : Fragment() {
    // TODO: Rename and change types of parameters
    private var param1: String? = null
    private var param2: String? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.let {
        }
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_search, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val searchAutoComplete = view.findViewById<AutoCompleteTextView>(R.id.search_auto_complete)
        val searchButton = view.findViewById<Button>(R.id.search_button)
        val searchResults = view.findViewById<RecyclerView>(R.id.search_results)
        val searchAutoCompleteFilter = ArrayList<Card>()

        for (card in GetStuff.getCardsInDecks(context)){
            searchAutoCompleteFilter.add(card)
        }
        searchAutoComplete.setAdapter(ArrayAdapter<String>(requireContext(), android.R.layout.simple_list_item_1, searchAutoCompleteFilter.map { c -> c.cardName }))

        searchButton.setOnClickListener {
            val searchResultsArrayList = ArrayList<Card>()
            for (item in searchAutoCompleteFilter){
                if (searchAutoComplete.text in item.cardName){
                    searchResultsArrayList.add(item)
                }
            }
            searchResults.adapter = SearchRecyclerAdapter(searchResultsArrayList)
        }

    }


}