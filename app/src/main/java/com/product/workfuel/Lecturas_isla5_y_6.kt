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
import java.math.BigDecimal
import java.math.RoundingMode

class Lecturas_isla5_y_6 : AppCompatActivity() {
    private var totalEntradas5y6: Double = 0.0
    private var totalBilletesYMonedas5y6: Double = 0.0
    private lateinit var inputPrecioDiesel: EditText
    private lateinit var inputPrecioEcoPais: EditText
    private lateinit var inputPrecioSuper: EditText
    private lateinit var btnSave: ImageView
    private lateinit var sharedPreferences: SharedPreferences
    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContentView(R.layout.activity_lecturas_isla5_y6)
        // Vincular con los elementos del XML
        inputPrecioDiesel = findViewById(R.id.input_precio_diesel5)
        inputPrecioEcoPais = findViewById(R.id.input_precio_ecopais5)
        inputPrecioSuper = findViewById(R.id.input_precio_super5)
        btnSave = findViewById(R.id.btn_save)
        val btnConfiguracion = findViewById<ImageView>(R.id.btn_configuracion)
        // Inicializar SharedPreferences
        sharedPreferences = getSharedPreferences("MisPrecios", MODE_PRIVATE)
        // Cargar datos guardados previamente
        cargarDatos()
        // Configurar el botón para guardar los datos
        btnSave.setOnClickListener {
            guardarDatos()
        }
        btnConfiguracion.setOnClickListener { view ->
            showPopupMenu(view)
        }
        val entradas6 = arrayOf(
            findViewById<EditText>(R.id.i6input1),
            findViewById<EditText>(R.id.i6input2),
            findViewById<EditText>(R.id.i6input3),
            findViewById<EditText>(R.id.i6input4),
            findViewById<EditText>(R.id.i6input5),
            findViewById<EditText>(R.id.i6input6)
        )
        val entradas5 = arrayOf(
            findViewById<EditText>(R.id.i5input1),
            findViewById<EditText>(R.id.i5input2),
            findViewById<EditText>(R.id.i5input3),
            findViewById<EditText>(R.id.i5input4),
            findViewById<EditText>(R.id.i5input5),
            findViewById<EditText>(R.id.i5input6)
        )
        val salidas6 = arrayOf(
            findViewById<EditText>(R.id.i6out1),
            findViewById<EditText>(R.id.i6out2),
            findViewById<EditText>(R.id.i6out3),
            findViewById<EditText>(R.id.i6out4),
            findViewById<EditText>(R.id.i6out5),
            findViewById<EditText>(R.id.i6out6)
        )
        val salidas5 = arrayOf(
            findViewById<EditText>(R.id.i5out1),
            findViewById<EditText>(R.id.i5out2),
            findViewById<EditText>(R.id.i5out3),
            findViewById<EditText>(R.id.i5out4),
            findViewById<EditText>(R.id.i5out5),
            findViewById<EditText>(R.id.i5out6)
        )
        val inputCaja = findViewById<EditText>(R.id.input_caja5y6)
        val inputCheque = findViewById<EditText>(R.id.input_cheque5y6)
        val inputVoucher = findViewById<EditText>(R.id.input_voucher5y6)
        val inputRecibos = findViewById<EditText>(R.id.input_recibos5y6)
        val inputCreditos = findViewById<EditText>(R.id.input_creditos5y6)

        val btnCalcular = findViewById<Button>(R.id.btn_calcular5y6)
        val btnVerOperaciones = findViewById<Button>(R.id.btn_ver_operaciones5y6)
        val txtResultados = findViewById<TextView>(R.id.txt_resultados5y6)
        val txtDetalles = findViewById<TextView>(R.id.txt_detalles5y6)

        val resultados = mutableListOf<Double>()
        val operaciones = mutableListOf<String>()
        val resultadosFinales = mutableMapOf<String, Double>()


        // Añadir los EditText de billetes y monedas
        val inputCien = findViewById<EditText>(R.id.input_cien5y6)
        val inputDolar = findViewById<EditText>(R.id.input_dolar5y6)
        val inputCincuenta = findViewById<EditText>(R.id.input_cincuenta5y6)
        val inputMocinquenta = findViewById<EditText>(R.id.input_mocinquenta5y6)
        val inputVeinte = findViewById<EditText>(R.id.input_veinte5y6)
        val inputVeinticinco = findViewById<EditText>(R.id.input_veinticinco5y6)
        val inputDiez = findViewById<EditText>(R.id.input_diez5y6)
        val inputCendiez = findViewById<EditText>(R.id.input_cendiez5y6)
        val inputCinco = findViewById<EditText>(R.id.input_cinco5y6)
        val inputCencinco = findViewById<EditText>(R.id.input_cencinco5y6)
        val inputDolars = findViewById<EditText>(R.id.input_dolars5y6)
        val inputCentavo = findViewById<EditText>(R.id.input_centavo5y6)

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

