package hu.officeshoes.barcode.ui

import androidx.annotation.LayoutRes
import hu.officeshoes.barcode.R
import hu.officeshoes.barcode.common.ui.BaseFragment

class ScanFragment : BaseFragment() {

    @LayoutRes
    override fun getViewResource(): Int = R.layout.fragment_scan
}
