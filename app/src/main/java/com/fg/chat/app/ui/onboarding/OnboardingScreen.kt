package com.fg.chat.app.ui.onboarding

import android.annotation.SuppressLint
import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.drawWithContent
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.R
import com.fg.chat.app.ui.theme.DarkBlue
import com.fg.chat.app.ui.theme.DarkCerulean
import com.fg.chat.app.ui.theme.LightBlue

@Composable
fun OnboardingScreen() {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFCCEEFF)),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.fillMaxWidth()
        ) {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .clip(RoundedCornerShape(24.dp))
                    .background(Color.White),
                contentAlignment = Alignment.Center
            ) {
                FillingChatBubblesAnimation()
            }
        }
    }
}

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun FillingChatBubblesAnimation() {
    // Biến điều khiển bắt đầu animation
    var startAnimation by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }
    // Tiến trình chạy từ 0.0 đến 1.0 (0% -> 100%)
    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 2000, // set animation time 2 seconds
            easing = LinearEasing // animation same loading progress
        ),
        finishedListener = {
            if (it == 1f) {
                visible = false
            }
        }
    )

    // active animation while screen is first drawn
    LaunchedEffect(Unit) {
        startAnimation = true
    }

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .fillMaxHeight() // Chiều cao chiếm phần lớn
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {

        if (visible) {
            // --- Bắt đầu phần Animation "Đổ màu" (chuẩn 100%) ---
            // Kích thước cố định cho icon để các phần khớp nhau
            val iconSize = 150.dp
            // Box tổng chứa hai lớp icon
            Box(
                modifier = Modifier.size(iconSize),
                contentAlignment = Alignment.Center
            ) {
                // Lớp nền 1: Icon màu nhạt (Trạng thái CHƯA tô)
                // (Bạn cần có file ảnh này, giả sử là ic_chat_light, giống hình 1)
                Image(
                    painter = painterResource(id = R.drawable.e_chat_191f1a),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                // Lớp phủ 2: Icon màu đậm (Trạng thái ĐÃ tô)
                // (File ảnh này có màu xanh đậm, giả sử là ic_chat_dark, giống hình 3)
                // Đây là "mặt nạ" dâng dần lên
                Image(
                    painter = painterResource(id = R.drawable.e_chat_191f3a),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            // Tính toán tọa độ Y để cắt từ dưới lên
                            // yTop = 0 là đỉnh, yTop = size.height là đáy
                            val yTop = size.height * (1f - progress)
                            // clipRect sẽ "giữ lại" phần nội dung bên trong vùng chỉ định
                            // Ở đây ta giữ từ yTop đến hết chiều cao (size.height)
                            clipRect(
                                top = yTop,
                                bottom = size.height,
                                left = 0f,
                                right = size.width
                            ) {
                                this@drawWithContent.drawContent()
                            }
                        }
                )
            }
        } else {
            Column(modifier = Modifier.fillMaxSize()) {
                val padding = LocalConfiguration.current.screenHeightDp.dp * 0.1f
                Box(
                    modifier = Modifier
                        .padding(0.dp, padding, 0.dp, 0.dp)
                        .fillMaxWidth(0.6f)
                        .fillMaxHeight(0.1f)
                        .align(Alignment.CenterHorizontally),
                    contentAlignment = Alignment.Center
                ) {
                    Row(modifier = Modifier.fillMaxSize()) {
                        Image(
                            painter = painterResource(id = R.drawable.e_chat_191f3a),
                            contentDescription = null,
                            contentScale = ContentScale.Fit
                        )
                        Text(
                            text = "E-Chat",
                            color = DarkCerulean,
                            style = TextStyle(
                                fontFamily = FontFamily.Default,
                                fontWeight = FontWeight.ExtraBold,
                                fontSize = 24.sp,
                                lineHeight = 24.sp,
                                letterSpacing = 0.5.sp
                            ),
                            modifier = Modifier
                                .align(alignment = Alignment.CenterVertically)
                                .padding(10.dp, 0.dp, 0.dp, 0.dp)
                        )
                    }
                }
                Spacer(modifier = Modifier.weight(0.7f))
                ChatBannerUI(modifier = Modifier.align(alignment = Alignment.CenterHorizontally))
                Spacer(modifier = Modifier.weight(1f))
            }
        }
    }
}

@Composable
fun ChatBannerUI(modifier: Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(40.dp),
        contentAlignment = Alignment.Center,
    ) {
        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .aspectRatio(1f)
                .padding(16.dp) // Thêm padding để không bị cắt đuôi
        ) {
            // Lấy kích thước của vùng vẽ (Canvas)
            val width = size.width
            val height = size.height

            // Định nghĩa các hằng số tính toán
            val strokeWidth = 10.dp.toPx() // Độ rộng đường viền (tùy chỉnh)
            val ovalRect = androidx.compose.ui.geometry.Rect(0f, 0f, width, height)
            // Vẽ cung tròn chính cho phần thân
            // sweepAngle là 320 độ để chừa lại khoảng hở cho cái đuôi
            drawArc(
                color = LightBlue,
                startAngle = 138f, // Bắt đầu ở góc dưới bên phải một chút
                sweepAngle = 335f,
                useCenter = false,
                topLeft = ovalRect.topLeft,
                size = ovalRect.size,
                style = Stroke(width = strokeWidth)
            )
            // Vẽ phần "đuôi" bong bóng bằng Path
            // Chúng ta sử dụng một đường cong bezier bậc hai để tạo độ cong mượt mà
            val tailPath = Path().apply {
                // define start point, at the end of the arc
                val startX = width * 0.31f
                val startY = height * 0.948f
                moveTo(startX, startY)

                // Vẽ đường cong đến điểm kết thúc của đuôi
                // Điểm kết thúc: nằm bên trái và thấp hơn một chút so với vòng tròn
                val endX = width * 0.128f // Kéo đuôi ra ngoài lề Canvas
                val endY = height * 0.81f

                // Điểm điều khiển (control point) để tạo độ cong mượt mà
                val controlX = -0.5f
                val controlY = height * 1.15f

                quadraticTo(controlX, controlY, endX, endY)
            }
            // Vẽ đường đuôi đã định nghĩa
            drawPath(
                path = tailPath,
                color = LightBlue,
                style = Stroke(width = strokeWidth)
            )
        }
        Column(horizontalAlignment = Alignment.CenterHorizontally) {
            Text(
                text = "Stay Connected",
                color = DarkBlue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
            Spacer(modifier = Modifier.height(4.dp))
            Text(
                text = "Stay Chatting",
                color = DarkBlue,
                fontSize = 20.sp,
                fontWeight = FontWeight.Bold,
                textAlign = TextAlign.Center
            )
        }
    }
}