            val precioDiesel = inputPrecioDiesel.text.toString().toDoubleOrNull() ?: 0.0
            val precioEcoPais = inputPrecioEcoPais.text.toString().toDoubleOrNull() ?: 0.0
            val precioSuper = inputPrecioSuper.text.toString().toDoubleOrNull() ?: 0.0

            val cajaTotal = inputCaja.text.toString().toDoubleOrNull() ?: 0.0
            val chequeTotal = inputCheque.text.toString().toDoubleOrNull() ?: 0.0
            val voucherTotal = inputVoucher.text.toString().toDoubleOrNull() ?: 0.0
            val recibosTotal = inputRecibos.text.toString().toDoubleOrNull() ?: 0.0
            val creditosazul = inputCreditos.text.toString().toDoubleOrNull() ?: 0.0

            totalEntradas5y6 = cajaTotal + chequeTotal + voucherTotal + recibosTotal +creditosazul

            for (i in entradas6.indices) {
                val entrada = entradas6[i].text.toString().toDoubleOrNull() ?: 0.0
                val salida = salidas6[i].text.toString().toDoubleOrNull() ?: 0.0
                val resultado = salida - entrada
                resultados.add(resultado)
                operaciones.add("S${i + 1} - E${i + 1} = ${String.format("%.2f", salida)} - ${String.format("%.2f", entrada)} = ${String.format("%.2f", resultado)}")
            }
            for (i in entradas5.indices) {
                val entrada = entradas5[i].text.toString().toDoubleOrNull() ?: 0.0
                val salida = salidas5[i].text.toString().toDoubleOrNull() ?: 0.0
                val resultado = salida - entrada
                resultados.add(resultado)
                operaciones.add("S${i + 1} - E${i + 1} = ${String.format("%.2f", salida)} - ${String.format("%.2f", entrada)} = ${String.format("%.2f", resultado)}")
            }

