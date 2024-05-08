package kz.rdd.core.ui.widgets.card_stack

import android.annotation.SuppressLint
import androidx.compose.foundation.gestures.Orientation
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.FractionalThreshold
import androidx.compose.material.ThresholdConfig
import androidx.compose.runtime.Composable
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

/**
 * A stack of cards that can be dragged.
 *
 * @param itemsList Cards to show in the stack.
 * @param heightCard Card's height for setting.
 * @param betweenMargin it is for set margin Card's between.
 * @param onSwipeLeft Lambda that executes when the animation of swiping left is finished.
 * @param onSwipeRight Lambda that executes when the animation of swiping right is finished.
 * @param onSwipeTop Lambda that executes when the animation of swiping top is finished.
 * @param onSwipeBottom Lambda that executes when the animation of swiping bottom is finished.
 * @param orientation it for set scroll orientation.
 * @param shadowSide it for set side background items.
 *
 * @param thresholdConfig Specifies where the threshold between the predefined Anchors is. This is represented as a lambda
 * that takes two float and returns the threshold between them in the form of a [ThresholdConfig].
 *
 */
@SuppressLint("MutableCollectionMutableState")
@ExperimentalMaterialApi
@Composable
fun <T> SwipeCard(
    modifier: Modifier = Modifier,
    itemsList: SnapshotStateList<T>,
    heightCard: Dp = 136.dp,
    betweenMargin: Dp = 12.dp,
    cardStackController: CardStackController = rememberCardStackController((heightCard + (betweenMargin * 3))),
    onSwipe: (SwipeSide, T, Int) -> Unit = { _, _, _ -> },
    orientation: Orientation = Orientation.Horizontal,
    shadowSide: CardShadowSide = CardShadowSide.ShadowBottom,
    thresholdConfig: (Float, Float) -> ThresholdConfig = { _, _ -> FractionalThreshold(0.2f) },
    content: @Composable ((item: T) -> Unit)
) {

    val currentIndex = itemsList.size - 1

    cardStackController.onSwipe = {
        onSwipe(it, itemsList[currentIndex], currentIndex)
    }

    if (itemsList.isNotEmpty()) {
        Box(
            modifier = modifier
                .height((heightCard + (betweenMargin * 2.5f)))
        ) {

            (currentIndex - 2).takeIf { it >= 0 }?.let {
                Box(
                    modifier = Modifier
                        .shadowPadding(shadowSide, betweenMargin, cardStackController, 1f)
                        .shadowHorizontalPadding(16.dp, cardStackController, 1f)
                        .align(Alignment.Center)
                        .height(heightCard)
                        .shadow(0.dp, RoundedCornerShape(8.dp))
                ) {
                    content(itemsList[it])
                }
            }

            (currentIndex - 1).takeIf { it >= 0 }?.let {
                Box(
                    modifier = Modifier
                        .shadowPadding(shadowSide, betweenMargin, cardStackController, 2f)
                        .shadowHorizontalPadding(16.dp, cardStackController, 0f)
                        .align(Alignment.Center)
                        .height(heightCard)
                        .shadow(0.dp, RoundedCornerShape(8.dp))
                ) { content(itemsList[it]) }
            }

            Box(
                modifier = Modifier
                    .shadowPaddingLayerThree(shadowSide, betweenMargin)
                    .align(Alignment.Center)
                    .height(heightCard)
                    .draggableStack(
                        controller = cardStackController,
                        orientation = orientation,
                        thresholdConfig = thresholdConfig
                    )
                    .moveTo(
                        x = if (orientation == Orientation.Horizontal) cardStackController.offsetX.value else 0f,
                        y = if (orientation == Orientation.Horizontal) 0f else cardStackController.offsetY.value
                    )
                    .graphicsLayer {
                        rotationZ = cardStackController.rotation.value
                        scaleX = cardStackController.cardMainScale.value
                        scaleY = cardStackController.cardMainScale.value
                    }
                    .shadow(0.dp, RoundedCornerShape(8.dp))
            ) { content(itemsList[currentIndex]) }

        }

    }

}