# 📧 Email Expense Tracker

An automated, privacy-conscious Android application that securely connects to your Gmail inbox, parses your digital receipts, and tracks your daily spending. Built with modern Android development practices including Kotlin, Jetpack Compose, Room Database, and MVVM architecture.

## 📑 Table of Contents
- [Features](#-features)
- [Technologies Used](#-technologies-used)
- [Installation](#️-installation)
- [Usage](#-usage)
- [Project Architecture](#-project-architecture)
- [Contributing](#-contributing)
- [License](#-license)

## 🚀 Features
* **Automated Expense Tracking:** Securely scans incoming emails for order confirmations and receipts.
* **Smart Parsing:** Uses Regex and text analysis to automatically extract vendor names, transaction dates, and total amounts.
* **Interactive Dashboard:** View a modern summary chart of total monthly expenses alongside a chronological list of recent transactions.
* **Local Data Persistence:** All parsed expense data is cached locally on your device for offline viewing and privacy.
* **Dynamic Theming:** Fully supports both Light and Dark modes using Material Design 3 guidelines.

## 🛠️ Technologies Used
* **[Kotlin](https://kotlinlang.org/):** Primary programming language.
* **[Jetpack Compose](https://developer.android.com/jetpack/compose):** Modern toolkit for building native UI seamlessly.
* **[Room Database](https://developer.android.com/training/data-storage/room):** SQLite object mapping library for robust local data storage.
* **[Coroutines & Flow](https://kotlinlang.org/docs/coroutines-overview.html):** For asynchronous programming and reactive UI state management.
* **[Compose Navigation](https://developer.android.com/jetpack/compose/navigation):** Handling transitions between Login and Dashboard screens.
* **[ViewModel](https://developer.android.com/topic/libraries/architecture/viewmodel):** Lifecycle-aware data handling.

## ⚙️ Installation

1. **Clone the repository:**
   ```bash
   git clone [https://github.com/yourusername/email-expense-tracker.git](https://github.com/yourusername/email-expense-tracker.git)
   ```
2. **Open the project in Android Studio:**
   * Launch Android Studio and select `File > Open`, then navigate to the cloned directory.
3. **Configure API Keys:**
   * Create or open the `local.properties` file in the root directory.
   * Add your Google OAuth Client ID for Gmail API access:
     ```properties
     GMAIL_CLIENT_ID="your-client-id-here.apps.googleusercontent.com"
     ```
4. **Build and Run:**
   * Sync the project with Gradle files.
   * Select an emulator or connected Android device and click **Run** (`Shift + F10`).

## 💡 Usage

1. **Authenticate:** Launch the app and tap "Sign in with Google" to grant read-only access to your receipts.
2. **Sync Inbox:** Once on the dashboard, tap the **Sync (Refresh) icon** in the top App Bar to fetch and parse recent transactions.
3. **Review:** Check your newly populated dashboard for an updated monthly total and individual expense cards.

## 🏗️ Project Architecture
This application strictly follows the **MVVM (Model-View-ViewModel)** architectural pattern to separate UI components from business logic:
* **UI Layer (`ui/`):** Contains all Jetpack Compose screens (`DashboardScreen`, `LoginScreen`) and reusable components (`ExpenseCard`, `SummaryChart`).
* **Presentation Layer (`viewmodel/`):** Manages UI state and connects the View to the Repository using Kotlin `StateFlow`.
* **Data Layer (`data/`):** Handles the Room Database instance, DAOs, and the central `ExpenseRepo` source of truth.
* **Network/API Layer (`api/`):** Manages interactions with the Gmail API and processes the raw email text using `EmailParser`.

## 🤝 Contributing
Contributions, issues, and feature requests are welcome! 
1. Fork the Project
2. Create your Feature Branch (`git checkout -b feature/AmazingFeature`)
3. Commit your Changes (`git commit -m 'Add some AmazingFeature'`)
4. Push to the Branch (`git push origin feature/AmazingFeature`)
5. Open a Pull Request

## 📄 License
Distributed under the MIT License. See `LICENSE` for more information.
