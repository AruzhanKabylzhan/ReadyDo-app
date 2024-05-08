package kz.rdd.main

import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.NoState


class MainNavigationViewModel(
) : BaseViewModel<NoState>() {

    override fun createInitialState() = NoState

}
