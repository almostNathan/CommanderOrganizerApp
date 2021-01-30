package com.example.commanderorganizerapp

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
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