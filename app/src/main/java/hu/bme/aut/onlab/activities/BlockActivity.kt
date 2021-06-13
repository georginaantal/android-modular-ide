package hu.bme.aut.onlab.activities

import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import hu.bme.aut.onlab.R
import hu.bme.aut.onlab.activityBlocks.*
import kotlinx.android.synthetic.main.activity_main.*
import java.io.InputStream

class BlockActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_block_layout)

        var startBlock : Int
        var endBlock : Int

        val intent = intent
        //val blockMap = intent.getSerializableExtra("EXTRA_BLOCKMAP") as HashMap<Int, Block>
        val isStartFinish  = intent.getStringExtra("EXTRA_START_FINISH")
        if(isStartFinish == "yes"){
            startBlock  = intent.getStringExtra("EXTRA_START")!!.toInt()
            endBlock  = intent.getStringExtra("EXTRA_END")!!.toInt()
        }
        else {
            startBlock = 0
            endBlock = 0
        }
        val contextMap  = intent.getSerializableExtra("EXTRA_CONTEXT") as HashMap<String, String>
        val file = intent.getStringExtra("EXTRA_FILE") as String

        val registry = PrototypeRegistry()
        registry.addBlock("ArrayBlock", ArrayBlock())
        registry.addBlock("EvaluateBlock", EvaluateBlock())
        registry.addBlock("ForEachBlock", ForEachBlock())
        registry.addBlock("IfBlock", IfBlock())
        registry.addBlock("ReadBlock", ReadBlock())
        registry.addBlock("StringCatBlock", StringCatBlock())
        registry.addBlock("StringCompareBlock", StringCompareBlock())
        registry.addBlock("SumBlock", SumBlock())
        registry.addBlock("WriteBlock", WriteBlock())

        val blockMap: HashMap<Int, Block> = HashMap()
        val inputStream: InputStream = assets.open(file)
        val lineList = ArrayList<String>()
        inputStream.bufferedReader().useLines { lines -> lines.forEach { lineList.add(it) } }

        lineList.forEach() {
            val line: String = it
            val components = line.split(";".toRegex()).toTypedArray()
            val newBlock: Block = registry.getByString(components[0])!!.clone()

            newBlock.initWith(components)
            if(isStartFinish == "no")
                endBlock = newBlock.getId()!!.toInt()
            blockMap[components[1].toInt()] = newBlock
        }

        val context = hu.bme.aut.onlab.activityBlocks.Context()
        context.setMap(contextMap)
        var actMain: Block? = blockMap[startBlock]
        var end: Boolean = false
        button.setOnClickListener {
            if (!end) {
                button.text = "NEXT"
                var next = actMain!!.execute(context, textView, textData)
                actMain = next
                if(actMain!!.getNextBlockId()!! < 0 || actMain!!.getId() == endBlock){
                    actMain!!.execute(context, textView, textData)
                    end = true
                }
            }

            else {
                Toast.makeText(this, "There are no more blocks", Toast.LENGTH_SHORT).show()
                button.text = "END"
            }
        }
    }
}