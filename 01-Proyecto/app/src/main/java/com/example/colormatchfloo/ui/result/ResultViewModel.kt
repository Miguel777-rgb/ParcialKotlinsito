package com.example.colormatchfloo.ui.result

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.colormatchfloo.data.SharedPreferencesManager

/**
 * Descripción: ViewModel para la pantalla de resultados. Gestiona la comparación del puntaje final
 *              con el récord histórico guardado en SharedPreferences.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 16/10/2025
 */
class ResultViewModel(application: Application) : AndroidViewModel(application) {

    // Creamos una instancia de nuestro manager para interactuar con SharedPreferences.
    private val sharedPrefsManager = SharedPreferencesManager(application.applicationContext)

    private val _highScore = MutableLiveData<Int>()
    val highScore: LiveData<Int> get() = _highScore

    /**
     * Procesa el puntaje final de la partida.
     * Carga el puntaje más alto y lo actualiza si se ha superado.
     * @param finalScore El puntaje obtenido en la última partida.
     */
    fun processFinalScore(finalScore: Int) {
        val currentHighScore = sharedPrefsManager.getHighScore()

        if (finalScore > currentHighScore) {
            // ¡Nuevo récord! Lo guardamos y actualizamos el LiveData para que la UI lo muestre.
            sharedPrefsManager.saveHighScore(finalScore)
            _highScore.value = finalScore
        } else {
            // No es un récord, solo mostramos el récord existente.
            _highScore.value = currentHighScore
        }
    }
}