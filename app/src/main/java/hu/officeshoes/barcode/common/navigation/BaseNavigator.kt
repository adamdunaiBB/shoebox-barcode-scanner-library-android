package hu.officeshoes.barcode.common.navigation

import androidx.annotation.IdRes
import androidx.navigation.findNavController
import hu.officeshoes.barcode.MainActivity
import kotlinx.android.synthetic.main.activity_main.*

abstract class BaseNavigator(mainActivity: MainActivity) {

    private val navController by lazy {
        mainActivity.fragmentNavHost.findNavController()
    }

    protected val navigationManager by lazy {
        mainActivity.supportFragmentManager
    }

    protected fun navigate(@IdRes resId: Int) {
        navController.navigate(resId)
    }
}
