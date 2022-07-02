package com.app.smartshamba

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.cardview.widget.CardView
import androidx.core.content.ContextCompat
import com.google.zxing.integration.android.IntentIntegrator
import org.json.JSONException
import android.Manifest
import android.content.pm.PackageManager
import androidx.core.app.ActivityCompat
import com.journeyapps.barcodescanner.CaptureActivity

class ScanCode : AppCompatActivity() {
    var cardView1: CardView? = null
    var cardView2: CardView? = null
    var btnEnterCode: Button? = null
    var codeContent: TextView? = null
    var tvText: TextView? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scan_code)

        cardView1 = findViewById(R.id.cardView1)
        cardView2 = findViewById(R.id.cardView2)
        codeContent = findViewById(R.id.code2ValueId)
        tvText = findViewById(R.id.tvText)


        cameraTask()

    }

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
        qrScanner.setOrientationLocked(false)
        qrScanner.setBeepEnabled(true)
        qrScanner.captureActivity = CaptureActivity::class.java
        qrScanner.initiateScan()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        if (result != null) {
            if (result.contents == null) {
                Toast.makeText(this, "Result Not Found", Toast.LENGTH_SHORT).show()
                codeContent!!.text = ""
            } else {
                val qrContent = findViewById<TextView>(R.id.code2ValueId)
                try {
                    codeContent!!.text = (result.contents.toString())
                } catch (exception: JSONException) {
                    Toast.makeText(this, exception.localizedMessage, Toast.LENGTH_SHORT).show()
                    codeContent!!.text = ""
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