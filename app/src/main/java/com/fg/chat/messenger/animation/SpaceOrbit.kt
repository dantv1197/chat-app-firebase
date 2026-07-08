package com.fg.chat.messenger.animation

import androidx.compose.animation.core.LinearEasing
import androidx.compose.animation.core.RepeatMode
import androidx.compose.animation.core.animateFloat
import androidx.compose.animation.core.infiniteRepeatable
import androidx.compose.animation.core.keyframes
import androidx.compose.animation.core.rememberInfiniteTransition
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.FilterChipDefaults
import androidx.compose.material3.Slider
import androidx.compose.material3.SliderDefaults
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.asComposePath
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.rotate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.PathParser
import kotlin.math.min

enum class SpaceTheme(
    val displayName: String,
    val primaryCyan: Color,
    val orbitMiddle: Color,
    val orbitOuter: Color
) {
    COSMIC_GLOW("Cosmic Glow", Color(0xFF66FFCC), Color(0xFFCBB5FF), Color(0xFF00CCFF)),
    NEON_CYBER("Neon Cyber", Color(0xFFFF007F), Color(0xFF39FF14), Color(0xFFFFCC00)),
    RETRO_GOLD("Retro Gold", Color(0xFFFFD700), Color(0xFFFFA500), Color(0xFFFF4500))
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun SpaceOrbitScreen() {
    var speedMultiplier by remember { mutableFloatStateOf(1.0f) }
    var currentTheme by remember { mutableStateOf(SpaceTheme.COSMIC_GLOW) }
    var isOrbitDrawingActive by remember { mutableStateOf(true) }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFF020917))
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Tiêu đề ứng dụng
        Text(
            text = "SPACE ORBIT SIMULATOR",
            fontSize = 24.sp,
            color = currentTheme.primaryCyan,
            modifier = Modifier.padding(top = 16.dp, bottom = 4.dp)
        )
        Text(
            text = "Bản dựng hoạt ảnh tương tác 100% bằng Jetpack Compose Canvas",
            fontSize = 12.sp,
            color = Color.Gray,
            modifier = Modifier.padding(bottom = 16.dp)
        )

        // Khung hiển thị Canvas chính
        Box(
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(Color(0xFF030E21))
                .padding(8.dp),
            contentAlignment = Alignment.Center
        ) {
            SpaceOrbitCanvas(
                speedMultiplier = speedMultiplier,
                theme = currentTheme,
                drawOrbits = isOrbitDrawingActive
            )
        }

        Spacer(modifier = Modifier.height(16.dp))

        Card(
            modifier = Modifier
                .fillMaxWidth()
                .padding(bottom = 16.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFF0C1930)),
            shape = RoundedCornerShape(16.dp)
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement = Arrangement.spacedBy(12.dp)
            ) {
                // Điều khiển Tốc độ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = "Tốc độ: ${(speedMultiplier * 100).toInt()}%",
                        color = Color.White,
                        modifier = Modifier.width(100.dp)
                    )
                    Slider(
                        value = speedMultiplier,
                        onValueChange = { speedMultiplier = it },
                        valueRange = 0.1f..3.0f,
                        modifier = Modifier.weight(1f),
                        colors = SliderDefaults.colors(
                            thumbColor = currentTheme.primaryCyan,
                            activeTrackColor = currentTheme.primaryCyan
                        )
                    )
                }

                // Chọn Chủ đề màu
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Chủ đề màu:", color = Color.White)
                    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
                        SpaceTheme.entries.forEach { theme ->
                            FilterChip(
                                selected = currentTheme == theme,
                                onClick = { currentTheme = theme },
                                label = { Text(theme.displayName) },
                                colors = FilterChipDefaults.filterChipColors(
                                    selectedContainerColor = theme.primaryCyan,
                                    selectedLabelColor = Color.Black,
                                    labelColor = Color.LightGray
                                )
                            )
                        }
                    }
                }

                // Nút chuyển đổi phụ
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(text = "Hiện đường quỹ đạo:", color = Color.White)
                    Switch(
                        checked = isOrbitDrawingActive,
                        onCheckedChange = { isOrbitDrawingActive = it },
                        colors = SwitchDefaults.colors(
                            checkedThumbColor = currentTheme.primaryCyan,
                            checkedTrackColor = currentTheme.primaryCyan.copy(alpha = 0.5f)
                        )
                    )
                }
            }
        }
    }
}

