package com.app.smartshamba

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.View
import android.widget.*
import com.google.android.material.navigation.NavigationView
import androidx.navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.navigateUp
import androidx.navigation.ui.setupActionBarWithNavController
import androidx.navigation.ui.setupWithNavController
import androidx.drawerlayout.widget.DrawerLayout
import androidx.appcompat.app.AppCompatActivity
import androidx.cardview.widget.CardView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.core.text.isDigitsOnly
import com.app.smartshamba.databinding.ActivityMainBinding
import com.google.zxing.integration.android.IntentIntegrator
import com.journeyapps.barcodescanner.CaptureActivity
import org.json.JSONException

class MainActivity : AppCompatActivity() {

    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var binding: ActivityMainBinding

    private lateinit var scanProduct:ImageView
    private lateinit var cardScaProduct:CardView
    lateinit var layoutItems:RelativeLayout
    private var itemValue:TextView? = null
    lateinit var bottomView:RelativeLayout
    lateinit var total:TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        scanProduct = findViewById(R.id.scanQrCodeId)
        cardScaProduct = findViewById(R.id.cardView1)
        layoutItems = findViewById(R.id.linearLayoutItems)
        itemValue = findViewById(R.id.codeContentsId)
        bottomView = findViewById(R.id.textBtnBottomId)
        total = findViewById(R.id.totalId)

        //Scan Qr Code
        scanProduct.setOnClickListener {
            cardScaProduct.visibility = View.GONE
            layoutItems.visibility = View.GONE
            bottomView.visibility = View.GONE

            cameraTask()
        }


        //calling the list adapter function
        //setListData(this)

        //Navigation Drawer
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


    fun setListData(context: Context, itemName:String, itemPrice:String){
        //Generating A data source
        val itemNames = ArrayList<String>()
        itemNames.add(itemName)
        val itemPrices = ArrayList<String>()
        itemPrices.add(itemPrice)

        val gridView = findViewById<ListView>(R.id.listViewId)

        val listOfArray:ArrayList<ItemDataModels> = arrayListOf()

        for (i in itemNames.indices){
            val list = ItemDataModels(itemNames[i], itemPrices[i])
            listOfArray.add(list)
        }

        val adapter = ItemAdapter(context, listOfArray)
        gridView.adapter = adapter

    }

    //setting up the scanner
    private fun hasCameraAccess(): Boolean {
        return ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED
    }
    private fun requestCameraPermissions(){
        ActivityCompat.requestPermissions(this, arrayOf(Manifest.permission.CAMERA), 101)
    }

    private fun cameraTask() {

        if (hasCameraAccess()) {
            scanQr()
        } else {
            requestCameraPermissions()
            scanQr()
        }
    }

    private fun scanQr(){
        val qrScanner = IntentIntegrator(this)
        qrScanner.setPrompt("QR code Scanning...")
        qrScanner.setCameraId(0)
        qrScanner.setOrientationLocked(true)
        qrScanner.setBeepEnabled(true)
        qrScanner.captureActivity = CaptureActivity::class.java
        qrScanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)

        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_SHORT).show()
                itemValue!!.text = ""
                cardScaProduct.visibility = View.VISIBLE
                layoutItems.visibility = View.VISIBLE
                bottomView.visibility = View.VISIBLE

            } else {

                try {
                    val content = result.contents
                    val splitContent = content.split(" ")
                    val itemName = splitContent[0]
                    val itemPrice = splitContent[splitContent.lastIndex]
                    var totals = 0

                    if (splitContent.size > 1){
                        if (itemPrice.isDigitsOnly()){
                            for (i in content){
                                setListData(this, itemName, itemPrice)
                            }
                            totals += itemPrice.toInt()
                            total.text = totals.toString()

                            cardScaProduct.visibility = View.VISIBLE
                            layoutItems.visibility = View.VISIBLE
                            bottomView.visibility = View.VISIBLE

                        }else{
                            Toast.makeText(this, "This Item Has no Price Tag", Toast.LENGTH_LONG).show()
                            cardScaProduct.visibility = View.VISIBLE
                            layoutItems.visibility = View.VISIBLE
                            bottomView.visibility = View.VISIBLE
                        }
                    }else{
                        if (splitContent.isEmpty()){
                            Toast.makeText(this, "This Item Has No \nName and Price Tag", Toast.LENGTH_LONG).show()
                            cardScaProduct.visibility = View.VISIBLE
                            layoutItems.visibility = View.VISIBLE
                            bottomView.visibility = View.VISIBLE
                        }else{
                            Toast.makeText(this, "This Item Has no Price Tag", Toast.LENGTH_LONG).show()
                            cardScaProduct.visibility = View.VISIBLE
                            layoutItems.visibility = View.VISIBLE
                            bottomView.visibility = View.VISIBLE
                        }
                    }

                } catch (exception: JSONException) {
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    itemValue!!.text = ""
                    cardScaProduct.visibility = View.VISIBLE
                    layoutItems.visibility = View.VISIBLE
                    bottomView.visibility = View.VISIBLE
                }

            }
        } else {
            super.onActivityResult(requestCode, resultCode, data)
        }

//        if (requestCode == AppSettingsDialog.DEFAULT_SETTINGS_REQ_CODE) {
//            Toast.makeText(this, "Permission Granted", Toast.LENGTH_SHORT).show()
//        }
    }

}