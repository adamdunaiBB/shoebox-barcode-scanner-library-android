package hu.officeshoes.barcode.barcodescanner

import android.app.Activity
import android.content.Intent
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import autodispose2.androidx.lifecycle.AndroidLifecycleScopeProvider
import autodispose2.autoDispose
import hu.officeshoes.barcode.common.intent.IntentRequester
import hu.officeshoes.barcode.common.threading.Schedulers

class HoneywellOldBarcodeScanner(
    override val activity: Activity,
    override val onBarcodeScanned: (String) -> Unit,
    override val onBarcodeScannerError: (BarcodeScannerException) -> Unit
) : BaseBarcodeScanner {

    companion object {
        // Constants from Honeywell Dolphin 70e Black Android 4.0.3 Barcode scanner documentation
        private const val SCAN_ACTION = "com.google.zxing.client.android.SCAN"
        private const val EXTRA_SCAN_MODE = "scan_mode"
        private const val EXTRA_RESULT_BARCODE_DATA = "barcode_data"
        private const val EXTRA_RESULT_BARCODE_FORMAT = "barcode_format"
        private const val SCAN_MODE_SHOW_NO_RESULT = 0
        private const val SCAN_MODE_SHOW_RESULT_UI = 1
        private const val SCAN_MODE_SHARE_BY_SMS = 2
        private const val SCAN_MODE_SHARE_BY_MMS = 3
        private const val SCAN_MODE_SHARE_BY_EMAIL = 4
        private const val SCAN_MODE_RESULT_AS_URI = 5
        private const val SCAN_RESULT_FAILED = -1000
        private const val SCAN_RESULT_SUCCEEDED = -9999

        private const val REQUEST_CODE_SCAN = 44
    }

    override fun startScanning(fragment: Fragment) {
        val scopeProvider =
            AndroidLifecycleScopeProvider.from(
                fragment,
                Lifecycle.Event.ON_DESTROY
            )

        IntentRequester(activity)
            .startIntentForResult(
                intent = createScanIntent(),
                requestCode = REQUEST_CODE_SCAN
            )
            .observeOn(Schedulers.backgroundScheduler)
            .filter { result -> result.resultCode == SCAN_RESULT_SUCCEEDED }
            .observeOn(Schedulers.mainScheduler)
            .autoDispose(scopeProvider)
            .subscribe({
                val scannedBarcode =
                    it.data?.getStringExtra(EXTRA_RESULT_BARCODE_DATA)

                if (scannedBarcode != null) {
                    onBarcodeScanned(scannedBarcode)
                } else {
                    onBarcodeScannerError(BarcodeScannerException("Unable to get scanned barcode"))
                }
            }, {
                onBarcodeScannerError(BarcodeScannerException("Unknown barcode reading error: " + it?.message))
            })
    }

    private fun createScanIntent() =
        Intent(SCAN_ACTION)
            .apply {
                putExtra(EXTRA_SCAN_MODE, SCAN_MODE_RESULT_AS_URI)
            }

    override fun registerBarcodeScanner() {
        // no-op
    }

    override fun unregisterBarcodeScanner() {
        // no-op
    }

    override fun onRegisterListener() {
        // no-op
    }
}
