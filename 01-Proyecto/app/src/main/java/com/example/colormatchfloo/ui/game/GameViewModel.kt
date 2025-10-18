/**
 * Descripción: ViewModel que maneja la lógica del juego usando un único objeto GameState.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
package com.example.colormatchfloo.ui.game

import android.os.CountDownTimer
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.colormatchfloo.model.ColorOption
import com.example.colormatchfloo.model.GameState
import com.example.colormatchfloo.utils.Constants
import com.example.colormatchfloo.utils.GameSound
import android.util.Log

class GameViewModel : ViewModel() {

    // Ahora solo tenemos un LiveData que contiene todo el estado de la UI.
    private val _gameState = MutableLiveData<GameState>()
    val gameState: LiveData<GameState> get() = _gameState

    private lateinit var timer: CountDownTimer

    init {
        Log.d("MiApp_Lifecycle", "GameViewModel: init - El ViewModel del juego se está inicializando.")
        startGame()
    }

    fun startGame() {
        // Inicializamos el estado y arrancamos el timer.
        _gameState.value = GameState(currentColor = generateRandomColor())
        startTimer()
    }

    private fun startTimer() {
        timer = object : CountDownTimer(Constants.GAME_DURATION_MS, Constants.COUNTDOWN_INTERVAL_MS) {
            override fun onTick(millisUntilFinished: Long) {
                val secondsLeft = (millisUntilFinished / Constants.COUNTDOWN_INTERVAL_MS).toInt()
                // Usamos copy() para crear un nuevo estado a partir del anterior, cambiando solo el tiempo.
                _gameState.value = _gameState.value?.copy(timeLeftSeconds = secondsLeft)
            }

            override fun onFinish() {
                // El juego termina. Actualizamos el estado para reflejarlo.
                _gameState.value = _gameState.value?.copy(timeLeftSeconds = 0, isGameFinished = true)
            }
        }.start()
    }

    private val _soundEvent = MutableLiveData<GameSound?>()
    val soundEvent: LiveData<GameSound?> get() = _soundEvent

    fun onColorSelected(selectedColor: ColorOption) {
        val currentState = _gameState.value ?: return
        if (currentState.isGameFinished) return

        val newScore: Int
        if (selectedColor == currentState.currentColor) {
            newScore = currentState.score + 1
            _soundEvent.value = GameSound.CORRECT // <-- Emitir evento de acierto
        } else {
            newScore = (currentState.score - 1).coerceAtLeast(0)
            _soundEvent.value = GameSound.ERROR   // <-- Emitir evento de error
        }

        _gameState.value = currentState.copy(
            score = newScore,
            currentColor = generateRandomColor(currentState.currentColor)
        )
    }

    /** Llama a esta función desde el Fragment después de reproducir el sonido. */
    fun onSoundPlayed() {
        _soundEvent.value = null
    }

    /**
     * Selecciona un nuevo color aleatorio, asegurando que no se repita el anterior.
     */
    private fun generateRandomColor(excludeColor: ColorOption? = null): ColorOption {
        var newColor: ColorOption
        do {
            newColor = Constants.GAME_COLORS.random()
        } while (newColor == excludeColor)
        return newColor
    }

    override fun onCleared() {
        super.onCleared()
        timer.cancel()
    }

}