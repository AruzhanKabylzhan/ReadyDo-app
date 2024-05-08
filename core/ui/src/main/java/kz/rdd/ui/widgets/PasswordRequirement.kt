package kz.rdd.core.ui.widgets

import androidx.compose.animation.animateColorAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.unit.dp
import kz.rdd.core.ui.R
import kz.rdd.core.ui.theme.LocalAppTheme

@Stable
interface PasswordRequirementsColumnState {
    val passwordsMatch: Boolean
    val matchesMinimumCharacter: Boolean
    val matchesMinimumNumber: Boolean
    val matchesMinimumCapitalLetter: Boolean
}

@Composable
fun PasswordRequirementsColumn(
    state: PasswordRequirementsColumnState,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {

        Spacer(modifier = Modifier.height(16.dp))

        PasswordRequirement(
            stringResource(id = R.string.registration_password_match_accept),
            isRequirementMatches = state.passwordsMatch
        )

        Spacer(modifier = Modifier.height(12.dp))


        PasswordRequirement(
            stringResource(id = R.string.registration_minimum_characters),
            isRequirementMatches = state.matchesMinimumCharacter
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordRequirement(
            stringResource(id = R.string.registration_minimum_numbers),
            isRequirementMatches = state.matchesMinimumNumber
        )

        Spacer(modifier = Modifier.height(12.dp))

        PasswordRequirement(
            stringResource(id = R.string.registration_minimum_capital_letter),
            isRequirementMatches = state.matchesMinimumCapitalLetter
        )

        Spacer(modifier = Modifier.height(12.dp))
    }

}

@Composable
fun PasswordRequirement(
    requirementText: String,
    isRequirementMatches: Boolean
) {
    Row(
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically
    ) {

        val textColor by animateColorAsState(
            LocalAppTheme.colors.run {
                if (isRequirementMatches) bgButton else accentText
            },
            label = "",
        )

        val (icon, iconColor) = if (isRequirementMatches) {
            R.drawable.ic_check_16 to LocalAppTheme.colors.checkGreen
        } else {
            R.drawable.ic_close_16 to Color.Unspecified
        }

        Icon(
            imageVector = ImageVector.vectorResource(id = icon),
            contentDescription = "",
            tint = iconColor,
        )
        Spacer(modifier = Modifier.width(4.dp))
        Text(
            text = requirementText,
            style = LocalAppTheme.typography.l14,
            color = textColor
        )

    }
}
