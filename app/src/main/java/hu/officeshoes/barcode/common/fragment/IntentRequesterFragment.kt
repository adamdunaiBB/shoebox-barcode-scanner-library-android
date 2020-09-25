package hu.officeshoes.barcode.common.fragment

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import io.reactivex.rxjava3.functions.BiConsumer

class IntentRequesterFragment : Fragment() {

    companion object {
        private const val REQUEST_CODE_KEY = "requestCode"

        fun newInstance(funcToCall: BiConsumer<Int, Intent?>, requestCode: Int) =
            IntentRequesterFragment().apply {
                callback = funcToCall
                intentRequestCode = requestCode
            }
    }

    private var intentRequestCode: Int = 0

    private var callback: BiConsumer<Int, Intent?>? = null
    private var callResult: CallResult? = null

    @SuppressLint("MissingSuperCall")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        savedInstanceState?.let {
            intentRequestCode = it.getInt(REQUEST_CODE_KEY)
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if (requestCode != intentRequestCode) {
            return
        }

        if (callback != null) {
            callback?.accept(resultCode, data)
            remove()
        } else {
            callResult = CallResult(
                code = resultCode,
                data = data
            )
        }
    }

    private fun remove() {
        activity
            ?.supportFragmentManager
            ?.beginTransaction()
            ?.remove(this)
            ?.commit()
    }

    fun requestIntentForResult(intent: Intent) {
        startActivityForResult(intent, intentRequestCode)
    }

    fun restoreCallback(newCallback: BiConsumer<Int, Intent?>) {
        callback = newCallback

        callResult?.let {
            newCallback.accept(it.code, it.data)
            remove()
        }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putInt(REQUEST_CODE_KEY, intentRequestCode)

        super.onSaveInstanceState(outState)
    }

    private data class CallResult(
        val code: Int,
        val data: Intent?
    )
}
