package hu.officeshoes.barcode.barcodescanner

interface BarcodeScannerListener {

    fun onBarcodeScanned(barcode: String)
}
