package hu.officeshoes.barcodescanner.scanner

class BarcodeScannerException(
    override val message: String
) : Exception(message)
