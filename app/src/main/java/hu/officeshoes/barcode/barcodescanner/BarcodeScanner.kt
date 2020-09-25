package hu.officeshoes.barcode.barcodescanner

import android.app.Activity
import android.os.Build
import androidx.fragment.app.Fragment
import java.lang.ref.WeakReference

object BarcodeScanner {

    private var barcodeScanner: BaseBarcodeScanner? = null
    private val barcodeScannerListenerMap =
        mutableMapOf<String, WeakReference<BarcodeScannerListener>>()

    fun init(
        activity: Activity,
        zebraDataWedgeIntentAction: String,
        onBarcodeScannerError: (BarcodeScannerException) -> Unit
    ) {
        barcodeScanner = when (Build.BRAND) {
            DeviceBrand.ZEBRA.brand ->
                ZebraBarcodeScanner(
                    activity = activity,
                    onBarcodeScanned = ::onBarcodeScanned,
                    onBarcodeScannerError = onBarcodeScannerError,
                    zebraDataWedgeIntentAction = zebraDataWedgeIntentAction
                )
            else -> {
                onBarcodeScannerError(BarcodeScannerException("Unknown device: " + Build.BRAND))
                null
            }
        }
    }

    fun registerListener(
        fragment: Fragment,
        barcodeScannerListener: BarcodeScannerListener
    ) {
        if (barcodeScannerListenerMap.isEmpty()) {
            barcodeScanner?.registerBarcodeScanner()
        }

        barcodeScannerListenerMap[getFragmentName(fragment)] = WeakReference(barcodeScannerListener)

        barcodeScanner?.onRegisterListener()
    }

    fun unregisterListener(fragment: Fragment) {
        barcodeScannerListenerMap.remove(getFragmentName(fragment))

        if (barcodeScannerListenerMap.isEmpty()) {
            barcodeScanner?.unregisterBarcodeScanner()
        }
    }

    private fun getFragmentName(fragment: Fragment) = fragment::class.java.name

    fun startScanning(fragment: Fragment) {
        barcodeScanner?.startScanning(fragment)
    }

    private fun onBarcodeScanned(barcode: String) {
        for (weakReferenceToListener in barcodeScannerListenerMap.values) {
            weakReferenceToListener.get()?.onBarcodeScanned(barcode)
        }
    }
}
