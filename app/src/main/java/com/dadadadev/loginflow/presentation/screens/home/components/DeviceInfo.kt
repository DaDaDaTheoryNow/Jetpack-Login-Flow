package com.dadadadev.loginflow.presentation.screens.home.components

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.core.DataState
import com.dadadadev.loginflow.data.model.home.DeviceInfo
import com.dadadadev.loginflow.presentation.shared_components.NormalTextComponent

@Composable
fun DeviceInfoComponent(deviceInfo: DataState<DeviceInfo>) {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp),
    ) {
        Card {
            if (deviceInfo is DataState.Success) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.device_info),
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))
                NormalTextComponent(text = "${stringResource(id = R.string.model)}: ${deviceInfo.data.model}")
                NormalTextComponent(text = "${stringResource(id = R.string.brand)}: ${deviceInfo.data.brand}")
                NormalTextComponent(text = "${stringResource(id = R.string.manufacturer)}: ${deviceInfo.data.manufacturer}")
                Spacer(modifier = Modifier.height(5.dp))
            }
        }

        if (deviceInfo is DataState.Failure) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = deviceInfo.exception.toString(),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 18.sp, color = Color.Red)
            )
        }

        if (deviceInfo is DataState.Loading) {
            CircularProgressIndicator()
        }
    }
}