package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView

class StringCatBlock : DoubleDataBlock {

    internal constructor() : super() {}
    internal constructor(source: StringCatBlock?) : super(source!!) {}

    override fun clone(): Block {
        return StringCatBlock(this)
    }

    override fun initWith(components: Array<String>) { //StringCatBlock;1;stringcat1;value1;value2;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setVariable(components[2])
        this.setData1(components[3])
        this.setData2(components[4])
        this.setNextBlockId(components[5].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "StringCatBlock"
        val concatenated = this.getData1() + ", " + this.getData2()
        dataText.text = concatenated
        val result = this.getData1().toString() + this.getData2().toString()
        context!!.setValue(this.getVariable(), result)
        return this.getBlocks(this.getNextBlockId())
    }
}
