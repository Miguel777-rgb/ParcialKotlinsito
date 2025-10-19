/**
 * Descripción: Fragmento que muestra la interfaz del juego y gestiona la interacción del usuario.
 *              Actualizado para observar el objeto GameState.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 19/10/2025
 */
package com.example.colormatchfloo.ui.game

import android.content.res.ColorStateList
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.example.colormatchfloo.R
import com.example.colormatchfloo.databinding.FragmentGameBinding
import com.example.colormatchfloo.utils.Constants
import com.example.colormatchfloo.utils.SoundManager
import com.example.colormatchfloo.data.SharedPreferencesManager
import com.example.colormatchfloo.ui.MainActivity
import android.util.Log

class GameFragment : Fragment() {

    private var _binding: FragmentGameBinding? = null
    private val binding get() = _binding!!

    private val viewModel: GameViewModel by viewModels()

    private lateinit var soundManager: SoundManager
    private lateinit var prefsManager: SharedPreferencesManager
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentGameBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        Log.d("MiApp_Lifecycle", "GameFragment: onViewCreated - La pantalla de juego se ha creado.")
        prefsManager = SharedPreferencesManager(requireContext())
        soundManager = (activity as MainActivity).soundManager
        setupButtonListeners()
        setupObservers()
    }

    // Gestionamos la música de fondo con el ciclo de vida del fragmento.
    override fun onResume() {
        super.onResume()
        // Le pasamos el volumen guardado al iniciar la música.
        soundManager.startMusic(prefsManager.getMusicVolume())
    }

    override fun onPause() {
        super.onPause()
        soundManager.pauseMusic()
    }
    private fun setupButtonListeners() {
        val buttonColorMap = mapOf(
            binding.redButton to Constants.GAME_COLORS[0],
            binding.greenButton to Constants.GAME_COLORS[1],
            binding.blueButton to Constants.GAME_COLORS[2],
            binding.yellowButton to Constants.GAME_COLORS[3],
            binding.orangeButton to Constants.GAME_COLORS[4],
            binding.purpleButton to Constants.GAME_COLORS[5]
        )

        buttonColorMap.forEach { (button, colorOption) ->
            button.backgroundTintList = ColorStateList.valueOf(
                ContextCompat.getColor(requireContext(), colorOption.colorRes)
            )
            button.setOnClickListener { view ->
                // Animación de "Pop" al presionar el botón.
                view.animate()
                    .scaleX(0.9f)
                    .scaleY(0.9f)
                    .setDuration(100)
                    .withEndAction {
                        view.animate()
                            .scaleX(1f)
                            .scaleY(1f)
                            .setDuration(100)
                            .start()
                    }.start()
                viewModel.onColorSelected(colorOption)
            }
        }
    }

    private fun setupObservers() {
        viewModel.gameState.observe(viewLifecycleOwner) { state ->
            // 1. Actualizar Puntaje
            binding.scoreTextView.text = getString(R.string.score_label, state.score)

            // 2. Actualizar Tiempo
            binding.timerTextView.text = getString(R.string.time_label, state.timeLeftSeconds)

            // 3. Actualizar Color Central (si no es nulo)
            state.currentColor?.let { colorOption ->
                val newColor = ContextCompat.getColor(requireContext(), colorOption.colorRes)
                if (binding.colorDisplayView.backgroundTintList?.defaultColor != newColor) {
                    // Animación de Fade Out
                    binding.colorDisplayView.animate()
                        .alpha(0f)
                        .setDuration(150) // Duración corta para la desaparición
                        .withEndAction {
                            // Cuando termina, cambiamos el color y hacemos Fade In
                            binding.colorDisplayView.backgroundTintList = ColorStateList.valueOf(newColor)
                            binding.colorDisplayView.animate()
                                .alpha(1f)
                                .setDuration(150) // Duración corta para la aparición
                                .start()
                        }.start()
                }
            }

            // 4. Comprobar si el juego terminó para navegar
            if (state.isGameFinished) {
                navigateToResult(state.score)
            }
        }
        viewModel.soundEvent.observe(viewLifecycleOwner) { sound ->
            sound?.let {
                // Leemos el volumen MÁS RECIENTE de las preferencias.
                val currentEffectsVolume = prefsManager.getEffectsVolume()
                soundManager.playSound(it, currentEffectsVolume)
                viewModel.onSoundPlayed()
            }
        }
    }

    private fun navigateToResult(finalScore: Int) {
        // Usamos GameFragmentDirections generado por Safe Args.
        val action = GameFragmentDirections.actionGameFragmentToResultFragment(finalScore)
        findNavController().navigate(action)
    }

}