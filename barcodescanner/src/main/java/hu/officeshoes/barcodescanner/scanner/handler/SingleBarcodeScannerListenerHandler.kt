package hu.officeshoes.barcodescanner.scanner.handler

import androidx.fragment.app.Fragment
import hu.officeshoes.barcodescanner.listener.BarcodeScannerListener
import hu.officeshoes.barcodescanner.scanner.BaseBarcodeScanner
import java.lang.ref.WeakReference

internal class SingleBarcodeScannerListenerHandler(
    override val barcodeScanner: BaseBarcodeScanner
) : BaseBarcodeScannerListenerHandler {

    private var singleBarcodeScannerListener: WeakReference<BarcodeScannerListener>? = null

    override fun registerListener(
        fragment: Fragment,
        barcodeScannerListener: BarcodeScannerListener
    ) {
        singleBarcodeScannerListener = WeakReference(barcodeScannerListener)

        barcodeScanner.registerBarcodeScanner()
        barcodeScanner.onRegisterListener()
    }

    override fun unregisterListener(fragment: Fragment) {
        barcodeScanner.unregisterBarcodeScanner()
    }

    override fun startScanning(fragment: Fragment) {
        barcodeScanner.startScanning(fragment)
    }

    override fun onBarcodeScanned(barcode: String) {
        singleBarcodeScannerListener?.get()?.onBarcodeScanned(barcode)
    }
}
