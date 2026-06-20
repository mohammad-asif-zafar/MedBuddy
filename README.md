# MedBuddy - Smart Glucose Care

MedBuddy is a robust **Kotlin Multiplatform (KMP)** mobile application designed to help users manage diabetes and track their blood glucose levels with precision and ease. Built with a focus on data synchronization and intuitive visualization, MedBuddy provides a professional suite of tools for health monitoring.

## 🚀 Key Features

-   **Intelligent Dashboard:** Real-time overview of latest glucose readings, daily averages, and estimated HbA1c.
-   **Advanced Analytics:** Dynamic reports covering 7, 30, and 90-day trends with cubic bezier charts and "Time In Range" donut visualizations.
-   **Automated Health Insights:** Context-aware feedback based on glucose patterns (e.g., lunch spikes, improved stability).
-   **Smart Notifications:** Dynamic alerts for critically high/low levels and reminders to log missed readings (FCM integrated).
-   **Doctor Management:** Store primary care physician details and track upcoming appointments.
-   **Personalized Experience:** Full support for **Light and Dark themes**, persistent user profiles, and secure Google Sign-In.

## 🛠 Tech Stack

-   **Framework:** Kotlin Multiplatform (Android & iOS)
-   **UI:** Compose Multiplatform for shared UI components.
-   **Backend:** 
    -   **Firebase Authentication:** Secure user sign-in and account management.
    -   **Firebase Firestore:** Real-time cloud database with "Latest Wins" merge strategy for multi-device sync.
    -   **Firebase Cloud Messaging (FCM):** Server-side push notifications for health alerts.
-   **Libraries:**
    -   **Ktor:** Cross-platform network communication.
    -   **Coil 3:** Asynchronous image loading for profile pictures.
    -   **Kotlinx-datetime:** Unified multiplatform time handling.

## 📂 Project Structure

-   `composeApp/src/commonMain`: Shared business logic, domain models, and UI components.
-   `composeApp/src/androidMain`: Android-specific implementations (FCM service, Local storage).
-   `composeApp/src/iosMain`: iOS-specific implementations and mock repositories for testing.
-   `iosApp`: Entry point for the iOS application and SwiftUI wrapper.

## 🔧 Getting Started

### Android
To build the debug version:
```bash
./gradlew :composeApp:assembleDebug
```

### iOS
1. Open `iosApp/iosApp.xcodeproj` in Xcode.
2. Ensure Firebase is configured for the iOS bundle ID.
3. Run on a physical device for full FCM notification support.

## 🔒 Privacy
Your health data security is our priority. Please review our [Privacy Policy](privacy-policy.html) for more information on how we handle and protect your sensitive information.

---
*Built with ❤️ using Kotlin Multiplatform.*
