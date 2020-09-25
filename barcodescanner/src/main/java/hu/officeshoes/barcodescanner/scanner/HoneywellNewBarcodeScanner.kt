package hu.officeshoes.barcodescanner.scanner

import android.app.Activity
import androidx.fragment.app.Fragment
import com.honeywell.aidc.AidcManager
import com.honeywell.aidc.BarcodeFailureEvent
import com.honeywell.aidc.BarcodeReadEvent
import com.honeywell.aidc.BarcodeReader
import com.honeywell.aidc.InvalidScannerNameException
import com.honeywell.aidc.ScannerUnavailableException
import com.honeywell.aidc.UnsupportedPropertyException

class HoneywellNewBarcodeScanner(
    override val activity: Activity,
    override val onBarcodeScanned: (String) -> Unit,
    override val onBarcodeScannerError: (BarcodeScannerException) -> Unit
) : BarcodeReader.BarcodeListener,
    BaseBarcodeScanner {

    companion object {
        private const val DEFAULT_PROFILE_NAME = "DEFAULT"
    }

    private var barcodeReader: BarcodeReader? = null

    init {
        AidcManager.create(activity) { manager ->
            try {
                barcodeReader = manager.createBarcodeReader()
                registerBarcodeScanner()
            } catch (e: InvalidScannerNameException) {
                onBarcodeScannerError(BarcodeScannerException("Invalid scanner name: $e"))
            } catch (e: Exception) {
                onBarcodeScannerError(BarcodeScannerException("Unknown error: $e"))
            }
        }
    }

    override fun startScanning(fragment: Fragment) {
        barcodeReader?.softwareTrigger(true)
    }

    override fun registerBarcodeScanner() {
        barcodeReader?.let {
            with(it) {
                try {
                    claim()
                    addBarcodeListener(this@HoneywellNewBarcodeScanner)
                    if (loadProfile(DEFAULT_PROFILE_NAME)) {
                        setProperty("DPR_WEDGE", false)
                    } else {
                        onBarcodeScannerError(BarcodeScannerException("Failed to load Honeywell settings profile:\n$DEFAULT_PROFILE_NAME"))
                    }
                } catch (e: UnsupportedPropertyException) {
                    onBarcodeScannerError(BarcodeScannerException("Failed to apply properties"))
                } catch (e: ScannerUnavailableException) {
                    onBarcodeScannerError(BarcodeScannerException("Scanner unavailable"))
                } catch (e: Exception) {
                    onBarcodeScannerError(BarcodeScannerException("Unknown error: $e"))
                }
            }
        }
    }

    override fun unregisterBarcodeScanner() {
        barcodeReader?.let {
            with(it) {
                release()
                removeBarcodeListener(this@HoneywellNewBarcodeScanner)
            }
        }
    }

    override fun onRegisterListener() {
        barcodeReader?.softwareTrigger(false)
    }

    override fun onBarcodeEvent(barcodeEvent: BarcodeReadEvent?) {
        barcodeReader?.softwareTrigger(false)
        barcodeEvent?.let {
            activity.runOnUiThread {
                onBarcodeScanned(it.barcodeData)
            }
        }
    }

    override fun onFailureEvent(barcodeFailureEvent: BarcodeFailureEvent?) {
        barcodeReader?.softwareTrigger(false)
    }
}
