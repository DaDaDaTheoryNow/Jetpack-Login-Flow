package com.dadadadev.loginflow.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun NormalTextComponent(text: String) {
    Text(
        text = text,
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = 30.dp),
        style = TextStyle(
            fontSize = 20.sp,
        ),
        textAlign = TextAlign.Center,
    )
}