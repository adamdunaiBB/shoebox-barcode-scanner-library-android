package hu.officeshoes.barcode

import hu.officeshoes.barcode.common.navigation.BaseNavigator

class MainNavigatorImpl(mainActivity: MainActivity) :
    BaseNavigator(mainActivity), MainNavigator {

    override fun navigateToScan() {
        navigate(R.id.action_mainFragment_to_scanFragment)
    }
}
