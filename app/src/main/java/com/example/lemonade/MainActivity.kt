/*
 * Copyright (C) 2021 The Android Open Source Project.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.lemonade

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.ImageView
import android.widget.TextView
import com.google.android.material.snackbar.Snackbar

class MainActivity : AppCompatActivity() {

    /**
     * DO NOT ALTER ANY VARIABLE OR VALUE NAMES OR THEIR INITIAL VALUES.
     *
     * Anything labeled var instead of val is expected to be changed in the functions but DO NOT
     * alter their initial values declared here, this could cause the app to not function properly.
     */
    private val LEMONADE_STATE = "LEMONADE_STATE"
    private val LEMON_SIZE = "LEMON_SIZE"
    private val SQUEEZE_COUNT = "SQUEEZE_COUNT"
    // SELECT represents the "pick lemon" state
    private val SELECT = "select"
    // SQUEEZE represents the "squeeze lemon" state
    private val SQUEEZE = "squeeze"
    // DRINK represents the "drink lemonade" state
    private val DRINK = "drink"
    // RESTART represents the state where the lemonade has be drunk and the glass is empty
    private val RESTART = "restart"
    // Default the state to select
    private var lemonadeState = "select"
    // Default lemonSize to -1
    private var lemonSize = -1
    // Default the squeezeCount to -1
    private var squeezeCount = -1

    private var lemonTree = LemonTree()
    private var lemonImage: ImageView? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        // === DO NOT ALTER THE CODE IN THE FOLLOWING IF STATEMENT ===
        if (savedInstanceState != null) {
            lemonadeState = savedInstanceState.getString(LEMONADE_STATE, "select")
            lemonSize = savedInstanceState.getInt(LEMON_SIZE, -1)
            squeezeCount = savedInstanceState.getInt(SQUEEZE_COUNT, -1)
        }
        // === END IF STATEMENT ===

        lemonImage = findViewById(R.id.image_lemon_state)
        setViewElements()
        lemonImage!!.setOnClickListener {
            // TODO: Llame al método que controla el estado en el que se hace clic en la imagen
            clickLemonImage()
        }
        lemonImage!!.setOnLongClickListener {
            // TODO: replace 'false' with a call to the function that shows the squeeze count
            showSnackbar()
        }
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * This method saves the state of the app if it is put in the background.
     */
    override fun onSaveInstanceState(outState: Bundle) {
        outState.putString(LEMONADE_STATE, lemonadeState)
        outState.putInt(LEMON_SIZE, lemonSize)
        outState.putInt(SQUEEZE_COUNT, squeezeCount)
        super.onSaveInstanceState(outState)
    }

    /**
     * Clicking will elicit a different response depending on the state.
     * This method determines the state and proceeds with the correct action.
     */
    private fun clickLemonImage() {
        // TODO: use una declaración condicional como 'if' o 'when' para rastrear la lemonadeState
        //  cuando se hace clic en la imagen, es posible que tengamos que cambiar el estado al siguiente paso en la
        //  progresión de la limonada (o al menos hacer algunos cambios en el estado actual en el
        //  caso de exprimir el limón). Eso debe hacerse en esta declaración condicional.


        // TODO: Cuando se hace clic en la imagen en el estado SELECT, el estado debe convertirse en SQUEEZE
        //  - La variable lemonSize debe establecerse utilizando el método 'pick()' en la clase LemonTree
        //  - El squeezeCount debe ser 0 ya que aún no hemos exprimido ningún limón.

        if (lemonadeState == SELECT){
            lemonadeState = SQUEEZE
            lemonSize = lemonTree.pick()
            squeezeCount = 0
        }
        // TODO: Cuando se hace clic en la imagen en el estado SQUEEZE, squeezeCount debe ser
        //  INCREASED en 1 y el tamaño del limón debe DECREASED en 1.
        //  - Si el lemonSize ha llegado a 0, se ha exprimido y el estado debe convertirse en DRINK
        //  - Además, lemonSize ya no es relevante y debe establecerse en -1
        else if (lemonadeState == SQUEEZE){
            if (lemonSize != 0){
                squeezeCount++
                lemonSize--
            }
            else{
                lemonadeState = DRINK
                lemonSize = -1
            }
        }
        // TODO: Cuando se hace clic en la imagen en el estado DRINK, el estado debe convertirse en RESTART
        else if (lemonadeState == DRINK){
            lemonadeState = RESTART
        }
        // TODO: Cuando se hace clic en la imagen en el estado RESTART, el estado debe convertirse en SELECT
        else if(lemonadeState == RESTART){
            lemonadeState = SELECT
        }
        // TODO: por último, antes de que finalice la función, debemos establecer los elementos de vista para que el
        //  UI pueda reflejar el estado correcto
        setViewElements()
    }

    /**
     * Set up the view elements according to the state.
     */
    private fun setViewElements() {
        // TODO: Configure un condicional que realice un seguimiento de la lemonadeState

        // TODO: Para cada estado, textAction TextView debe establecerse en la cadena correspondiente desde
        //  el archivo de recursos de cadena. Las cadenas se nombran para que coincidan con el estado

        val lemonText: TextView = findViewById(R.id.text_action)

        when(lemonadeState){
            SELECT -> lemonText.setText(R.string.lemon_select)
            SQUEEZE -> lemonText.setText(R.string.lemon_squeeze)
            DRINK -> lemonText.setText(R.string.lemon_drink)
            else -> lemonText.setText(R.string.lemon_empty_glass)
        }

        // TODO: Además, para cada estado (for each state), la lemonImage debe establecerse en la correspondiente
        //  drawable de los recursos drawable. Los dibujables (drawables) tienen los mismos nombres que los Strings
        //  pero recuerda que son dibujables, no son String.

        val drawableResource = when(lemonadeState){
            SELECT -> R.drawable.lemon_tree
            SQUEEZE -> R.drawable.lemon_squeeze
            DRINK -> R.drawable.lemon_drink
            else -> R.drawable.lemon_restart
        }
        lemonImage?.setImageResource(drawableResource)
    }

    /**
     * === DO NOT ALTER THIS METHOD ===
     *
     * Long clicking the lemon image will show how many times the lemon has been squeezed.
     */
    private fun showSnackbar(): Boolean {
        if (lemonadeState != SQUEEZE) {
            return false
        }
        val squeezeText = getString(R.string.squeeze_count, squeezeCount)
        Snackbar.make(
            findViewById(R.id.constraint_Layout),
            squeezeText,
            Snackbar.LENGTH_SHORT
        ).show()
        return true
    }
}

/**
 * A Lemon tree class with a method to "pick" a lemon. The "size" of the lemon is randomized
 * and determines how many times a lemon needs to be squeezed before you get lemonade.
 */
class LemonTree {
    fun pick(): Int {
        return (2..4).random()
    }
}
