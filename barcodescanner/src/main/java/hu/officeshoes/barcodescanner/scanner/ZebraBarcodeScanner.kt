package hu.officeshoes.barcodescanner.scanner

import android.app.Activity
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.fragment.app.Fragment

class ZebraBarcodeScanner(
    override val activity: Activity,
    override val onBarcodeScanned: (String) -> Unit,
    override val onBarcodeScannerError: (BarcodeScannerException) -> Unit,
    private val zebraDataWedgeIntentAction: String
) : BaseBarcodeScanner {

    companion object {
        // @formatter:off
        private const val DATA_STRING_TAG = "com.motorolasolutions.emdk.datawedge.data_string"
        private const val ACTION_SOFTSCANTRIGGER =
            "com.motorolasolutions.emdk.datawedge.api.ACTION_SOFTSCANTRIGGER"
        private const val EXTRA_PARAM = "com.motorolasolutions.emdk.datawedge.api.EXTRA_PARAMETER"
        private const val EXTRA_VALUE_START_SCANNING = "START_SCANNING"
        // @formatter:on
    }

    private val receiver: BroadcastReceiver = object : BroadcastReceiver() {
        override fun onReceive(context: Context, intent: Intent) {
            val scannedBarcode = intent.getStringExtra(DATA_STRING_TAG)

            if (scannedBarcode != null) {
                onBarcodeScanned(scannedBarcode)
            } else {
                onBarcodeScannerError(BarcodeScannerException("Unable to get scanned barcode"))
            }
        }
    }

    override fun startScanning(fragment: Fragment) {
        activity.sendBroadcast(
            Intent(ACTION_SOFTSCANTRIGGER).apply {
                putExtra(EXTRA_PARAM, EXTRA_VALUE_START_SCANNING)
            }
        )
    }

    override fun registerBarcodeScanner() {
        activity.registerReceiver(
            receiver,
            IntentFilter(zebraDataWedgeIntentAction)
        )
    }

    override fun unregisterBarcodeScanner() {
        activity.unregisterReceiver(receiver)
    }

    override fun onRegisterListener() {
        // no-op
    }
}
