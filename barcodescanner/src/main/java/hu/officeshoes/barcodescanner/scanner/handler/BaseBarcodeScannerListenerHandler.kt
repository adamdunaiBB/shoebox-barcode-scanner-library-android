package hu.officeshoes.barcodescanner.scanner.handler

import androidx.fragment.app.Fragment
import hu.officeshoes.barcodescanner.listener.BarcodeScannerListener
import hu.officeshoes.barcodescanner.scanner.BaseBarcodeScanner

internal interface BaseBarcodeScannerListenerHandler {

    val barcodeScanner: BaseBarcodeScanner

    fun registerListener(fragment: Fragment, barcodeScannerListener: BarcodeScannerListener)

    fun unregisterListener(fragment: Fragment)

    fun startScanning(fragment: Fragment)

    fun onBarcodeScanned(barcode: String)
}
