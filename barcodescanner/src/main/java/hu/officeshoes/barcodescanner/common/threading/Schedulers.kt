package hu.officeshoes.barcodescanner.common.threading

import android.os.Build
import android.os.Looper
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.core.Scheduler
import io.reactivex.rxjava3.schedulers.Schedulers
import java.util.concurrent.Executors
import kotlin.math.max

object Schedulers {

    val mainScheduler: Scheduler by lazy {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP_MR1)
            AndroidSchedulers.from(Looper.getMainLooper(), false)
        else
            AndroidSchedulers.mainThread()
    }

    val backgroundScheduler: Scheduler by lazy {
        val maxThreads = max(1, Runtime.getRuntime().availableProcessors() - 1)

        val executorService = if (maxThreads == 1) {
            Executors.newSingleThreadExecutor()
        } else {
            Executors.newFixedThreadPool(maxThreads)
        }

        Schedulers.from(executorService)
    }
}
