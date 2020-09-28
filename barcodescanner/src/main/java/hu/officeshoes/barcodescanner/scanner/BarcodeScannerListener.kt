package hu.officeshoes.barcodescanner.scanner

interface BarcodeScannerListener {

    fun onBarcodeScanned(barcode: String)
}
