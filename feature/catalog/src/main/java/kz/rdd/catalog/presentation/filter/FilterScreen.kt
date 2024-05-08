@file:OptIn(ExperimentalLayoutApi::class)

package kz.rdd.catalog.presentation.filter

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import kotlinx.parcelize.Parcelize
import kz.rdd.catalog.presentation.CatalogViewModel
import kz.rdd.core.ui.DestinationController
import kz.rdd.core.ui.R
import kz.rdd.core.ui.base.ComposeEffectHandler
import kz.rdd.core.ui.base.ComposeEventHandler
import kz.rdd.core.ui.base.Destination
import kz.rdd.core.ui.ext.collectAsStateWithLifecycle
import kz.rdd.core.ui.ext.safeNavigationPadding
import kz.rdd.core.ui.ext.scaledClickable
import kz.rdd.core.ui.theme.LocalAppTheme
import kz.rdd.core.ui.widgets.CenteredToolbar
import kz.rdd.core.ui.widgets.CommonButton
import kz.rdd.core.ui.widgets.CommonTextField
import org.koin.androidx.compose.getViewModel

@Parcelize
class FilterDestination : Destination {
    @Composable
    override fun Content(controller: DestinationController) {
        FilterScreen()
    }
}

@Composable
fun FilterScreen() {
    val viewModel = getViewModel<FilterViewModel>()
    val state by viewModel.uiState.collectAsStateWithLifecycle()

    ComposeEffectHandler(effect = viewModel.effect)
    ComposeEventHandler(event = viewModel.event)


    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(LocalAppTheme.colors.bg1)
            .safeNavigationPadding(),
    ) {

        CenteredToolbar(
            title = "Filter",
            titleStyle = LocalAppTheme.typography.l17,
            isNavigationIconVisible = true,
            onNavigationIconClick = viewModel::navigateBack
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "Cuisine",
            style = LocalAppTheme.typography.l22,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )

        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .fillMaxWidth()
        ) {
            state.cuisineList.forEach {
                FilterItem(it, state.cuisine, viewModel::onClickCuisine)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Taste",
            style = LocalAppTheme.typography.l22,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )

        FlowRow(
            verticalArrangement = Arrangement.Center,
            horizontalArrangement = Arrangement.spacedBy(0.dp),
            modifier = Modifier
                .padding(horizontal = 16.dp, vertical = 6.dp)
                .fillMaxWidth()
        ) {
            state.tasteList.forEach {
                FilterItem(it, state.taste, viewModel::onClickTaste)
            }
        }

        Spacer(modifier = Modifier.height(10.dp))

        Text(
            text = "Price",
            style = LocalAppTheme.typography.l22,
            color = LocalAppTheme.colors.primaryText,
            modifier = Modifier
                .padding(horizontal = 20.dp)
        )

        Column(
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .fillMaxWidth(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            CommonTextField(
                value = state.priceFrom,
                onUpdate = viewModel::onUpdatePriceFrom,
                placeholderText = "Price From",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )

            Spacer(modifier = Modifier.height(8.dp))

            CommonTextField(
                value = state.priceTo,
                onUpdate = viewModel::onUpdatePriceTo,
                placeholderText = "Price To",
                keyboardOptions = KeyboardOptions(
                    keyboardType = KeyboardType.Number
                )
            )
        }

        Spacer(modifier = Modifier.weight(1f))

        CommonButton(
            text = "Clear filter",
            modifier = Modifier.fillMaxWidth(),
            buttonColors = ButtonDefaults.buttonColors(
                backgroundColor = LocalAppTheme.colors.main,
                disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
            ),
            onClick = viewModel::onClickClear,
        )

        Spacer(modifier = Modifier.height(16.dp))

        CommonButton(
            text = "Apply",
            modifier = Modifier.fillMaxWidth(),
            buttonColors = ButtonDefaults.buttonColors(
                backgroundColor = LocalAppTheme.colors.main,
                disabledBackgroundColor = LocalAppTheme.colors.bgButtonSecondary,
            ),
            onClick = viewModel::onClickProceed,
        )
    }
}

@Composable
fun FilterItem(
    title: String,
    selectedFilter: String,
    onClick: (String) -> Unit,
) {
    val background =
        if (title.equals(selectedFilter)) LocalAppTheme.colors.main else LocalAppTheme.colors.white
    val textColor =
        if (title.equals(selectedFilter)) LocalAppTheme.colors.white else LocalAppTheme.colors.primaryText
    Column(
        modifier = Modifier
            .padding(2.dp)
            .background(background, RoundedCornerShape(16.dp))
            .border(1.dp, LocalAppTheme.colors.main, RoundedCornerShape(16.dp))
            .scaledClickable {
                onClick(title)
            },
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = title,
            style = LocalAppTheme.typography.l15,
            color = textColor,
            modifier = Modifier.padding(7.dp)
        )
    }
}