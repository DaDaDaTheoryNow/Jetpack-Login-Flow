package com.dadadadev.loginflow.presentation.shared_components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material.icons.filled.Visibility
import androidx.compose.material.icons.filled.VisibilityOff
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusManager
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.data.model.sign.FieldState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PasswordTextFieldComponent(
    fieldState: FieldState,
    onValueChange: (String) -> Unit,
) {
    var passwordVisibility by rememberSaveable { mutableStateOf(false) }

    val localFocusManager: FocusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        textStyle = TextStyle(color = Color.Black, fontSize = 17.sp),
        label = {
            Text(
                text = stringResource(id = R.string.password),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 15.sp)
            )
        },
        leadingIcon = {
            Icon(imageVector = Icons.Filled.Lock, contentDescription = null)
        },
        trailingIcon = {
            val iconImage = if (passwordVisibility) {
                Icons.Filled.Visibility
            } else {
                Icons.Filled.VisibilityOff
            }

            IconButton(onClick = {
                passwordVisibility = !passwordVisibility
            }, content = {
                Icon(imageVector = iconImage, contentDescription = null)
            })
        },
        visualTransformation = if (passwordVisibility)
            VisualTransformation.None
        else
            PasswordVisualTransformation(),
        colors = TextFieldDefaults.outlinedTextFieldColors(
            focusedBorderColor = MaterialTheme.colorScheme.primary,
            focusedLabelColor = MaterialTheme.colorScheme.primary,
            cursorColor = MaterialTheme.colorScheme.primary,
        ),
        keyboardOptions = KeyboardOptions(
            imeAction = ImeAction.Done,
            keyboardType = KeyboardType.Password
        ),
        keyboardActions = KeyboardActions {
            localFocusManager.clearFocus()
        },
        isError = fieldState.errorMessage.isNotEmpty(),
        singleLine = true,
        value = fieldState.value,
        onValueChange = { onValueChange(it) },
        supportingText = {
            if (fieldState.errorMessage.isNotEmpty()) {
                Text(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(bottom = 3.dp),
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