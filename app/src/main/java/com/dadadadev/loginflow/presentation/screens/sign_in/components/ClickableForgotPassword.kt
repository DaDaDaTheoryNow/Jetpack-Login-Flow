package com.dadadadev.loginflow.presentation.screens.sign_in.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp

@Composable
fun ClickableForgotPasswordComponent(value: String, onForgotPasswordPressed: () -> Unit) {
    val annotatedString = buildAnnotatedString {
        withStyle(
            style = SpanStyle(
                fontStyle = FontStyle.Italic,
                color = Color.Gray,
                textDecoration = TextDecoration.Underline
            )
        ) {
            pushStringAnnotation("ForgotPassword", "ForgotPassword")
            append(value)
        }
    }

    ClickableText(
        modifier = Modifier.padding(bottom = 10.dp),
        text = annotatedString,
        onClick = { offset ->
            annotatedString.getStringAnnotations(offset, offset).firstOrNull().also {
                if (it != null && it.item == "ForgotPassword") {
                    onForgotPasswordPressed()
                }
            }
        })
}