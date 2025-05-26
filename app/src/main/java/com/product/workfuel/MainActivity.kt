package com.product.workfuel;
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.PopupMenu
import android.widget.TextView
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.product.workfuel.R

import com.product.workfuel.ui.theme.WorkFuelTheme
import java.math.BigDecimal
import java.math.RoundingMode

class MainActivity : AppCompatActivity() {
    private var totalEntradas: Double = 0.0
    private var totalBilletesYMonedas: Double = 0.0
    private lateinit var precioDieselEdit: EditText
    private lateinit var precioEcoPaisEdit: EditText
    private lateinit var precioSuperEdit: EditText
    private lateinit var sharedPreferences: SharedPreferences
    private lateinit var btnSave: ImageView
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.principal)
        btnSave = findViewById(R.id.btn_save)
        val btnConfiguracion = findViewById<ImageView>(R.id.btn_configuracion)
        sharedPreferences = getSharedPreferences("MisPrecios", MODE_PRIVATE)

        btnConfiguracion.setOnClickListener { view ->
            showPopupMenu(view)
        }

        val entradas = arrayOf(
            findViewById<EditText>(R.id.input_entrada1),
            findViewById<EditText>(R.id.input_entrada2),
            findViewById<EditText>(R.id.input_entrada3),
            findViewById<EditText>(R.id.input_entrada4),
            findViewById<EditText>(R.id.input_entrada5),
            findViewById<EditText>(R.id.input_entrada6)


        )
        val salidas = arrayOf(
            findViewById<EditText>(R.id.input_salida1),
            findViewById<EditText>(R.id.input_salida2),
            findViewById<EditText>(R.id.input_salida3),
            findViewById<EditText>(R.id.input_salida4),
            findViewById<EditText>(R.id.input_salida5),
            findViewById<EditText>(R.id.input_salida6)

        )
        val inputCaja = findViewById<EditText>(R.id.input_caja)
        val inputCheque = findViewById<EditText>(R.id.input_cheque)
        val inputVoucher = findViewById<EditText>(R.id.input_voucher)
        val inputRecibos = findViewById<EditText>(R.id.input_recibos)
        val inputCreditos = findViewById<EditText>(R.id.input_creditos)

        precioDieselEdit = findViewById<EditText>(R.id.input_precio_diesel)
        precioEcoPaisEdit = findViewById<EditText>(R.id.input_precio_ecopais)
        precioSuperEdit = findViewById<EditText>(R.id.input_precio_super)

        val btnCalcular = findViewById<Button>(R.id.btn_calcular)
        val btnVerOperaciones = findViewById<Button>(R.id.btn_ver_operaciones)
        val txtResultados = findViewById<TextView>(R.id.txt_resultados)
        val txtDetalles = findViewById<TextView>(R.id.txt_detalles)

        val resultados = mutableListOf<Double>()
        val operaciones = mutableListOf<String>()
        val resultadosFinales = mutableMapOf<String, Double>()

        // Añadir los EditText de billetes y monedas
        val inputCien = findViewById<EditText>(R.id.input_cien)
        val inputDolar = findViewById<EditText>(R.id.input_dolar)
        val inputCincuenta = findViewById<EditText>(R.id.input_cincuenta)
        val inputMocinquenta = findViewById<EditText>(R.id.input_mocinquenta)
        val inputVeinte = findViewById<EditText>(R.id.input_veinte)
        val inputVeinticinco = findViewById<EditText>(R.id.input_veinticinco)
        val inputDiez = findViewById<EditText>(R.id.input_diez)
        val inputCendiez = findViewById<EditText>(R.id.input_cendiez)
        val inputCinco = findViewById<EditText>(R.id.input_cinco)
        val inputCencinco = findViewById<EditText>(R.id.input_cencinco)
        val inputDolars = findViewById<EditText>(R.id.input_dolars)
        val inputCentavo = findViewById<EditText>(R.id.input_centavo)

        // Método para guardar los valores en SharedPreferences

        cargarDatos()
        // Configurar el botón para guardar los datos
        btnSave.setOnClickListener {
            guardarDatos()
        }


        // Función para calcular el total de billetes y monedas
        fun calcularTotalBilletesYMonedas(): Double {
            val cien = inputCien.text.toString().toDoubleOrNull() ?: 0.0
            val dolar = inputDolar.text.toString().toDoubleOrNull() ?: 0.0
            val cincuenta = inputCincuenta.text.toString().toDoubleOrNull() ?: 0.0
            val mocinquenta = inputMocinquenta.text.toString().toDoubleOrNull() ?: 0.0
            val veinte = inputVeinte.text.toString().toDoubleOrNull() ?: 0.0
            val veinticinco = inputVeinticinco.text.toString().toDoubleOrNull() ?: 0.0
            val diez = inputDiez.text.toString().toDoubleOrNull() ?: 0.0
            val cendiez = inputCendiez.text.toString().toDoubleOrNull() ?: 0.0
            val cinco = inputCinco.text.toString().toDoubleOrNull() ?: 0.0
            val cencinco = inputCencinco.text.toString().toDoubleOrNull() ?: 0.0
            val dolars = inputDolars.text.toString().toDoubleOrNull() ?: 0.0
            val centavo = inputCentavo.text.toString().toDoubleOrNull() ?: 0.0

            // Calcular el total en billetes y monedas
            val totalBilletes = (cien * 100) + (dolar * 1) + (cincuenta * 50) + (veinte * 20) + (diez * 10) + (cinco * 5)
            val totalMonedas = (mocinquenta * 0.50) + (veinticinco * 0.25) + (cendiez * 0.10) + (cencinco * 0.05) + (dolars * 1) + (centavo * 0.01)

            return totalBilletes + totalMonedas
        }

        btnCalcular.setOnClickListener {
            resultados.clear()
            operaciones.clear()
            resultadosFinales.clear()

            val precioDiesel = precioDieselEdit.text.toString().toDoubleOrNull() ?: 0.0
            val precioEcoPais = precioEcoPaisEdit.text.toString().toDoubleOrNull() ?: 0.0
            val precioSuper = precioSuperEdit.text.toString().toDoubleOrNull() ?: 0.0

            val cajaTotal = inputCaja.text.toString().toDoubleOrNull() ?: 0.0
            val chequeTotal = inputCheque.text.toString().toDoubleOrNull() ?: 0.0
            val voucherTotal = inputVoucher.text.toString().toDoubleOrNull() ?: 0.0
            val recibosTotal = inputRecibos.text.toString().toDoubleOrNull() ?: 0.0
            val creditosazul = inputCreditos.text.toString().toDoubleOrNull() ?: 0.0

            totalEntradas = cajaTotal + chequeTotal + voucherTotal + recibosTotal +creditosazul

            for (i in entradas.indices) {
                val entrada = entradas[i].text.toString().toDoubleOrNull() ?: 0.0
                val salida = salidas[i].text.toString().toDoubleOrNull() ?: 0.0
                val resultado = salida - entrada
                resultados.add(resultado)
                operaciones.add("S${i + 1} - E${i + 1} = ${String.format("%.2f", salida)} - ${String.format("%.2f", entrada)} = ${String.format("%.2f", resultado)}")
            }

            if (resultados.size >= 6) {
                val diesel = resultados[0] + resultados[5]
                val ecoPais = resultados[1] + resultados[4]
                val superGas = resultados[2] + resultados[3]

                val costoDiesel = diesel * precioDiesel
                val costoEcoPais = ecoPais * precioEcoPais
                val costoSuper = superGas * precioSuper

                resultadosFinales["Diesel"] = costoDiesel
                resultadosFinales["Eco País"] = costoEcoPais
                resultadosFinales["Super"] = costoSuper

                val totalVentas = costoDiesel + costoEcoPais + costoSuper
                resultadosFinales["Total de Ventas"] = totalVentas

                totalBilletesYMonedas = calcularTotalBilletesYMonedas()

                // Crear la cadena finales para los resultados finales como un número.
                val totalResumen = totalEntradas + totalBilletesYMonedas
                resultadosFinales["Resultados finales"] = totalResumen // Almacena como Double

                // Resta de Total de Ventas con Resultados Finales
                val diferencia = totalVentas - totalResumen
                // Verificar si es un faltante o sobrante
                val mensajeFaltanteSobrante = if (diferencia > 0) {
                    "Faltante: $${String.format("%.2f", diferencia)}"
                } else {
                    "Sobrante: $${String.format("%.2f", -diferencia)}"
                }
                // Agregar el resultado de la resta al mapa de resultados finales
                resultadosFinales["Diferencia (Faltante/Sobrante)"] = diferencia

                // Mostrar los resultados finales en txtResultados
                val resultadosText = resultadosFinales.entries.joinToString("\n") {
                    "${it.key}: $${String.format("%.2f", it.value)}"
                } + "\n" + mensajeFaltanteSobrante

                txtResultados.text = resultadosText

            } else {
                txtResultados.text = "No hay suficientes resultados para calcular."
            }

            txtDetalles.visibility = View.GONE
        }


        btnVerOperaciones.setOnClickListener {
            totalBilletesYMonedas = calcularTotalBilletesYMonedas()

            if (resultadosFinales.isNotEmpty() && resultados.size >= 6) {
                val diesel = resultados[0] + resultados[5]
                val ecoPais = resultados[1] + resultados[4]
                val superGas = resultados[2] + resultados[3]

                val detallesFinales = """
            Diesel: R1 + R6 = ${String.format("%.2f", resultados[0])} + ${String.format("%.2f", resultados[5])}   = ${String.format("%.2f", diesel)}
            Resultado * Diesel: ${String.format("%.2f", diesel)} × ${BigDecimal(precioDieselEdit.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Diesel"]!!)}
            Dinero en Diesel: $${String.format("%.2f", resultadosFinales["Diesel"]!!)}

            Eco País: R2 + R5 = ${String.format("%.2f", resultados[1])} + ${String.format("%.2f", resultados[4])} = ${String.format("%.2f", ecoPais)}
            Resultado * Eco País: ${String.format("%.2f", ecoPais)} × ${BigDecimal(precioEcoPaisEdit.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Eco País"]!!)}
            Dinero en Eco País: $${String.format("%.2f", resultadosFinales["Eco País"]!!)}

            Super: R3 + R4 = ${String.format("%.2f", resultados[2])} + ${String.format("%.2f", resultados[3])} = ${String.format("%.2f", superGas)}
            Resultado * Super: ${String.format("%.2f", superGas)} × ${BigDecimal(precioSuperEdit.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Super"]!!)}
            Dinero en Super: $${String.format("%.2f", resultadosFinales["Super"]!!)}
 
            Total de Ventas: $${String.format("%.2f", resultadosFinales["Total de Ventas"]!!)}
            Datos del despachador
        """.trimIndent()
                val cajaTotal = inputCaja.text.toString().toDoubleOrNull() ?: 0.0
                val chequeTotal = inputCheque.text.toString().toDoubleOrNull() ?: 0.0
                val voucherTotal = inputVoucher.text.toString().toDoubleOrNull() ?: 0.0
                val recibosTotal = inputRecibos.text.toString().toDoubleOrNull() ?: 0.0
                val creditosazul = inputCreditos.text.toString().toDoubleOrNull() ?: 0.0

                val resumenTotalEntradas = "Suma Total Entradas: " +
                        "${String.format("%.2f", cajaTotal)} + " +
                        "${String.format("%.2f", chequeTotal)} + " +
                        "${String.format("%.2f", voucherTotal)} + " +
                        "${String.format("%.2f", recibosTotal)} + " +
                        "${String.format("%.2f", creditosazul)} = " +
                        "$${String.format("%.2f", totalEntradas)}"

                val resumenTotalBilletesYMonedas = "Total en Billetes y Monedas: $${String.format("%.2f", totalBilletesYMonedas)}"

                val totalResumen = totalEntradas + totalBilletesYMonedas
                val finales = "Resultados finales: $${String.format("%.2f", totalResumen)}"

                txtDetalles.text = operaciones.joinToString("\n") + "\n\n" + detallesFinales + "\n\n" + resumenTotalEntradas + "\n\n" + resumenTotalBilletesYMonedas + "\n\n" + finales
                txtDetalles.visibility = View.VISIBLE
            } else {
                txtDetalles.text = "No hay resultados disponibles. Por favor, calcule los resultados primero."
                txtDetalles.visibility = View.VISIBLE
            }
        }
    }
     private fun guardarDatos() {
        val editor = sharedPreferences.edit()
        editor.putString("precio_diesel", precioDieselEdit.text.toString())
        editor.putString("precio_ecopais", precioEcoPaisEdit.text.toString())
        editor.putString("precio_super", precioSuperEdit.text.toString())
        editor.apply() // Guardar los cambios

        // Mostrar un mensaje de confirmación
        Toast.makeText(this, "Precios guardados correctamente", Toast.LENGTH_SHORT).show()
    }
    fun cargarDatos() {
        precioDieselEdit.setText(sharedPreferences.getString("precio_diesel", "1.797") ?: "1.797")
        precioEcoPaisEdit.setText(sharedPreferences.getString("precio_ecopais", "2.466")?: "2.466")
        precioSuperEdit.setText(sharedPreferences.getString("precio_super", "3.699")?: "3.699")
    }

    private fun showPopupMenu(view: View) {
        val popupMenu = PopupMenu(this, view)
        val inflater: MenuInflater = popupMenu.menuInflater
        inflater.inflate(R.menu.menu_settings, popupMenu.menu)

        popupMenu.setOnMenuItemClickListener { item: MenuItem ->
            when (item.itemId) {
                R.id.option1 -> {
                    showAboutDialog()
                    true
                }
                R.id.option2 ->{
                    val intent = Intent(this, Fuel_2::class.java)
                    startActivity(intent) // Inicia la nueva actividad
                    true

                }
                R.id.option3 ->{
                    val intent = Intent(this, Lecturas_isla5_y_6::class.java)
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




