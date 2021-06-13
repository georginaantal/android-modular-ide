package hu.bme.aut.onlab.activities

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.onlab.R
import hu.bme.aut.onlab.activityBlocks.*
import kotlinx.android.synthetic.main.activity_welcome.*


class WelcomeActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_welcome)

        val context = Context()
        context.setValue("", "")

        val list = mutableListOf(
            "All at once",
            "Pick start and end",
            "Iterate through array block",
            "Fork and sum two numbers",
            "String concatenation and string compare"
        )

        val listFiles = mutableListOf(
            "data.txt",
            "data.txt",
            "iterate_array_block.txt",
            "fork_and_sum.txt",
            "string_cat_and_compare.txt"
        )

        val adapter: ArrayAdapter<String> = ArrayAdapter(
            this,
                R.layout.listview_text,
            list
        )
        listView.adapter = adapter

        interaction_button.setOnClickListener(){
            val intent = Intent(this, InteractionActivity::class.java)
            startActivity(intent)
        }

        listView.setOnItemClickListener { parent, view, position, id ->
            if(position == 1){
                val intent = Intent(this, StartAndFinishActivity::class.java).apply {
                    putExtra("EXTRA_FILE", listFiles[position])
                    putExtra("EXTRA_CONTEXT", context.getMap())
                }
                startActivity(intent)
            }
            else {
                val intent = Intent(this, BlockActivity::class.java).apply {
                    putExtra("EXTRA_START_FINISH", "no")
                    putExtra("EXTRA_FILE", listFiles[position])
                    putExtra("EXTRA_CONTEXT", context.getMap())
                }
                startActivity(intent)
            }
        }
    }
}