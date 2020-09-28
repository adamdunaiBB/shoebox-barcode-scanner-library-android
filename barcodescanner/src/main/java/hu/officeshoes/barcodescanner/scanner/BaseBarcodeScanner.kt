package hu.officeshoes.barcodescanner.scanner

import android.app.Activity
import androidx.fragment.app.Fragment
import hu.officeshoes.barcodescanner.exception.BarcodeScannerException

internal interface BaseBarcodeScanner {

    val activity: Activity
    val onBarcodeScanned: (String) -> Unit
    val onBarcodeScannerError: (BarcodeScannerException) -> Unit

    fun startScanning(fragment: Fragment)

    fun registerBarcodeScanner()

    fun unregisterBarcodeScanner()

    fun onRegisterListener()
}
