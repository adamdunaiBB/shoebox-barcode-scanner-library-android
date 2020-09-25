package hu.officeshoes.barcode.barcodescanner

import android.app.Activity
import androidx.fragment.app.Fragment

interface BaseBarcodeScanner {

    val activity: Activity
    val onBarcodeScanned: (String) -> Unit
    val onBarcodeScannerError: (BarcodeScannerException) -> Unit

    fun startScanning(fragment: Fragment)

    fun registerBarcodeScanner()

    fun unregisterBarcodeScanner()

    fun onRegisterListener()
}
