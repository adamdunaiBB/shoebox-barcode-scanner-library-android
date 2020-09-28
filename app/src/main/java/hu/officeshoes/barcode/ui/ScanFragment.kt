package hu.officeshoes.barcode.ui

import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import hu.officeshoes.barcode.R
import hu.officeshoes.barcode.common.ui.BaseFragment
import hu.officeshoes.barcodescanner.BarcodeScanner
import hu.officeshoes.barcodescanner.listener.BarcodeScannerListener
import kotlinx.android.synthetic.main.fragment_scan.*

class ScanFragment : BaseFragment(), BarcodeScannerListener {

    @LayoutRes
    override fun getViewResource(): Int = R.layout.fragment_scan

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scanButton.setOnClickListener {
            BarcodeScanner.startScanning(this)
        }
    }

    override fun onResume() {
        super.onResume()

        BarcodeScanner.registerListener(this, this)
    }

    override fun onPause() {
        super.onPause()

        BarcodeScanner.unregisterListener(this)
    }

    override fun onBarcodeScanned(barcode: String) {
        Log.i("SF", "@@@ ScanFragment: Barcode scanned: $barcode")

        Toast.makeText(
            getMainActivity(),
            "ScanFragment: Barcode scanned: $barcode",
            Toast.LENGTH_SHORT
        ).show()
    }
}
