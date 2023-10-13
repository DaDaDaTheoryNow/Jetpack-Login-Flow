package com.dadadadev.loginflow.presentation.home.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.dadadadev.loginflow.R
import com.dadadadev.loginflow.components.NormalTextComponent
import com.dadadadev.loginflow.domain.model.DeviceInfo

@Composable
fun DeviceInfoComponent(deviceInfo: DeviceInfo, error: String) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(15.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Card() {
            if (error.isEmpty()) {
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
                NormalTextComponent(text = "${stringResource(id = R.string.model)}: ${deviceInfo.model}")
                NormalTextComponent(text = "${stringResource(id = R.string.brand)}: ${deviceInfo.brand}")
                NormalTextComponent(text = "${stringResource(id = R.string.manufacturer)}: ${deviceInfo.manufacturer}")
                Spacer(modifier = Modifier.height(5.dp))
            } else {
                Text(
                    modifier = Modifier.padding(5.dp),
                    text = error,
                    textAlign = TextAlign.Center,
                    style = TextStyle(fontSize = 18.sp, color = Color.Red)
                )
            }
        }
    }
}