package com.example.inteliheadsinternship.util

import android.annotation.SuppressLint
import android.inputmethodservice.Keyboard
import androidx.compose.animation.*
import androidx.compose.animation.core.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.Icon
import androidx.compose.material.IconButton
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDownward
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.inteliheadsinternship.R
import androidx.core.content.ContextCompat
import com.example.inteliheadsinternship.data.Data
import com.example.inteliheadsinternship.data.SubCategory
import com.example.inteliheadsinternship.ui.screens.subCategories
import com.example.inteliheadsinternship.util.Constants.COLLAPSE_ANIMATION_DURATION
import com.example.inteliheadsinternship.util.Constants.EXPAND_ANIMATION_DURATION
import com.example.inteliheadsinternship.util.Constants.FADE_IN_ANIMATION_DURATION
import com.example.inteliheadsinternship.util.Constants.FADE_OUT_ANIMATION_DURATION


@Immutable
data class ExpandableCardModel(val id: Int, val title: String)

val cardCollapsedBackgroundColor = Color(0xFFFEFFFD)
val cardExpandedBackgroundColor = Color(0xFF918F8B)

@ExperimentalAnimationApi
@Composable
fun ExpandableCard(
    card: Data,
    onCardArrowClick: () -> Unit,
    expanded: Boolean,
) {
    val transitionState = remember {
        MutableTransitionState(expanded).apply {
            targetState = !expanded
        }
    }
    val transition = updateTransition(transitionState)
    val cardBgColor by transition.animateColor({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }) {
        if (expanded) cardExpandedBackgroundColor else cardCollapsedBackgroundColor
    }
    val cardPaddingHorizontal by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }) {
        if (expanded) 48.dp else 24.dp
    }
    val cardElevation by transition.animateDp({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }) {
        if (expanded) 24.dp else 4.dp
    }
    val cardRoundedCorners by transition.animateDp({
        tween(
            durationMillis = EXPAND_ANIMATION_DURATION,
            easing = FastOutSlowInEasing
        )
    }) {
        if (expanded) 0.dp else 16.dp
    }
    val arrowRotationDegree by transition.animateFloat({
        tween(durationMillis = EXPAND_ANIMATION_DURATION)
    }) {
        if (expanded) 0f else 180f
    }

    Card(
        backgroundColor = cardBgColor,
        contentColor = Color(
            ContextCompat.getColor(
                LocalContext.current,
                R.color.purple_700
            )
        ),
        elevation = cardElevation,
        shape = RoundedCornerShape(cardRoundedCorners),
        modifier = Modifier
            .fillMaxWidth()
            .padding(
                horizontal = cardPaddingHorizontal,
                vertical = 8.dp
            )
    ) {
        Column {
            Box {
                CardArrow(
                    degrees = arrowRotationDegree,
                    onClick = onCardArrowClick
                )
                CardTitle(title = card.name)
            }
            ExpandableContent(visible = expanded, initialVisibility = expanded, card = card.subCategory)
        }
    }
}

@Composable
fun CardTitle(title: String) {
    Text(
        text = title,
        modifier = Modifier
            .fillMaxWidth()
            .padding(16.dp),
        textAlign = TextAlign.Center,
    )
}


@Composable
fun CardArrow(
    degrees: Float,
    onClick: () -> Unit
) {
    IconButton(
        onClick = onClick,
        content = {
            Icon(
                imageVector = Icons.Filled.ArrowDownward,
                contentDescription = "Expandable Arrow",
                modifier = Modifier.rotate(degrees),

            )
        }
    )
}

@ExperimentalAnimationApi
@SuppressLint("RememberReturnType")
@Composable
fun ExpandableContent(
    visible: Boolean = true,
    initialVisibility: Boolean = false,
    card : List<SubCategory>
) {
    val enterFadeIn = remember {
        fadeIn(
            animationSpec = TweenSpec(
                durationMillis = FADE_IN_ANIMATION_DURATION,
                easing = FastOutLinearInEasing
            )
        )
    }
    val enterExpand = remember {
        expandVertically(animationSpec = tween(EXPAND_ANIMATION_DURATION))
    }
    val exitFadeOut = remember {
        fadeOut(
            animationSpec = TweenSpec(
                durationMillis = FADE_OUT_ANIMATION_DURATION,
                easing = LinearOutSlowInEasing
            )
        )
    }
    val exitCollapse = remember {
        shrinkVertically(animationSpec = tween(COLLAPSE_ANIMATION_DURATION))
    }
    AnimatedVisibility(
        visible = visible,
        initiallyVisible = initialVisibility,
        enter = enterExpand + enterFadeIn,
        exit = exitCollapse + exitFadeOut
    ) {
        Row(modifier = Modifier.padding(8.dp)) {
            Spacer(modifier = Modifier.heightIn(100.dp))
            LazyRow{
                itemsIndexed(card){_,card->
                    subCategories(data = card)

                }
            }

        }

    }
}