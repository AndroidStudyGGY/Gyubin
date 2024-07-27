package com.gyub.movieticket.ui.component

import androidx.compose.foundation.layout.Box
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment.Companion.Center
import androidx.compose.ui.Modifier

/**
 *
 *
 * @author   Gyub
 * @created  2024/07/27
 */

@Composable
fun LoadingIndicator(modifier: Modifier = Modifier) {
    Box(modifier = modifier) {
        CircularProgressIndicator(modifier = Modifier.align(Center))
    }
}