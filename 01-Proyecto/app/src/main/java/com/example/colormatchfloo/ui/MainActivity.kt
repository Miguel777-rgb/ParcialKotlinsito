/**
 * Descripción: Actividad principal que aloja la navegación de la aplicación.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 19/10/2025
 */
package com.example.colormatchfloo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colormatchfloo.R
import com.example.colormatchfloo.utils.SoundManager

class MainActivity : AppCompatActivity() {
    // Hacemos que sea accesible para los fragments.
    lateinit var soundManager: SoundManager
        private set // Los fragments pueden leerla, pero solo la Activity puede cambiarla.

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // Creamos la única instancia aquí.
        soundManager = SoundManager(applicationContext)
    }

    override fun onDestroy() {
        super.onDestroy()
        // Cuando la app se cierra del todo, liberamos los recursos.
        soundManager.release()
    }
}