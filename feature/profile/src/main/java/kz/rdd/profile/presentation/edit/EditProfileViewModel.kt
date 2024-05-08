package kz.rdd.profile.presentation.edit

import android.net.Uri
import kz.rdd.core.local_storage.domain.LocalStorage
import kz.rdd.core.ui.base.effect.SuccessEffect
import kz.rdd.core.ui.base.navigation.PopUpTo
import kz.rdd.core.ui.base.navigation.ShowSheet
import kz.rdd.core.ui.base.viewmodel.BaseViewModel
import kz.rdd.core.ui.base.viewmodel.UiState
import kz.rdd.core.ui.base.viewmodel.handleError
import kz.rdd.core.utils.ext.getFullName
import kz.rdd.core.utils.outcome.doOnError
import kz.rdd.core.utils.outcome.doOnSuccess
import kz.rdd.profile.data.model.RequestProfile
import kz.rdd.profile.domain.ProfileUseCase
import kz.rdd.store.UserStore

data class EditProfileState(
    val email: String = "",
    val selectedAvatar: Uri? = null,
    val firstName: String = "",
    val lastName: String = "",
    val aboutMySelf: String = "",
    val address: String = "",
    val currentAvatar: String = "",
    val phoneNumber: String = "",
    val username: String = "",
) : UiState
class EditProfileViewModel(
    private val localStorage: LocalStorage,
    private val profileUseCase: ProfileUseCase,
    private val userStore: UserStore,
) : BaseViewModel<EditProfileState>() {
    override fun createInitialState() = EditProfileState()

    init {
        load()
    }

    private fun load() {
        launch {
            profileUseCase.getProfile()
                .doOnSuccess {
                    setState {
                        copy(
                            currentAvatar = it.avatar,
                            phoneNumber = it.phoneNumber,
                            address = it.address,
                            email = it.email,
                            firstName = it.firstName,
                            lastName = it.lastName,
                            aboutMySelf = it.aboutYourself,
                        )
                    }
                }.doOnError {
                    handleError(it)
                }
        }
    }

    fun onUpdateEmail(item: String) {
        setState { copy(email = item) }
    }

    fun onUpdateFirstName(item: String) {
        setState { copy(firstName = item) }
    }

    fun onUpdateLastName(item: String) {
        setState { copy(lastName = item) }
    }

    fun onUpdateMySelf(item: String) {
        setState { copy(aboutMySelf = item) }
    }

    fun setAvatar(uri: Uri) {
        setState {
            copy(
                selectedAvatar = uri
            )
        }
    }

    fun onClickProceed() {
        launch {
            val avatarFilePath = currentState.selectedAvatar?.let {
                localStorage.saveExternalFile(it).first
            }
            if(avatarFilePath != null){
                profileUseCase.changeProfile(
                    RequestProfile(
                        firstName = currentState.firstName,
                        lastName = currentState.lastName,
                        avatar = avatarFilePath,
                        email = currentState.email,
                        aboutMySelf = currentState.aboutMySelf,
                        address = currentState.address,
                        phoneNumber = currentState.phoneNumber,
                    )
                ).doOnError {
                    handleError(it)
                }.doOnSuccess {
                    setEffect { SuccessEffect() }
                    navigate(PopUpTo { it.isMain })
                }
            } else {
                profileUseCase.changeProfile(
                    RequestProfile(
                        firstName = currentState.firstName,
                        lastName = currentState.lastName,
                        email = currentState.email,
                        aboutMySelf = currentState.aboutMySelf,
                        address = currentState.address,
                        phoneNumber = currentState.phoneNumber,
                        avatar = null
                    )
                ).doOnError {
                    handleError(it)
                }.doOnSuccess {
                    setEffect { SuccessEffect() }
                    navigate(PopUpTo { it.isMain })
                }
            }

        }
    }

    fun onUpdateAddress(s: String) {
        setState { copy(address = s) }
    }

    fun onUpdatePhoneNumber(s: String) {
        setState { copy(phoneNumber = s) }
    }

    fun onClickChangePassword() {
        navigate(ShowSheet(ChangePasswordSheetDestination()))
    }
}