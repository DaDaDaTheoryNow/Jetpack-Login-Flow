package com.dadadadev.loginflow.features.sign_up.presentation.components

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.ClickableText
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Divider
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun PrivacyPolicyCheckBoxComponent(
    onPrivacyPolicyPressed: () -> Unit,
    onCheckBoxPressed: (Boolean) -> Unit,
    checkBoxState: Boolean,
    checkBoxError: Boolean,
) {
    Column {
        if (checkBoxError)
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(top = 15.dp), color = MaterialTheme.colorScheme.error
            )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .heightIn(56.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Checkbox(
                checked = checkBoxState, onCheckedChange = onCheckBoxPressed,
            )
            ClickablePrivacyPolicyTextComponent(onPrivacyPolicyPressed)
        }

        if (checkBoxError)
            Divider(
                Modifier
                    .fillMaxWidth()
                    .padding(bottom = 15.dp), color = MaterialTheme.colorScheme.error
            )
    }
}

@Composable
fun ClickablePrivacyPolicyTextComponent(onPrivacyPolicyPressed: () -> Unit?) {
    val initialText = "You must accept"
    val clickableText = " Privacy Policy "
    val endText = "to continue"

    val annotatedString = buildAnnotatedString {
        append(initialText)
        withStyle(style = SpanStyle(color = MaterialTheme.colorScheme.primary)) {
            pushStringAnnotation(tag = "Privacy Policy", annotation = "Privacy Policy")
            append(clickableText)
        }
        append(endText)
    }

    ClickableText(
        text = annotatedString,
        style = TextStyle(textAlign = TextAlign.Center, letterSpacing = 1.sp, fontSize = 13.sp)
    ) { offset ->
        annotatedString.getStringAnnotations(offset, offset).firstOrNull().also {
            if (it != null && it.item == "Privacy Policy") {
                onPrivacyPolicyPressed()
            }
        }
    }
}