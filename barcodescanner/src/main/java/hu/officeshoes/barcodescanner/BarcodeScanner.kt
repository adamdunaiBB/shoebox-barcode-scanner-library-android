package hu.officeshoes.barcodescanner

import android.app.Activity
import androidx.fragment.app.Fragment
import hu.officeshoes.barcodescanner.exception.BarcodeScannerException
import hu.officeshoes.barcodescanner.listener.BarcodeScannerListener
import hu.officeshoes.barcodescanner.scanner.HoneywellOldBarcodeScanner
import hu.officeshoes.barcodescanner.scanner.handler.BaseBarcodeScannerListenerHandler
import hu.officeshoes.barcodescanner.scanner.handler.SingleBarcodeScannerListenerHandler

object BarcodeScanner {

    const val VERSION_CODE = 1
    const val VERSION_NAME = "1.0.0"

    private var barcodeScannerListenerHandler: BaseBarcodeScannerListenerHandler? = null

    fun init(
        activity: Activity,
        zebraDataWedgeIntentAction: String,
        onBarcodeScannerError: (BarcodeScannerException) -> Unit
    ) {
        barcodeScannerListenerHandler = SingleBarcodeScannerListenerHandler(
            HoneywellOldBarcodeScanner(
                activity = activity,
                onBarcodeScanned = ::onBarcodeScanned,
                onBarcodeScannerError = onBarcodeScannerError
            )
        )
//        barcodeScannerListenerHandler = when (Build.BRAND) {
//            DeviceBrand.HONEYWELL.brand ->
//                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN)
//                    MultiBarcodeScannerListenerHandler(
//                        HoneywellNewBarcodeScanner(
//                            activity = activity,
//                            onBarcodeScanned = ::onBarcodeScanned,
//                            onBarcodeScannerError = onBarcodeScannerError
//                        )
//                    )
//                else
//
//            DeviceBrand.ZEBRA.brand ->
//                MultiBarcodeScannerListenerHandler(
//                    ZebraBarcodeScanner(
//                        activity = activity,
//                        onBarcodeScanned = ::onBarcodeScanned,
//                        onBarcodeScannerError = onBarcodeScannerError,
//                        zebraDataWedgeIntentAction = zebraDataWedgeIntentAction
//                    )
//                )
//            else -> {
//                onBarcodeScannerError(BarcodeScannerException("Unknown device: " + Build.BRAND))
//                null
//            }
//        }
    }

    fun registerListener(
        fragment: Fragment,
        barcodeScannerListener: BarcodeScannerListener
    ) {
        barcodeScannerListenerHandler?.registerListener(fragment, barcodeScannerListener)
    }

    fun unregisterListener(fragment: Fragment) {
        barcodeScannerListenerHandler?.unregisterListener(fragment)
    }

    fun startScanning(fragment: Fragment) {
        barcodeScannerListenerHandler?.startScanning(fragment)
    }

    private fun onBarcodeScanned(barcode: String) {
        barcodeScannerListenerHandler?.onBarcodeScanned(barcode)
    }
}
