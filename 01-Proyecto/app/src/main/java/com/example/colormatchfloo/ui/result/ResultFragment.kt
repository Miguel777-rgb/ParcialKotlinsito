/**
 * Descripción: ViewModel que maneja la lógica del juego usando un único objeto GameState.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
package com.example.colormatchfloo.ui.result

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import androidx.recyclerview.widget.LinearLayoutManager
import com.example.colormatchfloo.R
import com.example.colormatchfloo.adapters.ScoreAdapter
import com.example.colormatchfloo.databinding.FragmentResultBinding
import com.example.colormatchfloo.ui.SharedViewModel
import com.example.colormatchfloo.data.SharedPreferencesManager
import com.example.colormatchfloo.ui.MainActivity
import com.example.colormatchfloo.utils.SoundManager

class ResultFragment : Fragment() {

    private var _binding: FragmentResultBinding? = null
    private val binding get() = _binding!!

    // ViewModel para la lógica de esta pantalla (récords).
    private val resultViewModel: ResultViewModel by viewModels()
    // ViewModel compartido. Usamos activityViewModels() para obtener la instancia ligada a la Activity.
    private val sharedViewModel: SharedViewModel by activityViewModels()

    // Delegado de Safe Args para obtener los argumentos de navegación de forma segura.
    private val args: ResultFragmentArgs by navArgs()

    private lateinit var prefsManager: SharedPreferencesManager
    private lateinit var soundManager: SoundManager

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentResultBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        prefsManager = SharedPreferencesManager(requireContext())
        soundManager = (activity as MainActivity).soundManager

        // 1. Obtenemos el puntaje final que nos llega desde GameFragment.
        val finalScore = args.finalScore
        binding.finalScoreTextView.text = finalScore.toString()

        // 2. Le pedimos a los ViewModels que procesen este puntaje.
        sharedViewModel.addScoreToSessionHistory(finalScore)
        resultViewModel.processFinalScore(finalScore)

        setupRecyclerView()
        setupObservers()
        setupListeners()
    }

    private fun setupRecyclerView() {
        // Preparamos nuestro RecyclerView con un layout vertical.
        binding.scoresRecyclerView.layoutManager = LinearLayoutManager(requireContext())
    }

    private fun setupObservers() {
        // Observamos el LiveData del récord. Cuando cambie, actualizamos el TextView.
        resultViewModel.highScore.observe(viewLifecycleOwner) { highScore ->
            binding.highScoreTextView.text = getString(R.string.high_score_label) + ": $highScore"
        }

        // Observamos la lista de puntajes de la sesión. Cuando se añada uno nuevo,
        // creamos y asignamos un nuevo adaptador al RecyclerView para que se refresque.
        sharedViewModel.sessionScores.observe(viewLifecycleOwner) { scores ->
            binding.scoresRecyclerView.adapter = ScoreAdapter(scores)
        }
    }

    private fun setupListeners() {
        binding.playAgainButton.setOnClickListener {
            // Usamos la acción del nav_graph para volver a la pantalla de juego.
            // La propiedad popUpTo definida en el XML se encargará de limpiar la pila de navegación.
            findNavController().navigate(R.id.action_resultFragment_to_gameFragment)
        }
        binding.homeButton.setOnClickListener {
            // Usamos la nueva acción que definimos en el nav_graph.
            findNavController().navigate(R.id.action_resultFragment_to_welcomeFragment)
        }
    }

    override fun onResume() {
        super.onResume()
        // Cuando la pantalla de resultados se muestra, reanuda la música.
        soundManager.startMusic(prefsManager.getMusicVolume())
    }

    override fun onPause() {
        super.onPause()
        // Cuando sales de la pantalla de resultados (para volver a jugar o ir al inicio),
        // la música se pausa, lista para que el siguiente fragmento tome el control.
        soundManager.pauseMusic()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}