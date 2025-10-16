package com.example.colormatchfloo.model

/**
 * Descripción: Modela el estado completo de la pantalla del juego en un solo objeto.
 *              Esto simplifica la observación desde el Fragment y garantiza la consistencia de la UI.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 16/10/2025
 *
 * @param score El puntaje actual del jugador.
 * @param timeLeftSeconds El tiempo restante en la partida.
 * @param currentColor El color que el jugador debe adivinar.
 * @param isGameFinished Indica si la partida ha concluido.
 */
data class GameState(
    val score: Int = 0,
    val timeLeftSeconds: Int = 30,
    val currentColor: ColorOption? = null,
    val isGameFinished: Boolean = false
)