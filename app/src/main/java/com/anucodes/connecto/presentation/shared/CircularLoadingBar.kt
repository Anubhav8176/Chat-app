package com.anucodes.connecto.presentation.shared

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.pointer.PointerEventPass
import androidx.compose.ui.input.pointer.pointerInput
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp


@Composable
fun CircularLoadingBar(
    isVisible: Boolean,
    scrimColor: Color = Color.Black.copy(alpha = 0.6f),
    indicatorColor: Color? = null,
    strokeWidth: Dp = 4.dp,
    indicatorSize: Dp = 56.dp,
) {
    if (!isVisible) return

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(scrimColor)
            .pointerInput(Unit) {
                awaitPointerEventScope {
                    while (true) {
                        awaitPointerEvent(pass = PointerEventPass.Initial).changes.forEach {
                            it.consume()
                        }
                    }
                }
            },
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(indicatorSize),
            color = indicatorColor ?: MaterialTheme.colorScheme.primary,
            strokeWidth = strokeWidth,
            trackColor = (indicatorColor ?: MaterialTheme.colorScheme.primary).copy(alpha = 0.2f)
        )
    }
}