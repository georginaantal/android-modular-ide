package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView

class SumBlock : DoubleDataBlock {

    private var sum: Int? = null

    internal constructor() : super() {}
    internal constructor(source: SumBlock?) : super(source!!) {}

    override fun clone(): Block {
        return SumBlock(this)
    }

    override fun initWith(components: Array<String>) { //SumBlock;1;sum1;value1;value2;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setVariable(components[2])
        this.setData1(components[3])
        this.setData2(components[4])
        this.setNextBlockId(components[5].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "SumBlock"
        val toWrite: String = this.getData1() + ", " + this.getData2()
        dataText.text = toWrite
        this.setSum(this.getData1()!!.toInt() + this.getData2()!!.toInt())
        context!!.setValue(this.getVariable(), getSum().toString())
        return this.getBlocks(this.getNextBlockId())
    }

    fun setSum(sum: Int?) {
        this.sum = sum
    }

    fun getSum(): Int? {
        return this.sum
    }
}