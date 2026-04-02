<div align="center">

# 💬 ChatApp

**A modern, real-time Android chat application built with Kotlin & Supabase**

![Android](https://img.shields.io/badge/Platform-Android-3DDC84?style=flat-square&logo=android&logoColor=white)
![Kotlin](https://img.shields.io/badge/Language-Kotlin-7F52FF?style=flat-square&logo=kotlin&logoColor=white)
![Jetpack Compose](https://img.shields.io/badge/UI-Jetpack%20Compose-4285F4?style=flat-square&logo=jetpackcompose&logoColor=white)
![Supabase](https://img.shields.io/badge/Backend-Supabase-3ECF8E?style=flat-square&logo=supabase&logoColor=white)
![License](https://img.shields.io/badge/License-MIT-yellow?style=flat-square)

</div>

---

## 📖 Overview

ChatApp is a feature-rich, real-time messaging application for Android, built using **Kotlin** and **Jetpack Compose** on the frontend, and **Supabase** as the backend. It offers a seamless and modern chat experience — from one-on-one conversations to group channels — all powered by Supabase's real-time database, authentication, and storage capabilities.

---

## ✨ Features

### 🔴 Real-Time Messaging
- Instant message delivery using **Supabase Realtime** (powered by PostgreSQL's `LISTEN/NOTIFY`)
- Live typing indicators and read receipts
- Messages sync instantly across all connected devices
- Offline message queuing — messages are sent as soon as connectivity is restored

### 🔐 User Authentication
- Secure sign-up and login via **Supabase Auth**
- Supports **email/password** authentication
- OAuth support for social logins (Google, GitHub, etc.)
- JWT-based session management with automatic token refresh
- Persistent login sessions across app restarts

### 👥 Group Chats & Channels
- Create and manage group conversations with multiple participants
- Channel-based messaging for topic-specific discussions
- Admin roles for group management (add/remove members, update group info)
- Group avatars and customizable group names
- Member list with online/offline presence indicators

### 📎 Media & File Sharing
- Send and receive **images, videos, documents**, and other file types
- Media stored securely in **Supabase Storage**
- In-app image previewer with pinch-to-zoom support
- Download shared files directly to the device
- File size and type validation before upload

### 🔔 Push Notifications
- Real-time push notifications powered by **Firebase Cloud Messaging (FCM)**
- Notifications for new messages, group invites, and mentions
- Deep-linking from notifications directly into the relevant chat
- Per-conversation notification mute/unmute controls
- Notification badges on the app icon for unread messages

---

## 🛠️ Tech Stack

| Layer | Technology |
|---|---|
| Language | Kotlin |
| UI Framework | Jetpack Compose |
| Backend | Supabase (PostgreSQL) |
| Authentication | Supabase Auth |
| Real-Time | Supabase Realtime |
| File Storage | Supabase Storage |
| Push Notifications | Firebase Cloud Messaging (FCM) |
| Async / Coroutines | Kotlin Coroutines & Flow |
| Dependency Injection | Hilt |
| Image Loading | Coil |

---

## 📋 Prerequisites

Before getting started, ensure you have the following:

- **Android Studio** Hedgehog (2023.1.1) or later
- **JDK 17** or higher
- **Android SDK** with minimum API level 26 (Android 8.0)
- A **Supabase** project — [create one here](https://supabase.com)
- A **Firebase** project for push notifications — [create one here](https://console.firebase.google.com)

---

## 🚀 Getting Started

### 1. Clone the Repository

```bash
git clone https://github.com/your-username/chatapp.git
cd chatapp
```

### 2. Set Up Supabase

1. Go to your [Supabase Dashboard](https://app.supabase.com) and create a new project.
2. Navigate to **Settings → API** and copy your:
   - **Project URL**
   - **Anon/Public Key**
3. Run the following SQL in the **Supabase SQL Editor** to set up the required tables:

```sql
-- Users profile table
create table profiles (
  id uuid references auth.users on delete cascade primary key,
  username text unique not null,
  avatar_url text,
  updated_at timestamp with time zone default now()
);

-- Channels/groups table
create table channels (
  id uuid default gen_random_uuid() primary key,
  name text not null,
  created_by uuid references profiles(id),
  created_at timestamp with time zone default now()
);

-- Messages table
create table messages (
  id uuid default gen_random_uuid() primary key,
  channel_id uuid references channels(id) on delete cascade,
  sender_id uuid references profiles(id),
  content text,
  media_url text,
  created_at timestamp with time zone default now()
);

-- Enable Row-Level Security
alter table profiles enable row level security;
alter table channels enable row level security;
alter table messages enable row level security;
```

4. Enable **Realtime** for the `messages` table in **Database → Replication**.

### 3. Configure Firebase

1. In the [Firebase Console](https://console.firebase.google.com), create a new Android app with your app's package name.
2. Download the `google-services.json` file.
3. Place it in the `app/` directory of the project.

### 4. Add Credentials to the Project

Create a `local.properties` file in the root of the project (if it doesn't exist) and add your Supabase credentials:

```properties
SUPABASE_URL=https://your-project-ref.supabase.co
SUPABASE_ANON_KEY=your-anon-key-here
```

> ⚠️ **Never commit `local.properties` or any file containing secrets to version control.** Make sure it is listed in your `.gitignore`.

### 5. Build and Run

Open the project in **Android Studio**, let Gradle sync complete, then run the app on an emulator or physical device (API 26+).

---

## 📁 Project Structure

```
chatapp/
├── app/
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/com/yourpackage/chatapp/
│   │   │   │   ├── data/
│   │   │   │   │   ├── model/          # Data models (Message, Channel, User)
│   │   │   │   │   ├── repository/     # Repository layer (Supabase interactions)
│   │   │   │   │   └── remote/         # Supabase client & API services
│   │   │   │   ├── di/                 # Hilt dependency injection modules
│   │   │   │   ├── ui/
│   │   │   │   │   ├── screens/        # Composable screens (Chat, Login, Home)
│   │   │   │   │   ├── components/     # Reusable UI components
│   │   │   │   │   ├── theme/          # Material 3 theme, colors, typography
│   │   │   │   │   └── viewmodel/      # ViewModels for each screen
│   │   │   │   ├── notifications/      # FCM service & notification handling
│   │   │   │   └── MainActivity.kt
│   │   │   ├── res/                    # Resources (drawables, strings, etc.)
│   │   │   └── AndroidManifest.xml
│   ├── build.gradle.kts
│   └── google-services.json            # ← Add this file (not committed to git)
├── local.properties                    # ← Add credentials here (not committed to git)
└── build.gradle.kts
```

---

## 🤝 Contributing

Contributions are welcome! Please follow these steps:

1. **Fork** the repository
2. **Create** a new branch: `git checkout -b feature/your-feature-name`
3. **Commit** your changes: `git commit -m 'Add some feature'`
4. **Push** to the branch: `git push origin feature/your-feature-name`
5. **Open a Pull Request** and describe your changes

Please ensure your code follows the existing code style and that all new features are covered with appropriate tests.

---

## 📄 License

This project is licensed under the **MIT License**. See the [LICENSE](LICENSE) file for details.

---

<div align="center">

Made with ❤️ using Kotlin & Supabase

</div>****
