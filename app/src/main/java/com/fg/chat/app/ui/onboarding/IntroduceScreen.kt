package com.fg.chat.app.ui.onboarding

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.fg.chat.app.R
import com.fg.chat.app.ui.theme.DarkBlueText
import com.fg.chat.app.ui.theme.LightBlue
import com.fg.chat.app.ui.theme.LightBlueBg
import com.fg.chat.app.ui.theme.MidBlue

val listImageIntroduce = listOf(
    R.drawable.image_introduce_1,
    R.drawable.image_introduce_2,
    R.drawable.image_introduce_3,
    R.drawable.image_introduce_4
)
val listMessageIntroduce = listOf(
    listOf(
        "Group Chatting",
        "Video And Voice Calls",
        "Message Encryption",
        "Cross-Platform Compatibility"
    ), listOf(
        "Connect with multiple members in group chats.",
        "Instantly connect via video and voice calls.",
        "Ensure privacy with encrypted messages.",
        "Access chats on any device seamlessly."
    )
)

@Composable
fun IntroduceScreen(onSkip:()->Unit) {
    var introduceItem by remember { mutableIntStateOf(0) }

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        Canvas(modifier = Modifier.fillMaxSize()) {
            val width = size.width
            val height = size.height

            // 1. Vẽ lớp màu xanh nhạt nhất (phía trên cùng)
            // Lớp này có thể là màu nền mặc định hoặc vẽ một hình chữ nhật

            // 2. Vẽ lớp đường cong thứ nhất (Màu xanh trung bình)
            val path1 = Path().apply {
                moveTo(0f, height * 0.5f) // Điểm bắt đầu bên trái
                // cubicTo hoặc quadraticBezierTo để tạo độ cong
                // Ở đây dùng quadraticBezierTo: (điểm điều khiển, điểm kết thúc)
                quadraticTo(
                    x1 = width / 2f, y1 = height * 0.7f,
                    x2 = width, y2 = height * 0.5f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            drawPath(path = path1, color = LightBlueBg)

            // 3. Vẽ lớp đường cong thứ hai (Màu xanh đậm hơn ở dưới)
            val path2 = Path().apply {
                moveTo(0f, height * 0.65F)
                quadraticTo(
                    x1 = width / 2f, y1 = height * 0.85f,
                    x2 = width, y2 = height * 0.65f
                )
                lineTo(width, height)
                lineTo(0f, height)
                close()
            }
            // Sử dụng alpha hoặc màu cụ thể để tạo hiệu ứng chồng lấp
            drawPath(path = path2, color = MidBlue.copy(alpha = 0.6f))
        }
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                painter = painterResource(id = listImageIntroduce[introduceItem]), // Thay thế bằng ID của bạn
                contentDescription = "Onboarding Illustration",
                modifier = Modifier
                    .fillMaxWidth()
                    .height(300.dp)
                    .padding(vertical = 32.dp),
                contentScale = ContentScale.Fit
            )

            Text(
                text = listMessageIntroduce[0][introduceItem],
                maxLines = 2,
                color = DarkBlueText,
                fontSize = 28.sp,
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(bottom = 8.dp)
                    .align(Alignment.CenterHorizontally),
                textAlign = TextAlign.Center,
            )

            Text(
                text = listMessageIntroduce[0][introduceItem],
                color = DarkBlueText,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 26.sp,
                modifier = Modifier.padding(24.dp, 48.dp)
            )
            Spacer(modifier = Modifier.weight(1f))
            Button(
                onClick = { /* Xử lý sự kiện click */ },
                modifier = Modifier
                    .padding(0.dp, 0.dp, 0.dp, 60.dp)
                    .fillMaxWidth()
                    .height(56.dp)
                    .padding(horizontal = 24.dp),
                shape = RoundedCornerShape(25.dp), // Góc bo tròn nhẹ
                colors = ButtonDefaults.buttonColors(containerColor = Color.White),
                elevation = ButtonDefaults.buttonElevation(defaultElevation = 8.dp) // Thêm bóng đổ
            ) {
                Text(
                    text = "Get started",
                    color = DarkBlueText,
                    fontSize = 20.sp,
                    fontWeight = FontWeight.Bold
                )
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 0.dp, start = 0.dp, end = 0.dp, bottom = 10.dp)
                    .padding(horizontal = 16.dp, vertical = 24.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                // Nút "Skip"
                TextButton(onClick = { onSkip.invoke() }) {
                    Text(
                        text = "Skip",
                        color = DarkBlueText.copy(alpha = 0.7f), // Màu xanh mờ hơn
                        fontSize = 18.sp
                    )
                }

                // page (indicators)
                Row(horizontalArrangement = Arrangement.Center) {
                    repeat(4) { index ->
                        val color =
                            if (index == introduceItem) DarkBlueText else DarkBlueText.copy(alpha = 0.3f)
                        val size =
                            if (index == introduceItem) 12.dp else 10.dp
                        Box(
                            modifier = Modifier
                                .padding(horizontal = 4.dp)
                                .size(size)
                                .clip(CircleShape)
                                .background(color)
                        )
                    }
                }
                Box(
                    modifier = Modifier
                        .size(56.dp)
                        .clip(CircleShape)
                        .background(LightBlue),
                    contentAlignment = Alignment.Center
                ) {
                    TextButton(onClick = { if (introduceItem < 3) introduceItem++ }) {
                        Text(
                            text = "Next",
                        )
                    }
                }
            }
        }
    }
}