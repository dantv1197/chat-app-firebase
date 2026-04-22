package com.fg.chat.app.ui.screen.onboarding

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
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
import android.annotation.SuppressLint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import com.fg.chat.app.R
import com.fg.chat.app.ui.theme.DarkBlue
import com.fg.chat.app.ui.theme.DarkCerulean
import com.fg.chat.app.ui.theme.LightBlue

@SuppressLint("ConfigurationScreenWidthHeight")
@Composable
fun OnboardingScreen(onFinish: () -> Unit) {
    // start controlling animation
    var startAnimation by remember { mutableStateOf(false) }
    var visible by remember { mutableStateOf(true) }
    // process start 0.0 to 1.0 (0% -> 100%)
    val progress by animateFloatAsState(
        targetValue = if (startAnimation) 1f else 0f,
        animationSpec = tween(
            durationMillis = 1000, // set animation time 2 seconds
            easing = LinearEasing // animation same loading progress
        ),
        finishedListener = {
            if (it == 1f) {
                visible = false
                startAnimation= false
            }
            CoroutineScope(Dispatchers.Default).launch {
                delay(2000)
                launch(Dispatchers.Main) {
                    onFinish.invoke()
                }
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
            .fillMaxHeight() // Take up most of the height
            .clip(RoundedCornerShape(24.dp)),
        contentAlignment = Alignment.Center
    ) {

        if (visible) {
            // --- Animation start painting ---
            // set icon size
            val iconSize = 150.dp
            Box(
                modifier = Modifier.size(iconSize),
                contentAlignment = Alignment.Center
            ) {
                // light color Icon
                Image(
                    painter = painterResource(id = R.drawable.e_chat_191f1a),
                    contentDescription = null,
                    modifier = Modifier.fillMaxSize(),
                    contentScale = ContentScale.Fit
                )
                // dark color Icon
                Image(
                    painter = painterResource(id = R.drawable.e_chat_191f3a),
                    contentDescription = null,
                    contentScale = ContentScale.Fit,
                    modifier = Modifier
                        .fillMaxSize()
                        .drawWithContent {
                            // Calculate the Y coordinates to cut from bottom to top,
                            // yTop = 0 is the top, yTop = size.height is the bottom
                            val yTop = size.height * (1f - progress)
                            // clipRect will "retain" the content inside the specified area
                            // Here we retain from yTop to the end of the height (size.height)
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
                .padding(16.dp) // Add padding to avoid clipping the tail
        ) {
            // Get the size of the drawing area (Canvas)
            val width = size.width
            val height = size.height

            // Define computational constants
            val strokeWidth = 10.dp.toPx() // Contour width (customizable)
            val ovalRect = androidx.compose.ui.geometry.Rect(0f, 0f, width, height)

            // Draw the main arc for the body
            // sweepAngle is 320 degrees to leave space for the tail
            drawArc(
                color = LightBlue,
                startAngle = 138f, // Starts in the bottom left corner
                sweepAngle = 335f, // end in the bottom left corner
                useCenter = false,
                topLeft = ovalRect.topLeft,
                size = ovalRect.size,
                style = Stroke(width = strokeWidth)
            )
            // Draw the bubble "tail" using Path
            // Use a quadratic bezier curve for smooth curvature
            val tailPath = Path().apply {
                // define start point, at the end of the arc
                val startX = width * 0.31f
                val startY = height * 0.948f
                moveTo(startX, startY)

                // Draw curve to the end point of the tail
                // End point: located on the left and slightly lower than the circle
                val endX = width * 0.128f // Pull the tail outside the Canvas boundary
                val endY = height * 0.81f

                // Control point to create smooth curvature
                val controlX = -0.5f
                val controlY = height * 1.15f

                quadraticTo(controlX, controlY, endX, endY)
            }
            // Draw the defined tail path
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
