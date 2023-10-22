package com.dadadadev.loginflow.presentation.screens.home.components

import androidx.compose.animation.animateContentSize
import androidx.compose.animation.core.EaseIn
import androidx.compose.animation.core.tween
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
import com.dadadadev.loginflow.data.model.home.UserInfo
import com.dadadadev.loginflow.presentation.shared_components.NormalTextComponent

@Composable
fun UserInfoComponent(userInfo: DataState<UserInfo>) {
    Box(
        modifier = Modifier
            .padding(horizontal = 15.dp, vertical = 5.dp),
    ) {
        Card(
            modifier = Modifier.animateContentSize(
                animationSpec = tween(easing = EaseIn)
            )
        ) {
            if (userInfo is DataState.Success) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = stringResource(id = R.string.user_info),
                    style = TextStyle(
                        fontSize = 23.sp,
                        fontWeight = FontWeight.Bold,
                    ),
                    textAlign = TextAlign.Center,
                )
                Spacer(modifier = Modifier.height(12.dp))
                NormalTextComponent(text = "${stringResource(id = R.string.first_name)}: ${userInfo.data.firstName}")
                NormalTextComponent(text = "${stringResource(id = R.string.last_name)}: ${userInfo.data.lastName}")
                Spacer(modifier = Modifier.height(5.dp))
            }

            if (userInfo is DataState.Loading) {
                CircularProgressIndicator(modifier = Modifier.padding(5.dp))
            }
        }

        if (userInfo is DataState.Failure) {
            Text(
                modifier = Modifier.padding(5.dp),
                text = userInfo.exception.toString(),
                textAlign = TextAlign.Center,
                style = TextStyle(fontSize = 18.sp, color = Color.Red)
            )
        }
    }
}