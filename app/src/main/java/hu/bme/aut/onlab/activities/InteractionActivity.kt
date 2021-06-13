package hu.bme.aut.onlab.activities

import android.os.Bundle
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.onlab.R
import hu.bme.aut.onlab.interactionBlocks.InteractionBlockRegistry
import hu.bme.aut.onlab.interactionBlocks.*
import kotlinx.android.synthetic.main.activity_display_layout.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream


class InteractionActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_display_layout)

        val registry = InteractionBlockRegistry()
        registry.addDisplayBlock("ButtonBlock", ButtonBlock(0))
        registry.addDisplayBlock("EditTextBlock", EditTextBlock(0))
        registry.addDisplayBlock("TextViewBlock", TextViewBlock(0))
        registry.addDisplayBlock("RadioButtonBlock", RadioButtonBlock(0))

        val blockMapCreate: HashMap<Int, InteractionBlock> = HashMap()
        val blockMapLayout: HashMap<Int, InteractionBlock> = HashMap()
        val inputStream: InputStream = assets.open("displayData.txt")
        val lineList = ArrayList<String>()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }
        val layout = findViewById<LinearLayout>(R.id.layout)

        lineList.forEach() {
            val line: String = it
            val components = line.split(";".toRegex()).toTypedArray()
            val newBlock: InteractionBlock = registry.getDisplayByName(components[0])!!.clone()

            newBlock.initWith(components)
            blockMapCreate[components[1].toInt()] = newBlock
            blockMapLayout[components[2].toInt()] = newBlock
        }

        Toast.makeText(this@InteractionActivity, "These display blocks are created from a file", Toast.LENGTH_SHORT).show()

        val okButton: InteractionBlock = registry.getDisplayByName("ButtonBlock")!!.clone()
        val arrayOk: Array<String> = ("ButtonBlock;0;0;OK;1").split(";".toRegex()).toTypedArray()
        okButton.initWith(arrayOk)
        blockMapCreate[arrayOk[1].toInt()] = okButton
        blockMapLayout[arrayOk[2].toInt()] = okButton

        for ((key, item) in blockMapCreate) {
            item.create(this)
        }

        for ((key, item) in blockMapLayout) {
            layout.addView(item.getItem())
        }

        /*okButton.getItem()!!.setOnClickListener{
            val intent = Intent(this, BlockActivity::class.java).apply {
            }
            startActivity(intent)
        }*/
    }
}
