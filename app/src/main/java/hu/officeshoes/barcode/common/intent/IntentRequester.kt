package hu.officeshoes.barcode.common.intent

import android.content.Context
import android.content.Intent
import androidx.fragment.app.FragmentActivity
import hu.officeshoes.barcode.common.fragment.FragmentManagerUtil
import hu.officeshoes.barcode.common.fragment.IntentRequesterFragment
import io.reactivex.rxjava3.core.Single
import io.reactivex.rxjava3.functions.BiConsumer

class IntentRequester(private val context: Context) {

    fun startIntentForResult(intent: Intent, requestCode: Int): Single<Result> =
        Single.create { objectEmitter ->

            val biConsumer = BiConsumer<Int, Intent?> { resCode, dataIntent ->
                objectEmitter.onSuccess(
                    Result(resCode, dataIntent)
                )
            }

            val existingIntentRequesterFragment = FragmentManagerUtil
                .findFragment<IntentRequesterFragment>(context, getTagName(requestCode))

            if (existingIntentRequesterFragment != null) {
                existingIntentRequesterFragment.restoreCallback(biConsumer)
            } else {
                val newIntentRequesterFragment =
                    IntentRequesterFragment.newInstance(biConsumer, requestCode)

                val fragmentManager = (context as FragmentActivity).supportFragmentManager
                fragmentManager.beginTransaction()
                    .add(newIntentRequesterFragment, getTagName(requestCode))
                    .commitNow()

                newIntentRequesterFragment.requestIntentForResult(intent)
            }
        }

    private fun getTagName(requestCode: Int) =
        FragmentManagerUtil.getTagName(IntentRequesterFragment::class.java) + "_" + requestCode

    data class Result(
        val resultCode: Int,
        val data: Intent?
    )
}
