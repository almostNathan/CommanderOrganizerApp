package com.example.commanderorganizerapp

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.AutoCompleteTextView
import android.widget.Button
import androidx.appcompat.app.AlertDialog
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.NavigationUI
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.bottomnavigation.BottomNavigationView
import java.io.File
import java.io.FileOutputStream
import java.io.ObjectOutputStream

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //get our nav host fragment view
        val navHostFragment = supportFragmentManager.findFragmentById(R.id.nav_host_fragment) as NavHostFragment
        //get navController
        val navController = navHostFragment.navController
        //get BottomNavigationMenu
        val bottomNavMenu = findViewById<BottomNavigationView>(R.id.menu_bottom_nav)

        //setup bottomNavMenu with navController
        bottomNavMenu.setupWithNavController(navController)


        //prints cards in library
//        for(item in GetStuff.getCardsInDecks(this)){
//            println("${item.cardName} ${item.listOfCommanders} ${item.cardManaCost}")
//        }





//
//        BottomNavigationView.OnNavigationItemSelectedListener {
//            when(it.itemId){
//                R.id.commander_list_fragment ->
//            }
//        }
//
//        NavigationUI.onNavDestinationSelected(findViewById(R.id.commander_list_fragment), navController)
//        NavigationUI.onNavDestinationSelected(findViewById(R.id.card_list_fragment), navController)
//        NavigationUI.onNavDestinationSelected(findViewById(R.id.search_cards_fragment), navController)

        makeStarterData()
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        val id = item.itemId

        if (id == R.id.reset_decks) {
            //set up AlertDialog:dialogbuilder to be used later
            val cmdrNameAlertDialog = AlertDialog.Builder(this as Context).create()
            //inflate the enter_cmdr_dialog View
            val dialogView = layoutInflater.inflate(R.layout.reset_deck_dialog, null)


            val buttonSubmit: Button = dialogView.findViewById(R.id.reset_dialog_submit)
            val buttonCancel: Button = dialogView.findViewById(R.id.reset_dialot_cancel)

            buttonCancel.setOnClickListener {
                cmdrNameAlertDialog.dismiss()
            }

            buttonSubmit.setOnClickListener {

                File("${applicationContext.filesDir} cardsInDecks").delete()
                File("${applicationContext.filesDir} commandersInDecks").delete()
                makeStarterData()
                cmdrNameAlertDialog.dismiss()



            }
            cmdrNameAlertDialog.setView(dialogView)
            cmdrNameAlertDialog.show()
        }
        return true
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.top_options_menu, menu)

        return true
    }

    private fun makeStarterData(){
        val listOfCards = ArrayList<Card>()
        val starterCommanders = ArrayList<Commander>()
        starterCommanders.add(Commander(0, "Obosh, the Preypiercer"))

        val file = File("${applicationContext.filesDir} cardsInDecks")
        if (!file.exists()) {
            file.createNewFile()
            val newOutputStream = ObjectOutputStream(FileOutputStream(file))
            newOutputStream.writeObject(listOfCards)
            newOutputStream.close()
        }
        val newFile = File("${applicationContext.filesDir} commandersInDecks")
        if (!newFile.exists()) {
            newFile.createNewFile()
            val outputStream = ObjectOutputStream(FileOutputStream(newFile))
            outputStream.writeObject(starterCommanders)
            outputStream.close()

        }
    }


}