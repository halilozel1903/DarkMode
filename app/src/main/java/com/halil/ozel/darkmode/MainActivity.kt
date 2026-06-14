package com.halil.ozel.darkmode

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.activity.viewModels
import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.animateColorAsState
import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.safeDrawing
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AutoAwesome
import androidx.compose.material.icons.rounded.DarkMode
import androidx.compose.material.icons.rounded.Devices
import androidx.compose.material.icons.rounded.LightMode
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle

class MainActivity : ComponentActivity() {

    private val viewModel: MainViewModel by viewModels {
        MainViewModel.Factory(ThemePreferences(applicationContext))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            val uiState by viewModel.uiState.collectAsStateWithLifecycle()
            DarkModeApp(
                uiState = uiState,
                onThemeSelected = viewModel::selectTheme,
            )
        }
    }
}

@Composable
private fun DarkModeApp(
    uiState: ThemeUiState,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    val systemDark = isSystemInDarkTheme()
    val darkTheme = when (uiState.selectedTheme) {
        ThemeMode.Light -> false
        ThemeMode.Dark -> true
        ThemeMode.System -> systemDark
    }

    DarkModeTheme(darkTheme = darkTheme) {
        Scaffold(
            containerColor = MaterialTheme.colorScheme.background,
            modifier = Modifier.fillMaxSize(),
        ) { innerPadding ->
            HomeScreen(
                uiState = uiState,
                onThemeSelected = onThemeSelected,
                modifier = Modifier
                    .padding(innerPadding)
                    .windowInsetsPadding(WindowInsets.safeDrawing),
            )
        }
    }
}

@Composable
private fun HomeScreen(
    uiState: ThemeUiState,
    onThemeSelected: (ThemeMode) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState())
            .padding(20.dp),
        verticalArrangement = Arrangement.spacedBy(18.dp),
    ) {
        HeroCard(selectedTheme = uiState.selectedTheme)
        ThemePicker(
            selectedTheme = uiState.selectedTheme,
            options = uiState.options,
            onThemeSelected = onThemeSelected,
        )
        FeatureGrid()
    }
}

@Composable
private fun HeroCard(selectedTheme: ThemeMode) {
    val colors = MaterialTheme.colorScheme
    Card(
        colors = CardDefaults.cardColors(containerColor = colors.primaryContainer),
        shape = RoundedCornerShape(32.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 0.dp),
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    Brush.linearGradient(
                        listOf(
                            colors.primaryContainer,
                            colors.tertiaryContainer,
                            colors.surfaceContainerHighest,
                        ),
                    ),
                )
                .padding(24.dp),
        ) {
            Column(verticalArrangement = Arrangement.spacedBy(16.dp)) {
                IconBubble(icon = Icons.Rounded.AutoAwesome)
                Text(
                    text = "Dark Mode deneyimini yeniden tasarladık",
                    style = MaterialTheme.typography.headlineMedium,
                    color = colors.onPrimaryContainer,
                    fontWeight = FontWeight.Black,
                )
                Text(
                    text = "Tamamen Jetpack Compose, MVVM ve DataStore ile akıcı, sağlam ve modern bir tema deneyimi.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = colors.onPrimaryContainer.copy(alpha = 0.78f),
                )
                AnimatedContent(
                    targetState = selectedTheme.label,
                    label = "selected-theme-label",
                ) { label ->
                    Text(
                        text = "Aktif tema: $label",
                        style = MaterialTheme.typography.titleMedium,
                        color = colors.onPrimaryContainer,
                        fontWeight = FontWeight.Bold,
                    )
                }
            }
        }
    }
}

@Composable
private fun ThemePicker(
    selectedTheme: ThemeMode,
    options: List<ThemeMode>,
    onThemeSelected: (ThemeMode) -> Unit,
) {
    Card(
        colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainer),
        shape = RoundedCornerShape(28.dp),
    ) {
        Column(
            modifier = Modifier.padding(18.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            Text(
                text = "Tema tercihi",
                style = MaterialTheme.typography.titleLarge,
                fontWeight = FontWeight.Bold,
            )
            options.forEach { option ->
                ThemeOptionRow(
                    themeMode = option,
                    selected = selectedTheme == option,
                    onClick = { onThemeSelected(option) },
                )
            }
        }
    }
}

