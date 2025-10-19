/**
 * Descripción: Clase helper para gestionar la persistencia de datos con SharedPreferences.
 *              Abstrae la lectura y escritura del puntaje más alto.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 16/10/2025
 */
package com.example.colormatchfloo.data

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit
class SharedPreferencesManager(context: Context) {

    // Usamos un companion object para las constantes, así están bien organizadas.
    companion object {
        private const val PREFS_NAME = "colormatchflooPrefs"
        private const val KEY_HIGH_SCORE = "high_score"
        private const val KEY_MUSIC_VOLUME = "music_volume"
        private const val KEY_EFFECTS_VOLUME = "effects_volume"
    }

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_NAME, Context.MODE_PRIVATE)

    /**
     * Guarda un nuevo puntaje más alto en las preferencias.
     * @param score El puntaje a guardar.
     */
    fun saveHighScore(score: Int) {
        // Usamos apply() en lugar de commit() porque es asíncrono y no bloquea el hilo principal.
        prefs.edit().putInt(KEY_HIGH_SCORE, score).apply()
    }

    /**
     * Obtiene el puntaje más alto guardado.
     * @return El puntaje más alto, o 0 si no se ha guardado ninguno.
     */
    fun getHighScore(): Int {
        return prefs.getInt(KEY_HIGH_SCORE, 0)
    }

    /** Guarda el nivel de volumen de la música (0.0f a 1.0f). */
    fun saveMusicVolume(volume: Float) {
        prefs.edit { putFloat(KEY_MUSIC_VOLUME, volume) }
    }

    /** Obtiene el nivel de volumen de la música, con un valor por defecto. */
    fun getMusicVolume(): Float {
        // Por defecto, la música estará a un 50% de volumen.
        return prefs.getFloat(KEY_MUSIC_VOLUME, 0.8f)
    }

    /** Guarda el nivel de volumen de los efectos (0.0f a 1.0f). */
    fun saveEffectsVolume(volume: Float) {
        prefs.edit { putFloat(KEY_EFFECTS_VOLUME, volume) }
    }

    /** Obtiene el nivel de volumen de los efectos, con un valor por defecto. */
    fun getEffectsVolume(): Float {
        // Por defecto, los efectos estarán a un 80% de volumen.
        return prefs.getFloat(KEY_EFFECTS_VOLUME, 0.8f)
    }
}