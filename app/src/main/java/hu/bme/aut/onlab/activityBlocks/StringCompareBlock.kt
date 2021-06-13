package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView

class StringCompareBlock : DoubleDataBlock {
    internal constructor() : super() {}
    internal constructor(source: StringCompareBlock?) : super(source!!) {}

    override fun clone(): Block {
        return StringCompareBlock(this)
    }

    override fun initWith(components: Array<String>) { //StringCompareBlock;1;stringcompare1;value1;value2;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setVariable(components[2])
        this.setData1(components[3])
        this.setData2(components[4])
        this.setNextBlockId(components[5].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "StringCompareBlock"
        dataText.text = this.getData1() + ", " + this.getData2()
        if (this.getData1() == this.getData2()) {
            context!!.setValue(this.getVariable(), "They are the same strings.")
        } else context!!.setValue(this.getVariable(), "They are not the same strings.")
        return this.getBlocks(this.getNextBlockId())
    }
}
