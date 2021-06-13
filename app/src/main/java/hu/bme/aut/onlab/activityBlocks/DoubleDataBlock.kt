package hu.bme.aut.onlab.activityBlocks

abstract class DoubleDataBlock : Block {
    private var variable: String? = null
    private var data1: String? = null
    private var data2: String? = null

    internal constructor() : super() {}
    internal constructor(source: DoubleDataBlock?) : super(source!!) {}

    override fun initWith(components: Array<String>) { //SumBlock;1;value1;value2;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setData1(components[2])
        this.setData2(components[3])
        this.setNextBlockId(components[4].toInt())
    }

    //Setters and getters
    open fun setData1(data: String?){
        this.data1 = data
    }

    open fun getData1(): String?{
        return this.data1
    }

    open fun setData2(data: String?){
        this.data2 = data
    }

    open fun getData2(): String?{
        return this.data2
    }

    fun setVariable(variable: String?) {
        this.variable = variable
    }

    fun getVariable(): String? {
        return variable
    }
}