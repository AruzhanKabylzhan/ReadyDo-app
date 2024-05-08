package kz.rdd.profile.presentation

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kz.rdd.core.ui.base.navigation.NavigateTo
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.ext.getFullName
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.profile.domain.ProfileUseCase
import kz.rdd.profile.presentation.about.AboutDestination
import kz.rdd.profile.presentation.about.AboutScreen
import kz.rdd.profile.presentation.edit.EditProfileDestination
import kz.rdd.profile.presentation.fav.FavDestination
import kz.rdd.profile.presentation.order.OrderDestination
import kz.rdd.profile.presentation.support.SupportDestination
import kz.rdd.store.UserLogoutCleanUseCase
import kz.rdd.store.UserStore

data class ProfileState(
    val avatarUrl: String = "",
    val fullName: String = "",
    val phoneNumber: String = "",
    val email: String = "",
    val address: String = "",
    val myself: String = "",
) : UiState
class ProfileViewModel(
    private val userStore: UserStore,
    private val profileUseCase: ProfileUseCase,
    private val userLogoutCleanUseCase: UserLogoutCleanUseCase,
) : BaseViewModel<ProfileState>() {
    override fun createInitialState() = ProfileState(
        avatarUrl = userStore.userDetails?.avatar.orEmpty(),
        fullName = userStore.userDetails?.fullName.orEmpty(),
        phoneNumber = userStore.userDetails?.phoneNumber.orEmpty(),
        email = userStore.userDetails?.email.orEmpty(),
    )

    init {
        load()
    }

    fun load() {
        launch {
            profileUseCase.getProfile()
                .doOnSuccess {
                    setState {
                        copy(
                            avatarUrl = it.avatar,
                            fullName = getFullName(it.firstName, it.lastName, it.middleName),
                            phoneNumber = it.phoneNumber,
                            address = it.address,
                            myself = it.aboutYourself
                        )
                    }
                }.doOnError {
                    handleError(it)
                }
        }
    }

    fun onClickEditProfile() {
        navigate(NavigateTo(EditProfileDestination()))
    }

    fun onClickFavList() {
        navigate(NavigateTo(FavDestination()))
    }

    fun onClickAbout() {
        navigate(NavigateTo(AboutDestination()))
    }

    fun onClickSupport() {
        navigate(NavigateTo(SupportDestination()))
    }

    fun onClickOrder() {
        navigate(NavigateTo(OrderDestination()))
    }

    fun onClickLogOut() {
        CoroutineScope(Dispatchers.IO).launch {
            userLogoutCleanUseCase.cleanAll()
        }
    }
}