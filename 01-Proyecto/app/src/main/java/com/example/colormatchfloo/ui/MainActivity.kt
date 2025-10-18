/**
 * Descripción: Actividad principal que aloja la navegación de la aplicación.
 * Autor: Miguel Flores
 * Fecha de creación: 16/10/2025
 * Fecha de última modificación: 18/10/2025
 */
package com.example.colormatchfloo.ui

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.example.colormatchfloo.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        // La navegación es manejada por el NavHostFragment definido en el XML.
        // No se necesita código adicional aquí para una configuración básica.
    }
}