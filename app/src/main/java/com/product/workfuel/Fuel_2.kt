package com.product.workfuel

import android.annotation.SuppressLint
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat

class Fuel_2 : AppCompatActivity() {
    private var totalEntradas: Double = 0.0
    private var totalBilletesYMonedas: Double = 0.0
    private lateinit var inputPrecioDiesel: EditText
    private lateinit var inputPrecioEcoPais: EditText
    private lateinit var inputPrecioSuper: EditText
    private lateinit var btnSave: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_fuel2)

        inputPrecioDiesel = findViewById(R.id.input_precio_diesel2)
        inputPrecioEcoPais = findViewById(R.id.input_precio_ecopais2)
        inputPrecioSuper = findViewById(R.id.input_precio_super2)
        btnSave = findViewById(R.id.btn_save)
        sharedPreferences = getSharedPreferences("MisPrecios", MODE_PRIVATE)
        cargarDatos()
        btnSave.setOnClickListener {
            guardarDatos()
        }
        val btnConfiguracion = findViewById<ImageView>(R.id.btn_configuracion2)
        btnConfiguracion.setOnClickListener { view ->
            showPopupMenu(view)
        }

        val entradas_2 = arrayOf(
            findViewById<EditText>(R.id.input_entrada1_2),
            findViewById<EditText>(R.id.input_entrada2_2),
            findViewById<EditText>(R.id.input_entrada3_2),
            findViewById<EditText>(R.id.input_entrada4_2),
            findViewById<EditText>(R.id.input_entrada5_2),
            findViewById<EditText>(R.id.input_entrada6_2),
            findViewById<EditText>(R.id.input_entrada7_2),
            findViewById<EditText>(R.id.input_entrada8_2)
        )
        val salidas_2 = arrayOf(
            findViewById<EditText>(R.id.input_salida1_2),
            findViewById<EditText>(R.id.input_salida2_2),
            findViewById<EditText>(R.id.input_salida3_2),
            findViewById<EditText>(R.id.input_salida4_2),
            findViewById<EditText>(R.id.input_salida5_2),
            findViewById<EditText>(R.id.input_salida6_2),
            findViewById<EditText>(R.id.input_salida7_2),
            findViewById<EditText>(R.id.input_salida8_2)
        )
        val inputCaja2 = findViewById<EditText>(R.id.input_caja2)
        val inputCheque2 = findViewById<EditText>(R.id.input_cheque2)
        val inputVoucher2 = findViewById<EditText>(R.id.input_voucher2)
        val inputRecibos2 = findViewById<EditText>(R.id.input_recibos2)
        val inputCreditos2 = findViewById<EditText>(R.id.input_creditos2)

        val precioDieselEdit2 = findViewById<EditText>(R.id.input_precio_diesel2)
        val precioEcoPaisEdit2 = findViewById<EditText>(R.id.input_precio_ecopais2)
        val precioSuperEdit2 = findViewById<EditText>(R.id.input_precio_super2)

        val btnCalcular2 = findViewById<Button>(R.id.btn_calcular2)
        val btnVerOperaciones2 = findViewById<Button>(R.id.btn_ver_operaciones2)
        val txtResultados2 = findViewById<TextView>(R.id.txt_resultados2)
        val txtDetalles2 = findViewById<TextView>(R.id.txt_detalles2)

        val resultados2 = mutableListOf<Double>()
        val operaciones2 = mutableListOf<String>()
        val resultadosFinales2 = mutableMapOf<String, Double>()

        // Añadir los EditText de billetes y monedas
        val inputCien2 = findViewById<EditText>(R.id.input_cien2)
        val inputDolar2 = findViewById<EditText>(R.id.input_dolar2)
        val inputCincuenta2 = findViewById<EditText>(R.id.input_cincuenta2)
        val inputMocinquenta2 = findViewById<EditText>(R.id.input_mocinquenta2)
        val inputVeinte2 = findViewById<EditText>(R.id.input_veinte2)
        val inputVeinticinco2 = findViewById<EditText>(R.id.input_veinticinco2)
        val inputDiez2 = findViewById<EditText>(R.id.input_diez2)
        val inputCendiez2 = findViewById<EditText>(R.id.input_cendiez2)
        val inputCinco2 = findViewById<EditText>(R.id.input_cinco2)
        val inputCencinco2 = findViewById<EditText>(R.id.input_cencinco2)
        val inputDolars2 = findViewById<EditText>(R.id.input_dolars2)
        val inputCentavo2 = findViewById<EditText>(R.id.input_centavo2)

        // Función para calcular el total de billetes y monedas
        fun calcularTotalBilletesYMonedas(): Double {
            val cien = inputCien2.text.toString().toDoubleOrNull() ?: 0.0
            val dolar = inputDolar2.text.toString().toDoubleOrNull() ?: 0.0
            val cincuenta = inputCincuenta2.text.toString().toDoubleOrNull() ?: 0.0
            val mocinquenta = inputMocinquenta2.text.toString().toDoubleOrNull() ?: 0.0
            val veinte = inputVeinte2.text.toString().toDoubleOrNull() ?: 0.0
            val veinticinco = inputVeinticinco2.text.toString().toDoubleOrNull() ?: 0.0
            val diez = inputDiez2.text.toString().toDoubleOrNull() ?: 0.0
            val cendiez = inputCendiez2.text.toString().toDoubleOrNull() ?: 0.0
            val cinco = inputCinco2.text.toString().toDoubleOrNull() ?: 0.0
            val cencinco = inputCencinco2.text.toString().toDoubleOrNull() ?: 0.0
            val dolars = inputDolars2.text.toString().toDoubleOrNull() ?: 0.0
            val centavo = inputCentavo2.text.toString().toDoubleOrNull() ?: 0.0

            // Calcular el total en billetes y monedas
            val totalBilletes = (cien * 100) + (dolar * 1) + (cincuenta * 50) + (veinte * 20) + (diez * 10) + (cinco * 5)
            val totalMonedas = (mocinquenta * 0.50) + (veinticinco * 0.25) + (cendiez * 0.10) + (cencinco * 0.05) + (dolars * 1) + (centavo * 0.01)

            return totalBilletes + totalMonedas
        }
        btnCalcular2.setOnClickListener {
            resultados2.clear()
            operaciones2.clear()
            resultadosFinales2.clear()

            val precioDiesel = inputPrecioDiesel.text.toString().toDoubleOrNull() ?: 0.0
            val precioEcoPais = inputPrecioEcoPais.text.toString().toDoubleOrNull() ?: 0.0
            val precioSuper = inputPrecioSuper.text.toString().toDoubleOrNull() ?: 0.0

            val cajaTotal = inputCaja2.text.toString().toDoubleOrNull() ?: 0.0
            val chequeTotal = inputCheque2.text.toString().toDoubleOrNull() ?: 0.0
            val voucherTotal = inputVoucher2.text.toString().toDoubleOrNull() ?: 0.0
            val recibosTotal = inputRecibos2.text.toString().toDoubleOrNull() ?: 0.0
            val creditosazul = inputCreditos2.text.toString().toDoubleOrNull() ?: 0.0

            totalEntradas = cajaTotal + chequeTotal + voucherTotal + recibosTotal +creditosazul

            for (i in entradas_2.indices) {
                val entrada = entradas_2[i].text.toString().toDoubleOrNull() ?: 0.0
                val salida = salidas_2[i].text.toString().toDoubleOrNull() ?: 0.0
                val resultado = salida - entrada
                resultados2.add(resultado)
                operaciones2.add("S${i + 1} - E${i + 1} = ${String.format("%.2f", salida)} - ${String.format("%.2f", entrada)} = ${String.format("%.2f", resultado)}")
            }

            if (resultados2.size >= 8) {
                val diesel = resultados2[0] + resultados2[3]+ resultados2[6] + resultados2[7]
                val ecoPais = resultados2[1] + resultados2[4]
                val superGas = resultados2[2] + resultados2[5]

                val costoDiesel = diesel * precioDiesel
                val costoEcoPais = ecoPais * precioEcoPais
                val costoSuper = superGas * precioSuper

                resultadosFinales2["Diesel"] = costoDiesel
                resultadosFinales2["Eco País"] = costoEcoPais
                resultadosFinales2["Super"] = costoSuper

                val totalVentas = costoDiesel + costoEcoPais + costoSuper
                resultadosFinales2["Total de Ventas"] = totalVentas

                totalBilletesYMonedas = calcularTotalBilletesYMonedas()

                // Crear la cadena finales para los resultados finales como un número.
                val totalResumen = totalEntradas + totalBilletesYMonedas
                resultadosFinales2["Resultados finales"] = totalResumen // Almacena como Double

                // Mostrar los resultados finales en txtResultados
                val resultadosText = resultadosFinales2.entries.joinToString("\n") {
                    "${it.key}: $${String.format("%.2f", it.value)}"
                }

                txtResultados2.text = resultadosText

            } else {
                txtResultados2.text = "No hay suficientes resultados para calcular."
            }

            txtDetalles2.visibility = View.GONE
        }
        btnVerOperaciones2.setOnClickListener {
            totalBilletesYMonedas = calcularTotalBilletesYMonedas()

            if (resultadosFinales2.isNotEmpty() && resultados2.size >= 8) {
                val diesel = resultados2[0] + resultados2[3]+ resultados2[6] + resultados2[7]
                val ecoPais = resultados2[1] + resultados2[4]
                val superGas = resultados2[2] + resultados2[5]

                val detallesFinales = """
            Diesel: R1 + R4 + R7 + R8 = ${String.format("%.2f", resultados2[0])} + ${String.format("%.2f", resultados2[3])} + ${String.format("%.2f", resultados2[6])} +${String.format("%.2f", resultados2[7])} = ${String.format("%.2f", diesel)}
            Costo Diesel: $${String.format("%.2f", resultadosFinales2["Diesel"]!!)}

            Eco País: R2 + R5 = ${String.format("%.2f", resultados2[1])} + ${String.format("%.2f", resultados2[4])} = ${String.format("%.2f", ecoPais)}
            Costo Eco País: $${String.format("%.2f", resultadosFinales2["Eco País"]!!)}

            Super: R3 + R6 = ${String.format("%.2f", resultados2[2])} + ${String.format("%.2f", resultados2[5])} = ${String.format("%.2f", superGas)}
            Costo Super: $${String.format("%.2f", resultadosFinales2["Super"]!!)}

            Total de Ventas: $${String.format("%.2f", resultadosFinales2["Total de Ventas"]!!)}
        """.trimIndent()

                val resumenTotalEntradas = "Suma Total Entradas: caja + cheque + voucher + recibos : $${String.format("%.2f", totalEntradas)}"
                val resumenTotalBilletesYMonedas = "Total en Billetes y Monedas: $${String.format("%.2f", totalBilletesYMonedas)}"

                val totalResumen = totalEntradas + totalBilletesYMonedas
                val finales = "Resultados finales: $${String.format("%.2f", totalResumen)}"

                txtDetalles2.text = operaciones2.joinToString("\n") + "\n\n" + detallesFinales + "\n\n" + resumenTotalEntradas + "\n\n" + resumenTotalBilletesYMonedas + "\n\n" + finales
                txtDetalles2.visibility = View.VISIBLE
            } else {
                txtDetalles2.text = "No hay resultados disponibles. Por favor, calcule los resultados primero."
                txtDetalles2.visibility = View.VISIBLE
            }
        }
    }
    private fun guardarDatos() {
        val editor = sharedPreferences.edit()
        editor.putString("precio_diesel", inputPrecioDiesel.text.toString())
        editor.putString("precio_ecopais", inputPrecioEcoPais.text.toString())
        editor.putString("precio_super", inputPrecioSuper.text.toString())
        editor.apply() // Guardar los cambios

        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Precios guardados correctamente", Toast.LENGTH_SHORT).show()
    }
    private fun cargarDatos() {
        inputPrecioDiesel.setText(sharedPreferences.getString("precio_diesel", "1.797"))
        inputPrecioEcoPais.setText(sharedPreferences.getString("precio_ecopais", "2.466"))
        inputPrecioSuper.setText(sharedPreferences.getString("precio_super", "3.699"))
    }
    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_settings2, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.option1 -> {
                    showAboutDialog()
                    true
                }
                R.id.option2 ->{
                    val intent = Intent(this, MainActivity::class.java)
                    startActivity(intent) // Inicia la nueva actividad
                    true

                }

                else -> false
            }
        }

        popupMenu.show()
    }
    private fun showAboutDialog() {
        AlertDialog.Builder(this)
            .setTitle("Acerca de")
            .setMessage(
                """
            Aplicación desarrollada por el Ing. Víctor Chiquito.
            
            • Proyecto: Código Abierto (Open Source)
            • Fecha de creación: 11-01-2025
            • Versión: 1.0.0
            
            ¡Gracias por usar nuestra aplicación!
            """.trimIndent()
            )
            .setPositiveButton("Aceptar") { dialog, _ ->
                dialog.dismiss()
            }
            .setNeutralButton("Más información") { _, _ ->
                val url = "https://github.com/Azunacool300/WorkFuel.git" // Cambia esto por tu URL
                val intent = Intent(Intent.ACTION_VIEW, Uri.parse(url))
                startActivity(intent)
            }
            .show()
    }

}