@Composable
fun SpaceOrbitCanvas(
    speedMultiplier: Float,
    theme: SpaceTheme,
    drawOrbits: Boolean
) {
    // Lưu và ghi nhớ toàn bộ Paths được parse từ dữ liệu vector XML
    val paths = remember {
        object {
            val earthBorder = PathParser.createPathFromPathData(
                "M348.19,144.03C379.07,144.03 404.09,169.05 404.09,199.54C404.09,230.42 379.07,255.44 348.19,255.44C317.7,255.44 292.68,230.42 292.68,199.54C292.68,169.05 317.7,144.03 348.19,144.03Z"
            ).asComposePath()

            val earthOcean = PathParser.createPathFromPathData(
                "M347.96,144.6C378.31,144.6 403,169.18 403,199.53C403,229.87 378.31,254.56 347.96,254.56C317.62,254.56 293.03,229.87 293.03,199.53C293.03,169.18 317.62,144.6 347.96,144.6Z"
            ).asComposePath()

            val innerOrbit = PathParser.createPathFromPathData(
                "M347.96,133.79C384.28,133.79 413.7,163.21 413.7,199.53C413.7,235.84 384.28,265.26 347.96,265.26C311.65,265.26 282.23,235.84 282.23,199.53C282.23,163.21 311.65,133.79 347.96,133.79Z"
            ).asComposePath()

            val middleOrbit = PathParser.createPathFromPathData(
                "M387.33,298.63C332.64,320.34 270.67,293.59 248.96,238.9C227.21,184.11 254.01,122.23 308.7,100.52C363.39,78.82 425.32,105.48 447.07,160.26C468.78,214.95 442.02,276.93 387.33,298.63Z"
            ).asComposePath()

            val outerOrbit = PathParser.createPathFromPathData(
                "M404.62,342.31C325.82,373.55 236.61,334.98 205.38,256.18C174.15,177.38 212.71,88.18 291.51,56.95C370.31,25.71 459.52,64.28 490.75,143.08C521.98,221.88 483.42,311.08 404.62,342.31Z"
            ).asComposePath()

            // Các mảnh lục địa Trái Đất (Continents)
            val continent1 = PathParser.createPathFromPathData(
                "M217.94,237.44C219.89,239.4 228.1,230.01 221.46,222.98C218.33,219.85 209.73,196.4 223.02,194.05C228.49,192.88 231.23,191.32 233.18,185.84C236.31,179.2 191.75,179.98 216.38,172.16C219.11,171.38 221.07,176.46 227.71,176.46C242.57,177.24 257.81,151.83 229.28,153.01C209.34,154.18 217.94,142.45 197.22,145.19C183.93,146.75 185.89,137.76 171.03,143.62C162.04,147.14 191.36,147.14 169.47,161.22C161.26,166.3 166.34,170.6 170.64,176.07C177.29,183.5 167.9,180.76 167.12,187.8C166.34,197.96 185.49,195.22 190.58,202.26C192.53,204.22 191.75,210.47 191.75,213.6C191.75,227.28 200.35,221.02 206.21,221.41C219.11,222.2 208.56,234.31 217.94,237.44Z"
            ).asComposePath()

            val continent2 = PathParser.createPathFromPathData(
                "M144.84,156.92C142.1,150.66 88.55,154.96 94.8,164.34C98.71,169.82 110.44,166.3 105.36,177.24C103.01,182.32 116.7,192.88 118.26,191.71C119.04,191.71 119.82,186.23 121.39,185.06C126.47,183.89 131.16,190.14 136.24,182.72C139.76,177.24 133.11,174.9 139.76,172.55C146.01,169.82 150.31,165.12 144.84,156.92Z"
            ).asComposePath()

            val continent3 = PathParser.createPathFromPathData(
                "M135.46,246.04C125.69,250.73 124.51,226.11 115.91,224.54C108.1,223.37 111.61,212.42 112.79,206.95C113.96,201.48 107.31,201.87 112,197.96C116.3,194.83 126.47,196.79 131.16,196.4C139.76,195.62 143.67,205.39 138.98,212.42C127.64,230.8 140.54,220.24 139.37,235.1C138.98,242.13 138.59,244.48 135.46,246.04Z"
            ).asComposePath()

            // Vòng đai Thổ Tinh (Saturn Ring)
            val saturnRing = PathParser.createPathFromPathData(
                "M222.84,110.9m-37.95,10.17a8.68,39.28 75,1 1,75.89 -20.34a8.68,39.28 75,1 1,-75.89 20.34"
            ).asComposePath()

            val saturnClip = PathParser.createPathFromPathData(
                "M199.57,105.61C187.12,111.16 179.51,117.54 180.74,122.17C182.49,128.96 202.86,129.47 226.11,123.2C249.46,116.92 266.84,106.43 264.99,99.64C263.76,95.01 253.88,93.26 240.41,94.7C242.98,97.48 244.93,100.98 245.96,104.78L199.77,117.13C199.26,115.17 198.95,113.01 198.95,110.85C198.95,109.11 199.16,107.36 199.57,105.61Z"
            ).asComposePath()
        }
    }

    val infiniteTransition = rememberInfiniteTransition(label = "UniverseTransition")

    // 1. Lục địa Trái Đất cuộn dịch chuyển ngang từ trái sang phải
    val earthScroll by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 230f, // Độ rộng chu kỳ dịch chuyển lặp
        animationSpec = infiniteRepeatable(
            animation = tween((12000 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "EarthScroll"
    )

    // 2. Quỹ đạo giữa xoay cùng chiều kim đồng hồ
    val middleRotation by infiniteTransition.animateFloat(
        initialValue = 0f,
        targetValue = 360f,
        animationSpec = infiniteRepeatable(
            animation = tween((20000 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "MiddleOrbit"
    )

    // 3. Quỹ đạo ngoài xoay ngược chiều kim đồng hồ
    val outerRotation by infiniteTransition.animateFloat(
        initialValue = 360f,
        targetValue = 0f,
        animationSpec = infiniteRepeatable(
            animation = tween((28000 / speedMultiplier).toInt(), easing = LinearEasing),
            repeatMode = RepeatMode.Restart
        ), label = "OuterOrbit"
    )

    // 4. Nhịp đập nhấp nháy cho các vì sao xung quanh
    val starPulseRegular by infiniteTransition.animateFloat(
        initialValue = 0.3f,
        targetValue = 1.0f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                0.3f at 0
                1.0f at 1500
                0.3f at 3000
            },
            repeatMode = RepeatMode.Restart
        ), label = "StarRegular"
    )

    val starPulseDelayed by infiniteTransition.animateFloat(
        initialValue = 1.0f,
        targetValue = 0.3f,
        animationSpec = infiniteRepeatable(
            animation = keyframes {
                durationMillis = 3000
                1.0f at 0
                0.3f at 1500
                1.0f at 3000
            },
            repeatMode = RepeatMode.Restart
        ), label = "StarDelayed"
    )

    Canvas(
        modifier = Modifier
            .fillMaxSize()
            .aspectRatio(700f / 400f) // Giữ nguyên tỉ lệ thiết kế gốc 700x400
    ) {
        val targetWidth = 700f
        val targetHeight = 400f

        // Tính tỷ lệ co giãn để luôn vừa vặn màn hình điện thoại mà không bị vỡ bố cục
        val scale = min(size.width / targetWidth, size.height / targetHeight)
        val dx = (size.width - targetWidth * scale) / 2f
        val dy = (size.height - targetHeight * scale) / 2f

        val centerPointX = 348f
        val centerPointY = 199.5f

        withTransform({
            translate(dx, dy)
            scale(scale, scale, pivot = Offset.Zero)
        }) {

            // --- 1. VẼ CÁC NGÔI SAO NHẤP NHÁY & ĐƯỜNG TRANG TRÍ CHẠY NỀN ---
            // Chữ thập góc trái trên (Nhấp nháy đồng bộ 1)
            withTransform({
                scale(
                    starPulseRegular,
                    starPulseRegular,
                    pivot = Offset(204.46f, 52.425f)
                )
            }) {
                drawLine(
                    Color(0xFF99FFFF),
                    Offset(209.19f, 47.69f),
                    Offset(199.73f, 57.16f),
                    strokeWidth = 2.98f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    Color(0xFF99FFFF),
                    Offset(199.73f, 47.69f),
                    Offset(209.19f, 57.16f),
                    strokeWidth = 2.98f,
                    cap = StrokeCap.Round
                )
            }

            // Chữ thập nhỏ góc trái dưới (Nhấp nháy đồng bộ 2)
            withTransform({
                scale(
                    starPulseDelayed,
                    starPulseDelayed,
                    pivot = Offset(165.165f, 118.675f)
                )
            }) {
                drawLine(
                    Color(0xFFFFEEAA),
                    Offset(168.15f, 115.69f),
                    Offset(162.18f, 121.66f),
                    strokeWidth = 2.98f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    Color(0xFFFFEEAA),
                    Offset(162.18f, 115.69f),
                    Offset(168.15f, 121.66f),
                    strokeWidth = 2.98f,
                    cap = StrokeCap.Round
                )
            }

            // Chữ thập góc phải (Nhấp nháy đồng bộ 1)
            withTransform({
                scale(
                    starPulseRegular,
                    starPulseRegular,
                    pivot = Offset(513.89f, 257.34f)
                )
            }) {
                drawLine(
                    Color(0xFF99FFFF),
                    Offset(516.87f, 254.36f),
                    Offset(510.91f, 260.32f),
                    strokeWidth = 2.98f,
                    cap = StrokeCap.Round
                )
                drawLine(
                    Color(0xFF99FFFF),
                    Offset(510.91f, 254.36f),
                    Offset(516.87f, 260.32f),
                    strokeWidth = 2.98f,
                    cap = StrokeCap.Round
                )
            }

            // Đường răng cưa màu hồng ở góc dưới
            val zigzagPath = Path().apply {
                moveTo(169.17f, 292.93f)
                lineTo(174.83f, 298.69f)
                lineTo(180.59f, 292.93f)
                lineTo(186.35f, 298.69f)
                lineTo(192.11f, 292.93f)
                lineTo(197.77f, 298.69f)
            }
            drawPath(
                zigzagPath,
                Color(0xFFFF00FF),
                style = Stroke(width = 2.98f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            // --- 2. VẼ QUY ĐẠO TRONG CÙNG (Tĩnh - Màu Tím Thẫm) ---
            if (drawOrbits) {
                drawPath(paths.innerOrbit, Color(0xFF9900FF), style = Stroke(width = 2.98f))
            }

            // --- 3. VẼ QUỸ ĐẠO GIỮA VÀ CÁC HÀNH TINH (Quay cùng chiều) ---
            rotate(degrees = middleRotation, pivot = Offset(centerPointX, centerPointY)) {
                if (drawOrbits) {
                    drawPath(paths.middleOrbit, theme.orbitMiddle, style = Stroke(width = 2.98f))
                }

                // Hành tinh tím nhạt đầu quỹ đạo
                drawCircle(Color(0xFF4B84F8), radius = 19.75f, center = Offset(351.41f, 93.01f))
                drawCircle(
                    theme.orbitMiddle,
                    radius = 19.75f,
                    center = Offset(351.41f, 93.01f),
                    style = Stroke(width = 2.98f)
                )

                // Mặt trăng nhỏ màu trắng xám đi kèm
                drawCircle(Color(0xFF020917), radius = 7.92f, center = Offset(386.9f, 298.14f))
                drawCircle(
                    Color(0xFFE6E6E6),
                    radius = 7.92f,
                    center = Offset(386.9f, 298.14f),
                    style = Stroke(width = 2.98f)
                )
            }

            // --- 4. VẼ QUỸ ĐẠO NGOÀI VÀ HÀNH TINH ĐẶC BIỆT (Quay ngược chiều) ---
            rotate(degrees = outerRotation, pivot = Offset(centerPointX, centerPointY)) {
                if (drawOrbits) {
                    drawPath(
                        paths.outerOrbit,
                        theme.orbitOuter,
                        style = Stroke(
                            width = 2.98f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                // Hành tinh Cam hỏa
                drawCircle(Color(0xFF020917), radius = 22.32f, center = Offset(407.73f, 341.08f))
                drawCircle(
                    Color(0xFFFF6600),
                    radius = 22.32f,
                    center = Offset(407.73f, 341.08f),
                    style = Stroke(width = 2.98f)
                )

                // Hành tinh Vàng kim nhỏ
                drawCircle(Color(0xFF020917), radius = 7.92f, center = Offset(433.76f, 72.28f))
                drawCircle(
                    Color(0xFFFFCC00),
                    radius = 7.92f,
                    center = Offset(433.76f, 72.28f),
                    style = Stroke(width = 2.98f)
                )

                // Hành tinh Saturn (Hồng + Vành Đai Nhẫn 3D)
                // Vẽ thân hình tròn hồng trước
                drawCircle(Color(0xFF020917), radius = 22.32f, center = Offset(222.82f, 110.85f))
                drawCircle(
                    Color(0xFFFF0066),
                    radius = 22.32f,
                    center = Offset(222.82f, 110.85f),
                    style = Stroke(width = 2.98f)
                )

                // Cắt lớp thông minh (Clip Path) để tạo hiệu ứng 3D vành đai ôm trước / sau hành tinh
                clipPath(paths.saturnClip) {
                    drawPath(paths.saturnRing, Color(0xFFCCFFCC), style = Stroke(width = 2f))
                }
            }

            // --- 5. TRÁI ĐẤT TRUNG TÂM (Tự quay mô phỏng bằng cuộn lục địa) ---
            // Nền đại dương màu đen/xanh thẫm đặc trưng của không gian
            drawPath(paths.earthOcean, Color(0xFF020917))
            drawPath(
                paths.earthOcean,
                theme.primaryCyan,
                style = Stroke(width = 2.98f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )

            // Giới hạn lục địa chỉ được hiển thị trong bo tròn Trái Đất (Clip)
            clipPath(paths.earthBorder) {
                // Vẽ tập lục địa thứ 1 chạy dịch chuyển tịnh tiến
                withTransform({
                    translate(left = earthScroll - 230f, top = 0f)
                }) {
                    drawPath(
                        paths.continent1,
                        theme.primaryCyan,
                        style = Stroke(
                            width = 3.13f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                    drawPath(
                        paths.continent2,
                        theme.primaryCyan,
                        style = Stroke(
                            width = 3.13f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                    drawPath(
                        paths.continent3,
                        theme.primaryCyan,
                        style = Stroke(
                            width = 3.13f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }

                // Vẽ tập lục địa thứ 2 chạy gối đầu liền mạch phía sau
                withTransform({
                    translate(left = earthScroll, top = 0f)
                }) {
                    drawPath(
                        paths.continent1,
                        theme.primaryCyan,
                        style = Stroke(
                            width = 3.13f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                    drawPath(
                        paths.continent2,
                        theme.primaryCyan,
                        style = Stroke(
                            width = 3.13f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                    drawPath(
                        paths.continent3,
                        theme.primaryCyan,
                        style = Stroke(
                            width = 3.13f,
                            cap = StrokeCap.Round,
                            join = StrokeJoin.Round
                        )
                    )
                }
            }

            // Vẽ viền đè trên cùng của Trái Đất để che mờ mép lỗi răng cưa
            drawPath(
                paths.earthBorder,
                theme.primaryCyan,
                style = Stroke(width = 3.13f, cap = StrokeCap.Round, join = StrokeJoin.Round)
            )
        }
    }
}