package hu.bme.aut.onlab.activities

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.onlab.R
import kotlinx.android.synthetic.main.activity_start.*

class StartActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_start)

        start_button.setOnClickListener(){
            val intent = Intent(this, WelcomeActivity::class.java)
            startActivity(intent)
        }
    }
}