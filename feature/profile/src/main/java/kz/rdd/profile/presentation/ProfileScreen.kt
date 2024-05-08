package kz.rdd.profile.presentation

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonAsyncImage
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.navigate.profile.ProfileContentDelegate
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.ext.scaledClickable
import org.koin.androidx.compose.getViewModel


object ProfileContentDelegateImpl : ProfileContentDelegate {
    @Composable
    override fun Content() {
        ProfileScreen()
    }
}

@Composable
fun ProfileScreen() {
    val viewModel = getViewModel<ProfileViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)

    LaunchedEffect(Unit) {
        viewModel.load()
    }


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1),
    ) {
        CenteredToolbar(
            title = "Profile",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = false,
        )

        Spacer(modifier = Modifier.height(16.dp))

        UserInfo(
            avatarUrl = state.avatarUrl,
            fullName = state.fullName,
            onClickEditProfile = viewModel::onClickEditProfile
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth()
                .verticalScroll(rememberScrollState()),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Row(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_phone_number),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = state.phoneNumber,
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_email),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = state.email,
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText
                )
            }

            Spacer(modifier = Modifier.height(10.dp))

            Row(
                modifier = Modifier
                    .padding(horizontal = 25.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.Start,
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    imageVector = ImageVector.vectorResource(R.drawable.ic_address),
                    contentDescription = "",
                )
                Spacer(modifier = Modifier.width(10.dp))
                Text(
                    text = state.address,
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Card(
                modifier = Modifier
                    .heightIn(80.dp, 80.dp)
                    .background(LocalAppTheme.colors.white, RoundedCornerShape(12.dp)),
                shape = RoundedCornerShape(12.dp),
                elevation = 4.dp,
                contentColor = LocalAppTheme.colors.main
            ) {
                Text(
                    text = state.myself,
                    style = LocalAppTheme.typography.l15,
                    color = LocalAppTheme.colors.primaryText,
                    modifier = Modifier
                        .padding(horizontal = 16.dp, vertical = 10.dp)
                        .fillMaxWidth()
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            Column {
                SettingsItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_like),
                    text = "Favorites",
                    onClick = { viewModel.onClickFavList() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                SettingsItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_order_history),
                    text = "Order History",
                    onClick = { viewModel.onClickOrder() }
                )

//                Spacer(modifier = Modifier.height(10.dp))
//
//                SettingsItem(
//                    icon = ImageVector.vectorResource(R.drawable.ic_chef),
//                    text = "Become a chef",
//                    onClick = { }
//                )

                Spacer(modifier = Modifier.height(10.dp))

                SettingsItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_support),
                    text = "Support",
                    onClick = { viewModel.onClickSupport() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                SettingsItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_about_app),
                    text = "About",
                    onClick = { viewModel.onClickAbout() }
                )

                Spacer(modifier = Modifier.height(10.dp))

                SettingsItem(
                    icon = ImageVector.vectorResource(R.drawable.ic_log_out),
                    text = "Log out",
                    onClick = { viewModel.onClickLogOut() }
                )

                Spacer(modifier = Modifier.height(10.dp))
            }
        }
    }
}

@Composable
fun SettingsItem(
    icon: ImageVector,
    text: String,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .height(56.dp)
            .clickableWithIndication(
                rippleColor = LocalAppTheme.colors.primaryText,
                onClick = onClick,
            )
            .background(LocalAppTheme.colors.main, RoundedCornerShape(10.dp)),
        horizontalArrangement = Arrangement.spacedBy(6.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        Icon(
            imageVector = icon,
            contentDescription = "",
            tint = LocalAppTheme.colors.white,
            modifier = Modifier.padding(start = 16.dp)
        )

        Text(
            text = text,
            style = LocalAppTheme.typography.l17,
            color = LocalAppTheme.colors.white,
            modifier = Modifier.weight(1f),
            textAlign = TextAlign.Start,
        )
    }
}


@Composable
fun UserInfo(
    avatarUrl: Any?,
    fullName: String,
    onClickEditProfile: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 20.dp, horizontal = 16.dp),
        horizontalArrangement = Arrangement.spacedBy(14.dp)
    ) {
        CommonAsyncImage(
            modifier = Modifier
                .border(1.dp, LocalAppTheme.colors.stroke, CircleShape)
                .clip(CircleShape)
                .size(110.dp),
            url = avatarUrl,
            placeholder = painterResource(id = R.drawable.ic_user_photo_solo),
            error = painterResource(id = R.drawable.ic_user_photo_solo),
        )

        Column(
            modifier = Modifier,
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = fullName,
                style = LocalAppTheme.typography.l17,
                color = LocalAppTheme.colors.primaryText
            )
            Spacer(modifier = Modifier.height(6.dp))
            CommonButton(
                text = "Edit Profile",
                border = BorderStroke(1.dp, LocalAppTheme.colors.bgButton),
                textColor = LocalAppTheme.colors.primaryText,
                buttonColors = ButtonDefaults.buttonColors(
                    backgroundColor = LocalAppTheme.colors.white
                ),
                minHeight = 36.dp,
                maxHeight = 36.dp,
                onClick = onClickEditProfile
            )
        }
    }
}