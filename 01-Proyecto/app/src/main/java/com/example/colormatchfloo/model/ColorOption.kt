/**
 * Descripción: Modelo de datos que representa una opción de color en el juego.
 *
 * @param "name" Nombre descriptivo del color (ej. "Rojo").
 * @param "colorRes" ID del recurso de color (ej. R.color.game_red).
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 16/18/2025
 */
package com.example.colormatchfloo.model

import androidx.annotation.ColorRes

data class ColorOption(
    val name: String,
    @ColorRes val colorRes: Int
)