package kz.rdd.login.presentation.otp.widgets

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CommonDivider
import kz.rdd.login.domain.model.VerifySmsTypes

@Composable
fun ChangePhoneNumberDialog(
    action: String = "OK",
    status: VerifySmsTypes,
    horizontalPadding: Dp = 24.dp,
    onActionPositive: () -> Unit = { },
    controller: DestinationController
) {
    Card(
        modifier = Modifier.padding(horizontal = horizontalPadding),
        backgroundColor = LocalAppTheme.colors.white,
        shape = RoundedCornerShape(16.dp),
        elevation = 0.dp,
    ) {
        val description = when (status) {
            VerifySmsTypes.OK -> stringResource(id = R.string.settings_edit_phone_number_updated)
            else -> stringResource(id = R.string.change_password_incorrect_old_password)
        }

        val icon = when (status) {
            VerifySmsTypes.OK -> ImageVector.vectorResource(id = R.drawable.ic_change_password_check_32)
            else -> ImageVector.vectorResource(id = R.drawable.ic_circle_error_32)
        }

        val color = when (status) {
            VerifySmsTypes.OK -> LocalAppTheme.colors.checkGreen
            else -> LocalAppTheme.colors.error
        }

        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            Icon(
                imageVector = icon,
                contentDescription = "",
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 16.dp),
                tint = color
            )
            Text(
                modifier = Modifier
                    .padding(horizontal = 16.dp)
                    .padding(top = 12.dp),
                text = description,
                textAlign = TextAlign.Center,
                style = LocalAppTheme.typography.l14,
                color = LocalAppTheme.colors.primaryText
            )
            Spacer(modifier = Modifier.height(24.dp))
            CommonDivider()
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                Text(
                    text = action,
                    modifier = Modifier
                        .weight(1f)
                        .clickableWithIndication {
                            onActionPositive()
                        }
                        .padding(vertical = 16.dp, horizontal = 4.dp),
                    textAlign = TextAlign.Center,
                    style = LocalAppTheme.typography.l15,
                    color = color
                )
            }
        }
    }
}