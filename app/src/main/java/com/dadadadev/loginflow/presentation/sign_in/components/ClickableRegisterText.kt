package com.dadadadev.loginflow.presentation.sign_in.components

import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp

@Composable
fun ClickableRegisterTextComponent(onRegisterPressed: () -> Unit?) {
    val initialText = "Don't have an account yet?"
    val clickableText = " Register"

    val annotation = "Register"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(
            style = SpanStyle(
                color = MaterialTheme.colorScheme.primary,
            )
        ) {
            pushStringAnnotation(tag = annotation, annotation = annotation)
            append(clickableText)
        }
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(
            fontWeight = FontWeight.Medium,
            fontSize = 16.sp,
            textAlign = TextAlign.Center
        )
    ) { offset ->
        annotatedString.getStringAnnotations(offset, offset).firstOrNull().also {
            if (it != null && it.item == annotation) {
                onRegisterPressed()
            }
        }
    }
}