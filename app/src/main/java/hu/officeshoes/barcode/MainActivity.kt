package hu.officeshoes.barcode

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import hu.officeshoes.barcodescanner.BarcodeScanner

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        BarcodeScanner.init(
            activity = this,
            zebraDataWedgeIntentAction = "hu.officeshoes.barcode.RECVR"
        ) {
            Log.e("MA", "Barcode scanner error: " + it.message)

            showErrorDialog(it.message)
        }
    }

    private fun showErrorDialog(message: String) {
        AlertDialog.Builder(this)
            .setTitle("Error happened")
            .setIcon(android.R.drawable.ic_dialog_alert)
            .setMessage(message)
            .setPositiveButton("OK") { _, _ ->
                // no-op
            }
            .show()
    }
}
