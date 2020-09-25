package hu.officeshoes.barcodescanner.common.fragment

import android.content.Context
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

internal object FragmentManagerUtil {

    fun <T : Fragment> findFragment(context: Context, fragmentClass: Class<T>): T? =
        findFragment<T>(context, getTagName(fragmentClass))

    fun <T : Fragment> findFragment(context: Context, tag: String): T? {
        val fragmentActivity = context as FragmentActivity
        val fragmentManager = fragmentActivity.supportFragmentManager
        return findFragment(fragmentManager, tag)
    }

    @Suppress("UNCHECKED_CAST")
    fun <T : Fragment> findFragment(fragmentManager: FragmentManager, tag: String): T? =
        fragmentManager.findFragmentByTag(tag) as? T

    fun getTagName(fragmentClass: Class<*>): String = fragmentClass.name
}
