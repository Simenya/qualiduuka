package com.app.smartshamba

import android.content.Context
import android.os.Bundle
import android.widget.GridView
import android.widget.ImageView
import android.widget.ListView
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import com.app.smartshamba.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        //calling the list adapter function
        setListData(this)

        setSupportActionBar(binding.appBarMain.toolbar)

        val drawerLayout: DrawerLayout = binding.drawerLayout
        val navView: NavigationView = binding.navView
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        // Passing each menu ID as a set of Ids because each
        // menu should be considered as top level destinations.
        appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.homeFragment
            ), drawerLayout
        )
        setupActionBarWithNavController(navController, appBarConfiguration)
        navView.setupWithNavController(navController)
    }

    override fun onSupportNavigateUp(): Boolean {
        val navController = findNavController(R.id.nav_host_fragment_content_main)
        return navController.navigateUp(appBarConfiguration) || super.onSupportNavigateUp()
    }


    fun setListData(context: Context){
        //Generating A data source
        val itemNames:Array<String> = arrayOf(
            "Maize",
            "Tomatoes",
            "Passion Fruit",
            "Rabbits",
            "Poultry",
            "Onions"
        )
        val itemPrices:Array<String> = arrayOf(
            "2000",
            "5000",
            "20000",
            "30000",
            "40000",
            "50000"
        )

        val gridView = findViewById<ListView>(R.id.listViewId)

        val listOfArray:ArrayList<ItemDataModels> = arrayListOf()

        for (i in itemNames.indices){
            val list = ItemDataModels(itemNames[i], itemPrices[i])
            listOfArray.add(list)
        }

        val adapter = ItemAdapter(context, listOfArray)
        gridView.adapter = adapter

    }

}