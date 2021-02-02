package com.example.commanderorganizerapp

import android.annotation.SuppressLint
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.android.volley.Request
import com.android.volley.toolbox.StringRequest
import com.android.volley.toolbox.Volley
import com.example.commanderorganizer.CardDetailRecyclerAdapter
import com.squareup.picasso.Picasso
import com.google.gson.JsonObject
import com.google.gson.JsonParser

class CardDetailFragment : Fragment() {

    var passedCard : Card = Card("name" ,"","", -1)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        passedCard = arguments?.get("passedCard") as Card

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_card_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        val commanderArrayList = GetStuff.getCommandersInDecks(view.context)
        val cardScreenCommanderList = ArrayList<Commander>()
        drawCardImage(passedCard, view)

        for (item in commanderArrayList) {
            if (item.key in passedCard.listOfCommanders) {
                cardScreenCommanderList.add(item)
            }
        }

        //get views
        val cardScreenCardName = view.findViewById<TextView>(R.id.card_detail_card_name)
        val commanderNameRecyclerView = view.findViewById<RecyclerView>(R.id.card_detail_commander_list_view)

        //set commander name gradient background
        cardScreenCardName.background = CreateGradient(view.context, passedCard.cardName).getBackgroundGradient()
        cardScreenCardName.text = passedCard.cardName

        //set up the adapter for the RecyclerView
        commanderNameRecyclerView.adapter = CardDetailRecyclerAdapter(cardScreenCommanderList)
        commanderNameRecyclerView.layoutManager = LinearLayoutManager(view.context)

        if (cardScreenCommanderList.size % 2 == 0) {
            commanderNameRecyclerView.setBackgroundColor(ContextCompat.getColor(commanderNameRecyclerView.context, R.color.card_background_1))
        } else {
            commanderNameRecyclerView.setBackgroundColor(ContextCompat.getColor(commanderNameRecyclerView.context, R.color.card_background_2))
        }

    }

    private fun drawCardImage(passedCard: Card, view : View) {
        val myQueue = Volley.newRequestQueue(view.context)
        val url = "https://api.scryfall.com/cards/named?fuzzy=${passedCard.cardName.replace(" ", "").replace("'", "")}"

        val cardStringRequest = StringRequest(
            Request.Method.GET, url,
            { response ->
                val cardJsonObject = (JsonParser.parseString(response).asJsonObject)
                val urlList = cardJsonObject.getAsJsonObject("image_uris")
                //check if card is 2 sided
                //will return null because API has 2 "image_uris" items
                if (urlList == null){
                    addFlipCardImage(cardJsonObject)
                }else {
                    val imageUrl = urlList.get("png").asString
                    val cardImage = view.findViewById<ImageView>(R.id.card_image)
                    Picasso.get()
                        .load(imageUrl)
                        .into(cardImage)
                }
            },
            {
                println("FAILED")
            })

        myQueue.add(cardStringRequest)
    }

    @SuppressLint("ClickableViewAccessibility")
    fun addFlipCardImage(cardJson: JsonObject){
        val arrayItem = cardJson.getAsJsonArray("card_faces")
        //get front face image
        val frontFace = arrayItem.get(0).asJsonObject
        val frontFaceUrlList = frontFace.getAsJsonObject("image_uris")
        val frontFaceImageUrl = frontFaceUrlList.get("png").asString
        //get back face image
        val backFace = arrayItem.get(1).asJsonObject
        val backFaceUrlList = backFace.getAsJsonObject("image_uris")
        val backFaceImageUrl = backFaceUrlList.get("png").asString



        val cardImage = view?.findViewById<ImageView>(R.id.card_image)!!
        Picasso.get()
            .load(frontFaceImageUrl)
            .into(cardImage)

        cardImage.setOnTouchListener { v , event ->
            when (event.action) {
                MotionEvent.ACTION_DOWN -> {
                    Picasso.get()
                        .load(backFaceImageUrl)
                        .into(view?.findViewById<ImageView>(R.id.card_image))
                    return@setOnTouchListener true
                }
                MotionEvent.ACTION_UP -> {
                    Picasso.get()
                        .load(frontFaceImageUrl)
                        .into(view?.findViewById<ImageView>(R.id.card_image))
                    return@setOnTouchListener true

                }
            }
            return@setOnTouchListener false

        }

    }
}


