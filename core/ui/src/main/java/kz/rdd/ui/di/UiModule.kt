package kz.rdd.core.ui.di

import kz.rdd.core.ui.screen.item_picker.SheetPickerViewModel
import kz.rdd.core.ui.screen.loading_indicator.LoadingIndicatorViewModel
import kz.rdd.core.ui.widgets.ServerConfigurationSwitcherViewModel
import kz.rdd.core.ui.widgets.dialog.DialogWithTextFieldViewModel
import org.koin.androidx.viewmodel.dsl.viewModelOf
import org.koin.dsl.module

val uiModule = module {
    viewModelOf(::DialogWithTextFieldViewModel)
    viewModelOf(::ServerConfigurationSwitcherViewModel)
    viewModelOf(::LoadingIndicatorViewModel)
    viewModelOf(::SheetPickerViewModel)
}
