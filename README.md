# Dark Mode App

Modern Android sample showcasing a polished light/dark/system theme switcher built fully with **Jetpack Compose**, **Material 3**, **MVVM**, `StateFlow`, and coroutine-friendly **DataStore** persistence.

## Highlights

- **Full Jetpack Compose UI:** No XML screen or DataBinding layer; the main screen is rendered with composable functions.
- **Material 3 visual polish:** Dynamic colors on Android 12+, gradient hero card, rounded surfaces, animated selected-theme feedback, and icon-led option rows.
- **MVVM state management:** `MainViewModel` exposes a single `ThemeUiState` via `StateFlow` and the UI collects it lifecycle-safely.
- **Three theme modes:** Light, Dark, and System Default modes are represented with a type-safe `ThemeMode` enum.
- **Modern persistence:** Theme preference is stored with AndroidX DataStore instead of SharedPreferences.
- **Edge-to-edge experience:** `ComponentActivity` enables edge-to-edge rendering and handles safe drawing insets in Compose.

## Tech Stack

| Area | Choice |
| --- | --- |
| Language | Kotlin |
| UI | Jetpack Compose, Material 3, Material Icons |
| Architecture | MVVM, `ViewModel`, `StateFlow`, lifecycle-aware Compose collection |
| Theme engine | Compose `MaterialTheme`, Material You dynamic color on Android 12+ |
| Persistence | AndroidX DataStore Preferences |
| Minimum SDK | 28 |
| Target SDK | 34 |
| Java target | 17 |

## Project Structure

```text
app/src/main/java/com/halil/ozel/darkmode/
├── MainActivity.kt          # Compose entry point, screen composables, Material theme setup
├── MainViewModel.kt         # MVVM state holder and theme selection actions
├── ThemeMode.kt             # Type-safe Light/Dark/System theme model
└── ThemePreferences.kt      # DataStore-backed theme persistence

app/src/main/res/
├── values/themes.xml        # Minimal NoActionBar light app theme for Compose host activity
├── values-night/themes.xml  # Minimal NoActionBar night app theme for Compose host activity
└── drawable/                # Existing image/screenshot assets
```

## How It Works

1. `MainActivity` starts as a Compose-only `ComponentActivity`, enables edge-to-edge rendering, and calls `setContent`.
2. `MainViewModel` observes `ThemePreferences.themeMode` and maps it into `ThemeUiState`.
3. The Compose UI collects `ThemeUiState` with `collectAsStateWithLifecycle`.
4. The selected `ThemeMode` decides whether `DarkModeTheme` uses light colors, dark colors, or the current system theme.
5. On Android 12 and newer, the app uses Material You dynamic color schemes; older devices use the fallback Material 3 color schemes.
6. When the user taps a theme row, `MainViewModel.selectTheme()` persists the new value with DataStore and the UI updates reactively.

## Getting Started

### Requirements

- Android Studio with Jetpack Compose support
- JDK 17
- Android SDK Platform 34
- Network access to resolve Gradle, Android Gradle Plugin, Compose, Lifecycle, and DataStore dependencies if they are not already cached locally

### Build

```bash
./gradlew assembleDebug
```

### Install on a connected device/emulator

```bash
./gradlew installDebug
```

### Run checks

```bash
./gradlew test
./gradlew lint
```

## Screenshots

Existing screenshot assets are stored in `app/src/main/res/drawable/`. Re-capture them after installing the Compose version so README visuals match the latest UI.

## Customization Ideas

- Move UI strings into `strings.xml` for easier localization and translation workflows.
- Split composables into feature-specific files as the screen grows.
- Add Compose UI tests for theme selection and DataStore persistence.
- Add preview variants for light, dark, and system theme states.

## License

MIT License

Copyright (c) 2023 Halil OZEL

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
