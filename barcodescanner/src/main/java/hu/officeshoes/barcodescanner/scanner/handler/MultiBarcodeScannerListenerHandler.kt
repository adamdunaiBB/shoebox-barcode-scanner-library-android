package hu.officeshoes.barcodescanner.scanner.handler

import androidx.fragment.app.Fragment
import hu.officeshoes.barcodescanner.listener.BarcodeScannerListener
import hu.officeshoes.barcodescanner.scanner.BaseBarcodeScanner
import java.lang.ref.WeakReference

internal class MultiBarcodeScannerListenerHandler(
    override val barcodeScanner: BaseBarcodeScanner
) : BaseBarcodeScannerListenerHandler {

    private val barcodeScannerListenerMap =
        mutableMapOf<String, WeakReference<BarcodeScannerListener>>()

    override fun registerListener(
        fragment: Fragment,
        barcodeScannerListener: BarcodeScannerListener
    ) {
        if (barcodeScannerListenerMap.isEmpty()) {
            barcodeScanner.registerBarcodeScanner()
        }

        barcodeScannerListenerMap[getFragmentName(fragment)] = WeakReference(barcodeScannerListener)

        barcodeScanner.onRegisterListener()
    }

    private fun getFragmentName(fragment: Fragment) = fragment::class.java.name

    override fun unregisterListener(fragment: Fragment) {
        barcodeScannerListenerMap.remove(getFragmentName(fragment))

        if (barcodeScannerListenerMap.isEmpty()) {
            barcodeScanner.unregisterBarcodeScanner()
        }
    }

    override fun startScanning(fragment: Fragment) {
        barcodeScanner.startScanning(fragment)
    }

    override fun onBarcodeScanned(barcode: String) {
        for (weakReferenceToListener in barcodeScannerListenerMap.values) {
            weakReferenceToListener.get()?.onBarcodeScanned(barcode)
        }
    }
}
