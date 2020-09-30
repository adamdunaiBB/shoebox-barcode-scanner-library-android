package hu.officeshoes.barcodescanner.listener

interface BarcodeScannerListener {

    fun onBarcodeScanned(barcode: String)
}
