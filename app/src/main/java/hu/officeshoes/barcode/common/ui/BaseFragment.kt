package hu.officeshoes.barcode.common.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.CallSuper
import androidx.annotation.LayoutRes
import androidx.fragment.app.Fragment
import hu.officeshoes.barcode.MainActivity

abstract class BaseFragment : Fragment() {

    @CallSuper
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? =
        inflater.inflate(getViewResource(), container, false)

    protected fun getMainActivity() = activity as MainActivity

    @LayoutRes
    abstract fun getViewResource(): Int
}
