<p align="right">
  <a href="./README.md">🇬🇧 English</a> |
  <a href="./README.ru.md">🇷🇺 Русский</a>
</p>

# EarNotifyTG

## Problem

You're wearing earbuds, phone in your pocket, screen off. A Telegram message arrives — but from whom?
You have to pull out your phone, unlock it, check. Every single time.

## Solution

EarNotifyTG speaks the sender's name directly into your Bluetooth earbuds.
Music ducks down, you hear the name, music comes back.
You never touched your phone.

## Features

- 🔊 Text-to-Speech sender name announcement
- 🎧 Works only with Bluetooth earbuds
- 📱 Only triggers when screen is off
- 🔇 Auto-ducking and resuming music
- ⚡ System service — always on, battery-friendly

## Installation

1. Download APK from [Releases](../../releases)
2. Install
3. Grant notification access (the app will guide you)
4. Tap the **ENABLED** button
5. Connect your Bluetooth earbuds

## Requirements

- Android 8.0+
- Bluetooth earbuds
- Telegram installed

## Tech Stack

- Kotlin
- Jetpack Compose
- NotificationListenerService
- TextToSpeech
- AudioManager

## License

MIT