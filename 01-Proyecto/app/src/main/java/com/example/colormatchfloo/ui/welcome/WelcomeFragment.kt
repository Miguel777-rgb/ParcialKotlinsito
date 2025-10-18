package com.example.colormatchfloo.ui.welcome

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AlertDialog
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.example.colormatchfloo.R
import com.example.colormatchfloo.databinding.FragmentWelcomeBinding
/**
 * Descripción: Clase que muestra la pantalla de bienvenida y gestiona la navegación. Previo a la partida.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentWelcomeBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MiApp_Lifecycle", "WelcomeFragment: onViewCreated - La pantalla de bienvenida está visible.")

        // ===== PASO 1: LLAMAMOS AL DIÁLOGO AQUÍ =====
        // Mostramos las reglas del juego automáticamente al entrar a la pantalla.
        showGameRulesDialog()

        // El resto de los listeners siguen funcionando como antes.
        binding.startGameButton.setOnClickListener {
            Log.d("MiApp_Lifecycle", "WelcomeFragment: Botón 'Iniciar Juego' presionado. Navegando...")
            findNavController().navigate(R.id.action_welcomeFragment_to_gameFragment)
        }

        binding.rulesButton.setOnClickListener {
            // El botón de reglas sigue funcionando por si el usuario quiere volver a verlas.
            showGameRulesDialog()
        }
    }

    private fun showGameRulesDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle(R.string.game_rules_title)
            .setMessage(R.string.game_rules_message)
            .setPositiveButton(R.string.ok_button) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false) // Opcional: Evita que el diálogo se cierre al tocar fuera.
            .create()
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}