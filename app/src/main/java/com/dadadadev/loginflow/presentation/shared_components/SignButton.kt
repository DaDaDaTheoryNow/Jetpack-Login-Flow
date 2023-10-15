package com.dadadadev.loginflow.presentation.shared_components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun SignButtonComponent(
    value: String,
    onPressed: () -> Unit,
    loading: Boolean,
    errorMessage: String
) {

    val alphaAnimation = remember {
        androidx.compose.animation.core.Animatable(0f)
    }
    LaunchedEffect(Unit) {
        alphaAnimation.animateTo(1f, animationSpec = spring(stiffness = Spring.StiffnessLow))
    }

    if (!loading) {

        Button(
            onClick = onPressed,
            modifier = Modifier
                .graphicsLayer {
                    alpha = alphaAnimation.value
                }
                .fillMaxWidth()
                .heightIn(46.dp),
            colors = ButtonDefaults.buttonColors(Color.Transparent),
            contentPadding = PaddingValues(),
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .heightIn(46.dp)
                    .background(
                        brush = Brush.linearGradient(
                            listOf(
                                Color.Cyan,
                                MaterialTheme.colorScheme.primary,
                            ),
                        ),
                        shape = RoundedCornerShape(4.dp)
                    ),
                contentAlignment = Alignment.Center,
            ) {
                Text(
                    text = value,
                    style = TextStyle(
                        fontSize = 18.sp,
                        fontWeight = FontWeight.Bold,
                        color = Color.White
                    )
                )
            }
        }
    } else {
        CircularProgressIndicator()
    }

    Box(
        modifier = Modifier
            .animateContentSize()
            .padding(vertical = if (errorMessage.isNotEmpty()) 5.dp else 0.dp)
            .clip(RoundedCornerShape(25))
            .background(Color.Gray)
    ) {
        if (errorMessage.isNotEmpty()) {
            Text(
                modifier = Modifier.padding(3.dp),
                text = errorMessage,
                textAlign = TextAlign.Center,
                style = TextStyle(
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Bold,
                    fontStyle = FontStyle.Italic,
                    color = MaterialTheme.colorScheme.error
                )
            )
        }
    }
}
