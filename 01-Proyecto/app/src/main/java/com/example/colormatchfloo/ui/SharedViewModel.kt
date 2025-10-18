/**
 * Descripción: ViewModel compartido a nivel de actividad para mantener el historial de puntajes de la sesión.
 *              Sobrevive a la navegación entre fragmentos.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
package com.example.colormatchfloo.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class SharedViewModel : ViewModel() {

    private val _sessionScores = MutableLiveData<List<Int>>(emptyList())
    val sessionScores: LiveData<List<Int>> get() = _sessionScores

    /**
     * Añade un nuevo puntaje al historial de la sesión actual.
     * Los puntajes se añaden al principio de la lista para mostrar el más reciente primero.
     */
    fun addScoreToSessionHistory(score: Int) {
        val currentList = _sessionScores.value ?: emptyList()
        // Creamos una nueva lista con el nuevo puntaje al inicio.
        _sessionScores.value = listOf(score) + currentList
    }
}