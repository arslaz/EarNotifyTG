package com.example.voice_message_notification

import android.app.Notification
import android.content.Context
import android.media.AudioDeviceInfo
import android.media.AudioManager
import android.os.Handler
import android.os.Looper
import android.os.PowerManager
import android.service.notification.NotificationListenerService
import android.service.notification.StatusBarNotification
import android.speech.tts.TextToSpeech
import java.util.*

class TelegramTtsService : NotificationListenerService(), TextToSpeech.OnInitListener {

    private lateinit var tts: TextToSpeech
    private lateinit var audioManager: AudioManager


    override fun onCreate() {
        super.onCreate()
        tts = TextToSpeech(this, this)
        audioManager = getSystemService(Context.AUDIO_SERVICE) as AudioManager
    }

    override fun onInit(status: Int) {

    }

    override fun onNotificationPosted(sbn: StatusBarNotification?) {
        if (!getSharedPreferences("prefs", MODE_PRIVATE).getBoolean("enabled", false)) return
        if ((sbn?.notification?.flags?.and(Notification.FLAG_GROUP_SUMMARY) ?: 0) != 0) return
        if (sbn?.packageName != "org.telegram.messenger") return
        if (!isScreenOff()) return
        if (!areBluetoothHeadphonesConnected()) return

        val name = sbn.notification?.extras?.getString("android.title") ?: return

        audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_LOWER, 0)
        tts.speak(name, TextToSpeech.QUEUE_FLUSH, null, UUID.randomUUID().toString())

        Handler(Looper.getMainLooper()).postDelayed({
            audioManager.adjustStreamVolume(AudioManager.STREAM_MUSIC, AudioManager.ADJUST_RAISE, 0)
        }, 3000)
    }

    private fun isScreenOff(): Boolean {
        val powerManager = getSystemService(Context.POWER_SERVICE) as PowerManager
        return !powerManager.isInteractive
    }

    private fun areBluetoothHeadphonesConnected(): Boolean {
        return audioManager.getDevices(AudioManager.GET_DEVICES_OUTPUTS)
            .any { it.type == AudioDeviceInfo.TYPE_BLUETOOTH_A2DP || it.type == AudioDeviceInfo.TYPE_BLUETOOTH_SCO }
    }

    override fun onDestroy() {
        tts.shutdown()
        super.onDestroy()
    }
}