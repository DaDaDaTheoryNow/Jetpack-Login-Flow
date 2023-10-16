package com.dadadadev.loginflow.presentation.shared_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dadadadev.loginflow.data.model.sign.FieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicTextFieldComponent(
    fieldState: FieldState,
    labelValue: String,
    imageVector: ImageVector,
    onValueChange: (String) -> Unit,
) {
    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        textStyle = TextStyle(color = Color.Black, fontSize = 17.sp),
        shape = RoundedCornerShape(10.dp),
        label = {
            Text(
                text = labelValue,
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 15.sp)
            )
        },
        leadingIcon = {
            Icon(imageVector = imageVector, contentDescription = null)
        },
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        isError = fieldState.errorMessage.isNotEmpty(),
        keyboardOptions = KeyboardOptions(imeAction = ImeAction.Next),
        singleLine = true,
        value = fieldState.value,
        onValueChange = {
            onValueChange(it)
        },
        supportingText = {
            if (fieldState.errorMessage.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
                    textAlign = TextAlign.Start,
                    text = fieldState.errorMessage,
                    color = MaterialTheme.colorScheme.error,
                    style = TextStyle(
                        fontStyle = FontStyle.Italic,
                        fontSize = 13.sp,
                    )
                )
            }
        },
    )
}