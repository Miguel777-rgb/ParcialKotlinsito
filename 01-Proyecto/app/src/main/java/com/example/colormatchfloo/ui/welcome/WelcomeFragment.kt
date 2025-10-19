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
import com.example.colormatchfloo.utils.SoundManager
import com.example.colormatchfloo.data.SharedPreferencesManager
import com.example.colormatchfloo.utils.GameSound
import android.widget.SeekBar
import com.example.colormatchfloo.ui.MainActivity

/**
 * Descripción: Clase que muestra la pantalla de bienvenida y gestiona la navegación. Previo a la partida.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
class WelcomeFragment : Fragment() {

    private var _binding: FragmentWelcomeBinding? = null
    private val binding get() = _binding!!
    private lateinit var prefsManager: SharedPreferencesManager
    private lateinit var soundManager: SoundManager
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
        prefsManager = SharedPreferencesManager(requireContext())
        soundManager = (activity as MainActivity).soundManager

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
        setupVolumeControls()
    }

    override fun onResume() {
        super.onResume()
        // Inicia la música de fondo cuando la pantalla se vuelve visible.
        // Lee el volumen MÁS RECIENTE de las preferencias para empezar con el nivel correcto.
        soundManager.startMusic(prefsManager.getMusicVolume())
    }

    override fun onPause() {
        super.onPause()
        // Pausa la música cuando la pantalla deja de ser visible (al ir al juego, por ejemplo).
        // Esto es VITAL para que no suenen dos pistas de música a la vez.
        soundManager.pauseMusic()
    }

    private fun setupVolumeControls() {
        // 1. Cargar valores guardados y actualizar la UI (esto estaba bien)
        binding.musicVolumeSlider.progress = (prefsManager.getMusicVolume() * 100).toInt()
        binding.effectsVolumeSlider.progress = (prefsManager.getEffectsVolume() * 100).toInt()

        // 2. Listener para el SLIDER DE MÚSICA
        binding.musicVolumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val volume = progress / 100f
                    prefsManager.saveMusicVolume(volume)
                    soundManager.setMusicVolume(volume)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {}
        })

        // 3. Listener para el SLIDER DE EFECTOS DE SONIDO
        binding.effectsVolumeSlider.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {
                if (fromUser) {
                    val volume = progress / 100f
                    prefsManager.saveEffectsVolume(volume)
                }
            }
            override fun onStartTrackingTouch(seekBar: SeekBar?) {}
            override fun onStopTrackingTouch(seekBar: SeekBar?) {
                // Leemos el volumen MÁS RECIENTE de las preferencias.
                val currentEffectsVolume = prefsManager.getEffectsVolume()
                soundManager.playSound(GameSound.CORRECT, currentEffectsVolume)
            }
        })
    }
    private fun showGameRulesDialog() {
        AlertDialog.Builder(requireContext(), R.style.CustomAlertDialog)
            .setTitle(R.string.game_rules_title)
            .setMessage(R.string.game_rules_message)
            .setPositiveButton(R.string.ok_button) { dialog, _ ->
                dialog.dismiss()
            }
            .setCancelable(false)
            .create()
            .show()
    }
}