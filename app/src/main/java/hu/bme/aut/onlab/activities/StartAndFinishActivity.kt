package hu.bme.aut.onlab.activities

import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.onlab.R
import kotlinx.android.synthetic.main.activity_start_and_finish_layout.*

class StartAndFinishActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start_and_finish_layout)

        var passStart : String
        var passEnd : String

        val contextMap  = intent.getSerializableExtra("EXTRA_CONTEXT") as HashMap<String, String>
        val file = intent.getStringExtra("EXTRA_FILE") as String

        buttonOk.setOnClickListener(){
            if((editText1.text.toString() != "") && (editText2.text.toString() != "")){
                try {
                    val d1: Double = editText1.text.toString().toDouble()
                    val d2: Double = editText2.text.toString().toDouble()
                } catch (nfe: NumberFormatException) {
                    Toast.makeText(this, "Enter a valid ID", Toast.LENGTH_SHORT).show()
                    return@setOnClickListener
                }
                passStart = editText1.text.toString()
                passEnd = editText2.text.toString()
                val intent = Intent(this, BlockActivity::class.java).apply {
                    putExtra("EXTRA_START_FINISH", "yes")
                    putExtra("EXTRA_START", passStart)
                    putExtra("EXTRA_END", passEnd)

                    putExtra("EXTRA_FILE", file)
                    putExtra("EXTRA_CONTEXT", contextMap)
                }
                startActivity(intent)
            }
            else {
                Toast.makeText(this, "Enter both values", Toast.LENGTH_SHORT).show()
            }
        }

    }
}