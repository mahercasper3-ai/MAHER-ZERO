# MAHER ZERO

A personal Android launcher-style interface with a black, purple and red identity.

## Build an APK with GitHub Actions
1. Create a GitHub repository named `MAHER-ZERO`.
2. Upload the contents of this folder (not the outer folder itself).
3. Open the repository's **Actions** tab.
4. Select **Build MAHER ZERO APK** and press **Run workflow** (or push to main).
5. Open the completed workflow run, find **Artifacts**, and download `MAHER-ZERO-APK`.
6. Extract the downloaded artifact ZIP; install `app-debug.apk` on your Android phone.

## Build locally
Install Android SDK and JDK 17, then run:
`gradle assembleDebug`

APK output:
`app/build/outputs/apk/debug/app-debug.apk`

## Notes
- This is an installable Android app that can also be selected as the default Home app.
- It lists launchable apps and opens Android's real Wi-Fi, Bluetooth, display and sound settings.
- Android controls permissions and system functions; this app does not bypass Android security.
