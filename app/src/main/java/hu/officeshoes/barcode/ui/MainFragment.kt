package hu.officeshoes.barcode.ui

import android.os.Bundle
import android.view.View
import androidx.annotation.LayoutRes
import hu.officeshoes.barcode.MainNavigator
import hu.officeshoes.barcode.MainNavigatorImpl
import hu.officeshoes.barcode.R
import hu.officeshoes.barcode.common.ui.BaseFragment
import kotlinx.android.synthetic.main.fragment_main.*

class MainFragment : BaseFragment() {

    private val navigator: MainNavigator by lazy {
        MainNavigatorImpl(getMainActivity())
    }

    @LayoutRes
    override fun getViewResource(): Int = R.layout.fragment_main

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        navigateToScanButton.setOnClickListener {
            navigator.navigateToScan()
        }
    }
}