            if (resultados.size >= 12) {
                val dieselm1 = resultados[0]
                val ecoPaism1 = resultados[1]
                val superGasm1 = resultados[2]

                val dieselm2 = resultados[3]
                val ecoPaism2 = resultados[4]
                val superGasm2 = resultados[5]

                val costoDieselm1 = dieselm1 * precioDiesel
                val costoEcoPaism1 = ecoPaism1 * precioEcoPais
                val costoSuperm1 = superGasm1 * precioSuper

                val costoDieselm2 = dieselm2 * precioDiesel
                val costoEcoPaism2 = ecoPaism2 * precioEcoPais
                val costoSuperm2 = superGasm2 * precioSuper

                val costoDiesel6 = costoDieselm1 +costoDieselm2
                val costoEcoPais6 = costoEcoPaism1 + costoEcoPaism2
                val costoSuper6 = costoSuperm1 + costoSuperm2

                resultadosFinales["Diesel isla#6"] = costoDiesel6
                resultadosFinales["Eco País isla#6"] = costoEcoPais6
                resultadosFinales["Super isla#6"] = costoSuper6

                val diesel5m1 = resultados[6]
                val ecoPais5m1 = resultados[7]
                val superGas5m1 = resultados[8]
                val diesel5m2 = resultados[9]
                val ecoPais5m2 = resultados[10]
                val superGas5m2 = resultados[11]

                val costoDiesel5m1 = diesel5m1 * precioDiesel
                val costoEcoPais5m1 = ecoPais5m1 * precioEcoPais
                val costoSuper5m1 = superGas5m1 * precioSuper
                val costoDiesel5m2 = diesel5m2 * precioDiesel
                val costoEcoPais5m2 = ecoPais5m2 * precioEcoPais
                val costoSuper5m2 = superGas5m2 * precioSuper

                val costoDiesel5 = costoDiesel5m1 + costoDiesel5m2
                val costoEcoPais5 = costoEcoPais5m1 + costoEcoPais5m2
                val costoSuper5 = costoSuper5m1 + costoSuper5m2
                resultadosFinales["Diesel isla#5"] = costoDiesel5
                resultadosFinales["Eco País isla#5"] = costoEcoPais5
                resultadosFinales["Super isla#5"] = costoSuper5

                val totalVentas6 = costoDiesel6 + costoEcoPais6 + costoSuper6
                val totalVentas5 = costoDiesel5 + costoEcoPais5 + costoSuper5
                val totalVentas = totalVentas6 + totalVentas5
                resultadosFinales["Total de Ventas isla #5 y #6"] = totalVentas

                totalBilletesYMonedas5y6 = calcularTotalBilletesYMonedas()

                // Crear la cadena finales para los resultados finales como un número.
                val totalResumen = totalEntradas5y6 + totalBilletesYMonedas5y6
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
            totalBilletesYMonedas5y6 = calcularTotalBilletesYMonedas()

            if (resultadosFinales.isNotEmpty() && resultados.size >= 12  &&
                resultadosFinales.containsKey("Diesel isla#6") &&
                resultadosFinales.containsKey("Eco País isla#6") &&
                resultadosFinales.containsKey("Super isla#6") &&
                resultadosFinales.containsKey("Diesel isla#5") &&
                resultadosFinales.containsKey("Eco País isla#5") &&
                resultadosFinales.containsKey("Super isla#5")  ) {
                val diesel6 = resultados[0] + resultados[3]
                val ecoPais6 = resultados[1] + resultados[4]
                val superGas6 = resultados[2] + resultados[5]

                val diesel5 = resultados[6] + resultados[9]
                val ecoPais5 = resultados[7] + resultados[10]
                val superGas5 = resultados[8] + resultados[11]

                val detallesFinales = """
           
             Diesel isla #5 : = ${String.format("%.2f", resultados[6])} + ${String.format("%.2f", resultados[9])}   = ${String.format("%.2f", diesel6)}
            Resultado * Diesel: ${String.format("%.2f", diesel6)} × ${BigDecimal(inputPrecioDiesel.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Diesel isla#6"]!!)}
            Dinero en Diesel isla #5: $${String.format("%.2f", resultadosFinales["Diesel isla#6"]!!)}
            
            Eco País isla #5: = ${String.format("%.2f", resultados[7])} + ${String.format("%.2f", resultados[10])} = ${String.format("%.2f", ecoPais6)}
            Resultado * Eco País: ${String.format("%.2f", ecoPais6)} × ${BigDecimal(inputPrecioEcoPais.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Eco País isla#6"]!!)}
            Dinero en Eco País isla #5: $${String.format("%.2f", resultadosFinales["Eco País isla#6"]!!)} 
             
            Super isla #5:  = ${String.format("%.2f", resultados[8])} + ${String.format("%.2f", resultados[11])} = ${String.format("%.2f", superGas6)}
            Resultado * Super: ${String.format("%.2f", superGas6)} × ${BigDecimal(inputPrecioSuper.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Super isla#6"]!!)}
            Dinero en Super isla #5: $${String.format("%.2f",resultadosFinales["Super isla#6"]!!)}
            
            Diesel isla #6 : = ${String.format("%.2f", resultados[0])} + ${String.format("%.2f", resultados[3])}   = ${String.format("%.2f", diesel5)}
            Resultado * Diesel: ${String.format("%.2f", diesel5)} × ${BigDecimal(inputPrecioDiesel.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Diesel isla#5"]!!)}
            Dinero en Diesel isla #6: $${String.format("%.2f", resultadosFinales["Diesel isla#5"]!!)}

            Eco País isla #6: = ${String.format("%.2f", resultados[1])} + ${String.format("%.2f", resultados[4])} = ${String.format("%.2f", ecoPais5)}
            Resultado * Eco País: ${String.format("%.2f", ecoPais5)} × ${BigDecimal(inputPrecioEcoPais.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Eco País isla#5"]!!)}
            Dinero en Eco País isla #6: $${String.format("%.2f", resultadosFinales["Eco País isla#5"]!!)}

            Super isla #6:  = ${String.format("%.2f", resultados[2])} + ${String.format("%.2f", resultados[5])} = ${String.format("%.2f", superGas5)}
            Resultado * Super: ${String.format("%.2f", superGas5)} × ${BigDecimal(inputPrecioSuper.text.toString()).setScale(2, RoundingMode.FLOOR).toPlainString()}= ${String.format("%.2f", resultadosFinales["Super isla#5"]!!)}
            Dinero en Super isla #6: $${String.format("%.2f", resultadosFinales["Super isla#5"]!!)}
            
          
 
            Total de Ventas: $${String.format("%.2f", resultadosFinales["Total de Ventas isla #5 y #6"]!!)}
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
                        "$${String.format("%.2f", totalEntradas5y6)}"

                val resumenTotalBilletesYMonedas = "Total en Billetes y Monedas: $${String.format("%.2f", totalBilletesYMonedas5y6)}"

                val totalResumen = totalEntradas5y6 + totalBilletesYMonedas5y6
                val finales = "Resultados finales: $${String.format("%.2f", totalResumen)}"

                txtDetalles.text = operaciones.joinToString("\n") + "\n\n" + detallesFinales + "\n\n" + resumenTotalEntradas + "\n\n" + resumenTotalBilletesYMonedas + "\n\n" + finales
                txtDetalles.visibility = View.VISIBLE
            } else {
                txtDetalles.text = "No hay resultados disponibles. Por favor, calcule los resultados primero."
                txtDetalles.visibility = View.VISIBLE
            }
        }
    }
    // Método para guardar los valores en SharedPreferences
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
        inflater.inflate(R.menu.menu_settings3, popupMenu.menu)

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