@Composable
private fun ThemeOptionRow(
    themeMode: ThemeMode,
    selected: Boolean,
    onClick: () -> Unit,
) {
    val targetColor = if (selected) MaterialTheme.colorScheme.primaryContainer else MaterialTheme.colorScheme.surface
    val containerColor by animateColorAsState(
        targetValue = targetColor,
        animationSpec = spring(stiffness = Spring.StiffnessLow),
        label = "theme-option-color",
    )

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(22.dp))
            .background(containerColor)
            .clickable(onClick = onClick)
            .padding(14.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(14.dp),
    ) {
        IconBubble(icon = themeMode.icon, compact = true)
        Column(modifier = Modifier.weight(1f)) {
            Text(text = themeMode.label, fontWeight = FontWeight.Bold)
            Text(
                text = themeMode.description,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
        RadioButton(selected = selected, onClick = onClick)
    }
}

@Composable
private fun FeatureGrid() {
    val features = listOf(
        Feature("Compose UI", "Deklaratif ekranlar, animasyonlu durum geçişleri ve Material 3 bileşenleri."),
        Feature("MVVM", "Ekran durumu ViewModel içinde tek kaynaktan yönetilir."),
        Feature("DataStore", "Tema tercihi güvenli ve coroutine uyumlu şekilde saklanır."),
    )
    Column(verticalArrangement = Arrangement.spacedBy(12.dp)) {
        features.forEach { feature ->
            Card(
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceContainerHigh),
                shape = RoundedCornerShape(24.dp),
            ) {
                Column(modifier = Modifier.padding(18.dp), verticalArrangement = Arrangement.spacedBy(6.dp)) {
                    Text(text = feature.title, style = MaterialTheme.typography.titleMedium, fontWeight = FontWeight.Bold)
                    Text(text = feature.body, color = MaterialTheme.colorScheme.onSurfaceVariant)
                }
            }
        }
    }
}

@Composable
private fun IconBubble(icon: ImageVector, compact: Boolean = false) {
    Surface(
        modifier = Modifier.size(if (compact) 44.dp else 58.dp),
        shape = CircleShape,
        color = MaterialTheme.colorScheme.primary.copy(alpha = 0.14f),
    ) {
        Icon(
            imageVector = icon,
            contentDescription = null,
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.padding(if (compact) 10.dp else 14.dp),
        )
    }
}

@Composable
private fun DarkModeTheme(
    darkTheme: Boolean,
    content: @Composable () -> Unit,
) {
    val context = LocalContext.current
    val colorScheme = if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.S) {
        if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
    } else if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFFB8C7FF),
            secondary = Color(0xFFFFC2D1),
            tertiary = Color(0xFFAAD7C8),
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF3655C8),
            secondary = Color(0xFF8B3F5E),
            tertiary = Color(0xFF236A5B),
        )
    }

    MaterialTheme(colorScheme = colorScheme, content = content)
}

private val ThemeMode.label: String
    get() = when (this) {
        ThemeMode.Light -> "Açık tema"
        ThemeMode.Dark -> "Koyu tema"
        ThemeMode.System -> "Sistem varsayılanı"
    }

private val ThemeMode.description: String
    get() = when (this) {
        ThemeMode.Light -> "Net ve parlak bir arayüz kullan."
        ThemeMode.Dark -> "Düşük ışıkta konforlu koyu görünüm."
        ThemeMode.System -> "Cihazının tema ayarını otomatik takip et."
    }

private val ThemeMode.icon: ImageVector
    get() = when (this) {
        ThemeMode.Light -> Icons.Rounded.LightMode
        ThemeMode.Dark -> Icons.Rounded.DarkMode
        ThemeMode.System -> Icons.Rounded.Devices
    }

private data class Feature(val title: String, val body: String)

@Preview(showBackground = true)
@Composable
private fun DarkModePreview() {
    DarkModeApp(uiState = ThemeUiState(), onThemeSelected = {})
}
