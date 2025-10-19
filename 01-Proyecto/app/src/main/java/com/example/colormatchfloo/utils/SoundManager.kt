/**
 * Descripción: Gestiona la reproducción de efectos de sonido y música de fondo.
 *              Utiliza SoundPool para efectos cortos y MediaPlayer para música.
 * Autor: Miguel Flores
 * Fecha de creación: 18/10/2025
 * Fecha de última modificación: 18/10/2025
 */
package com.example.colormatchfloo.utils

import android.content.Context
import android.media.AudioAttributes
import android.media.MediaPlayer
import android.media.SoundPool
import com.example.colormatchfloo.R



// Enum para identificar los sonidos de forma segura y legible.
enum class GameSound {
    CORRECT,
    ERROR
}

class SoundManager(
    private val context: Context,
) {

    private val soundPool: SoundPool
    private var correctSoundId: Int = 0
    private var errorSoundId: Int = 0
    private var musicPlayer: MediaPlayer? = null


    init {

        // Configuramos el SoundPool para el audio del juego.
        val audioAttributes = AudioAttributes.Builder()
            .setUsage(AudioAttributes.USAGE_GAME)
            .setContentType(AudioAttributes.CONTENT_TYPE_SONIFICATION)
            .build()

        soundPool = SoundPool.Builder()
            .setMaxStreams(5) // Podemos reproducir hasta 5 sonidos a la vez.
            .setAudioAttributes(audioAttributes)
            .build()

        // Cargamos los sonidos en memoria.
        correctSoundId = soundPool.load(context, R.raw.correct_answer, 1)
        errorSoundId = soundPool.load(context, R.raw.wrong_answer, 1)
    }

    /** Reproduce un efecto de sonido corto. */
    fun playSound(sound: GameSound, volume: Float) {
        when (sound) {
            GameSound.CORRECT -> soundPool.play(correctSoundId, volume, volume, 1, 0, 1.0f)
            GameSound.ERROR -> soundPool.play(errorSoundId, volume, volume, 1, 0, 1.0f)
        }
    }

    fun startMusic(volume: Float) {
        if (musicPlayer == null) {
            musicPlayer = MediaPlayer.create(context, R.raw.background_sound).apply {
                isLooping = true
            }
        }
        musicPlayer?.setVolume(volume, volume)
        musicPlayer?.start()
    }

    // La función para ajustar el volumen en tiempo real ahora es más simple.
    fun setMusicVolume(volume: Float) {
        musicPlayer?.setVolume(volume, volume)
    }

    /** Pausa la música de fondo. */
    fun pauseMusic() {
        musicPlayer?.pause()
    }

    /** Libera todos los recursos de audio para evitar fugas de memoria. */
    fun release() {
        soundPool.release()
        musicPlayer?.release()
        musicPlayer = null
    }

}