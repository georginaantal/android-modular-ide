package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView

class ReadBlock : Block {
    private var variable: String? = null
    internal constructor() : super() {}
    internal constructor(source: ReadBlock?) : super(source!!) {}

    override fun clone(): Block {
        return ReadBlock(this)
    }

    override fun initWith(components: Array<String>) { //ReadBlock;1;value;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        variable = components[2]
        this.setNextBlockId(components[3].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        context!!.setValue(variable, dataText.text.toString())
        return blocks[this.getNextBlockId()];
    }
}