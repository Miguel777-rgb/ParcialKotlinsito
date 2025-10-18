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

class SharedPreferencesManager(context: Context) {

    // Usamos un companion object para las constantes, así están bien organizadas.
    companion object {
        private const val PREFS_NAME = "colormatchflooPrefs"
        private const val KEY_HIGH_SCORE = "high_score"
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
}