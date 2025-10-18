/**
 * Descripción: Adaptador para el RecyclerView que muestra el historial de puntajes de la sesión.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 16/18/2025
 */
package com.example.colormatchfloo.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.colormatchfloo.databinding.ItemScoreHistoryBinding

// La lista de puntajes ahora se puede pasar como un var para poder actualizarla
class ScoreAdapter(private var scores: List<Int>) : RecyclerView.Adapter<ScoreAdapter.ScoreViewHolder>() {

    /**
     * ViewHolder representa la vista de un solo ítem en nuestra lista.
     * Utiliza ViewBinding para acceder a sus componentes de forma segura.
     */
    inner class ScoreViewHolder(private val binding: ItemScoreHistoryBinding) : RecyclerView.ViewHolder(binding.root) {
        // Esta función "conecta" un dato (el puntaje) con la vista.
        fun bind(score: Int) {
            // La referencia a 'scoreItemTextView' ahora es correcta a través de 'binding'.
            binding.scoreItemTextView.text = "Puntaje: $score"
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ScoreViewHolder {
        // "Inflamos" (creamos) la vista para cada ítem usando la clase de binding generada.
        val binding = ItemScoreHistoryBinding.inflate(LayoutInflater.from(parent.context), parent, false)
        return ScoreViewHolder(binding)
    }

    override fun onBindViewHolder(holder: ScoreViewHolder, position: Int) {
        // Le pasamos el puntaje correspondiente a la posición para que el ViewHolder lo muestre.
        holder.bind(scores[position])
    }

    override fun getItemCount(): Int {
        // Devolvemos el número total de ítems en la lista.
        return scores.size
    }
}