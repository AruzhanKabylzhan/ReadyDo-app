package kz.rdd.core.ui.screen.alert

import android.content.Context
import android.os.Parcelable
import androidx.annotation.DrawableRes
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import dev.olshevski.navigation.reimagined.pop
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlinx.parcelize.Parcelize
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.DialogDestination
import kz.rdd.core.ui.ext.clickableWithIndication
import kz.rdd.core.ui.ext.returnToPrevDestination
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.utils.VmRes
import kz.rdd.core.ui.utils.toStringCompose

@Parcelize
data class CommonAlertDialogDestination(
    val title: VmRes.Parcelable<CharSequence>,
    @DrawableRes val icon: Int? = null,
    val positiveButtonText: VmRes.Parcelable<CharSequence> = VmRes.Str("OK"),
    val negativeButtonText: VmRes.Parcelable<CharSequence>? = VmRes.StrRes(R.string.common_cancel),
    val behavior: Behavior = Behavior.None,
    val additionalKey: String? = null,
) : DialogDestination {

    override fun onDismiss(
        destinationController: DestinationController,
        coroutineScope: CoroutineScope,
    ) {
        coroutineScope.launch {
            destinationController.returnToPrevDestination(
                key = RESULT_KEY, value = null,
                additionalKey = additionalKey
            )
        }
    }

    companion object {
        const val RESULT_KEY = "CommonAlertDialogDestination"
    }

    interface Behavior : Parcelable {
        fun onClickPositive(
            context: Context,
            controller: DestinationController,
        ) = Unit

        fun onClickNegative(
            context: Context,
            controller: DestinationController,
        ) = Unit

        @Parcelize
        object None : Behavior
    }

    @Composable
    override fun Content(controller: DestinationController) {
        val coroutineScope = rememberCoroutineScope()
        val context = LocalContext.current
        Column(
            modifier = Modifier
                .padding(horizontal = 68.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(16.dp))
                    .background(LocalAppTheme.colors.white)
                    .padding(top = 21.dp)
            ) {
                icon?.let {
                    Icon(
                        imageVector = ImageVector.vectorResource(icon),
                        contentDescription = null,
                        tint = Color.Unspecified,
                    )
                    Spacer(modifier = Modifier.height(16.dp))
                }
                Text(
                    text = title.toStringCompose(),
                    style = LocalAppTheme.typography.l12B,
                    color = LocalAppTheme.colors.primaryText,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .padding(horizontal = 16.dp)
                        .padding(bottom = 24.dp)
                )

                Spacer(
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(1.dp)
                        .background(LocalAppTheme.colors.stroke2)
                )

                Text(
                    text = positiveButtonText.toStringCompose(),
                    color = LocalAppTheme.colors.lowestScore,
                    style = LocalAppTheme.typography.l16B,
                    textAlign = TextAlign.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clickableWithIndication {
                            coroutineScope.launch {
                                behavior.onClickPositive(
                                    context = context,
                                    controller = controller,
                                )
                                controller.returnToPrevDestination(
                                    key = RESULT_KEY, value = true,
                                    additionalKey = additionalKey
                                )
                                controller.dialogNavController.pop()
                            }
                        }
                        .padding(
                            top = 16.dp,
                            bottom = 12.dp,
                            start = 8.dp,
                            end = 8.dp,
                        )
                )
            }

            negativeButtonText?.let {
                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally,
                    modifier = Modifier
                        .fillMaxWidth()
                        .clip(RoundedCornerShape(16.dp))
                        .background(LocalAppTheme.colors.white)
                ) {
                    Text(
                        text = negativeButtonText.toStringCompose(),
                        color = LocalAppTheme.colors.primaryText,
                        style = LocalAppTheme.typography.l16,
                        textAlign = TextAlign.Center,
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickableWithIndication {
                                coroutineScope.launch {
                                    behavior.onClickNegative(
                                        context = context,
                                        controller = controller,
                                    )
                                    controller.returnToPrevDestination(
                                        key = RESULT_KEY, value = false,
                                        additionalKey = additionalKey
                                    )
                                    controller.dialogNavController.pop()
                                }
                            }
                            .padding(
                                top = 16.dp,
                                bottom = 16.dp,
                                start = 8.dp,
                                end = 8.dp,
                            )
                    )
                }
            }
        }
    }
}