package hu.officeshoes.barcode.ui

import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.annotation.LayoutRes
import hu.officeshoes.barcode.MainNavigator
import hu.officeshoes.barcode.MainNavigatorImpl
import hu.officeshoes.barcode.R
import hu.officeshoes.barcode.common.ui.BaseFragment
import hu.officeshoes.barcodescanner.BarcodeScanner
import hu.officeshoes.barcodescanner.scanner.BarcodeScannerListener
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment(), BarcodeScannerListener {

    private val navigator: MainNavigator by lazy {
        MainNavigatorImpl(getMainActivity())
    }

    @LayoutRes
    override fun getViewResource(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        scanButton.setOnClickListener {
            BarcodeScanner.startScanning(this)
        }

        navigateToScanButton.setOnClickListener {
            navigator.navigateToScan()
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
        Toast.makeText(
            getMainActivity(),
            "MainFragment: Barcode scanned: $barcode",
            Toast.LENGTH_SHORT
        ).show()
    }
}
