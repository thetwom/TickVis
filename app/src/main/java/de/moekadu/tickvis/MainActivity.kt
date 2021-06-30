package de.moekadu.tickvis

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.text.InputType
import android.widget.Button
import android.widget.EditText
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AlertDialog

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val button = findViewById<Button>(R.id.button)
        val vis = findViewById<TickVisualizer>(R.id.vis)
        val text = findViewById<TextView>(R.id.bpm_text)

        text.text = "${vis.bpm} bpm"

        text.setOnClickListener {
            val builder = AlertDialog.Builder(this).apply {
                val editText = EditText(this@MainActivity)
                editText.inputType = InputType.TYPE_CLASS_NUMBER or InputType.TYPE_NUMBER_FLAG_DECIMAL
                val pad = 10
                editText.setPadding(pad, pad, pad, pad)

                setTitle("new speed in bpm")
                setPositiveButton("done") { _, _ ->
                    val newBpmText = editText.text.toString()
                    val newBpm = newBpmText.toFloatOrNull()
                    if (newBpm == null) {
                        Toast.makeText(
                            this@MainActivity, "invalid input",
                            Toast.LENGTH_LONG
                        ).show()
                    } else {
                        vis.bpm = newBpm
                        text.text = "${vis.bpm} bpm"
                    }

                }
                setNegativeButton("abort") { dialog, _ ->
                    dialog?.cancel()
                }
                setView(editText)
            }
            val dialog = builder.create()
            dialog.show()
        }

        button.setOnClickListener {
            if (vis.isRunning)
                vis.stop()
            else
                vis.start()
        }
    }
}