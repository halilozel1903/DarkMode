# Dark Mode App

Modern, lightweight Android sample that demonstrates a polished light/dark/system theme switcher with persistent user preferences.

## Highlights

- **Modern Material UI:** Rounded cards, a gradient hero area, clear hierarchy and Material buttons.
- **Three theme modes:** Light, Dark and System Default via `AppCompatDelegate` DayNight modes.
- **Persistent settings:** The selected mode is saved with AndroidX Preference/SharedPreferences and restored at launch.
- **Accessible copy:** Turkish UI strings with clear labels and status feedback for the active theme.
- **Night resources:** Dedicated `values-night` color resources for a more consistent dark-mode palette.

## Tech Stack

| Area | Choice |
| --- | --- |
| Language | Kotlin |
| UI | XML layouts, Data Binding, Material Components, Constraint/Layout widgets |
| Theme engine | AppCompat DayNight |
| Persistence | AndroidX Preference KTX |
| Minimum SDK | 28 |
| Target SDK | 34 |

## Project Structure

```text
app/src/main/java/com/halil/ozel/darkmode/
├── MainActivity.kt      # Theme dialog, mode application and UI status text
├── MyPreferences.kt     # Persisted dark-mode preference wrapper
└── Numbers.kt           # Small constants used for mode indexes

app/src/main/res/
├── layout/activity_main.xml       # Main modernized screen
├── values/                        # Light theme strings, colors, dimensions and styles
├── values-night/                  # Dark theme color/style overrides
└── drawable/hero_gradient.xml     # Hero card background
```

## How It Works

1. `MainActivity` reads the saved integer theme preference on startup.
2. The integer is mapped to the corresponding `AppCompatDelegate` night mode:
   - `0` → `MODE_NIGHT_NO`
   - `1` → `MODE_NIGHT_YES`
   - `2` → `MODE_NIGHT_FOLLOW_SYSTEM`
3. The choice is applied immediately and saved for future launches.
4. The current theme label is shown on the home screen so users always know which mode is active.

## Getting Started

### Requirements

- Android Studio with Android Gradle Plugin support
- JDK 17
- Android SDK Platform 34

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

Existing screenshots are stored in `app/src/main/res/drawable/`. Re-capture them after installing the app if you want the README images to reflect the refreshed UI.

## Customization Ideas

- Add Material You dynamic colors for Android 12+.
- Replace the dialog with a bottom sheet for an even more immersive picker.
- Add UI tests that verify the selected theme survives process recreation.
- Localize strings into additional languages under `res/values-*/strings.xml`.

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
