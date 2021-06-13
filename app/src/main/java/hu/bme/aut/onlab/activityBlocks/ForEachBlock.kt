package hu.bme.aut.onlab.activityBlocks

import android.widget.TextView

class ForEachBlock : Block {
    private var array: ArrayList<String>? = null
    private var actual: String? = null
    private var iterator: String? = null
    private var firstOfLoopID: String? = null
    private var i: Int = 0

    internal constructor() : super() {}
    internal constructor(source: ForEachBlock?) : super(source!!) {}

    override fun clone(): Block {
        return ForEachBlock(this)
    }

    override fun initWith(components: Array<String>){ //components example: ForEachBlock;0;1,4,3;x;i;1;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        val array: ArrayList<String> = components[2].split(",".toRegex()).toTypedArray().toCollection(ArrayList<String>())
        this.setArray(array)
        this.setActual(components[3])
        this.setIterator(components[4])
        this.setFirstOfLoopID(components[5])
        this.setNextBlockId(components[6].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "ForEachBlock"
        dataText.text = ""

        return if (i < array?.size as Int) {
            context!!.setValue(actual, array!![i])
            context.setValue(iterator, i.toString())
            i++
            blocks[firstOfLoopID?.toInt()]
        } else {
            i = 0
            return this.getBlocks(this.getNextBlockId())
        }
    }

    //Setters and getters
    fun setActual(actual: String?){
        this.actual = actual
    }

    fun getActual(): String? {
        return this.actual
    }

    fun setIterator(iterator: String){
        this.iterator = iterator
    }

    fun getIterator(): String? {
        return this.iterator
    }

    fun setFirstOfLoopID(loopID: String){
        this.firstOfLoopID = loopID
    }

    fun getFirstOfLoopID(): String? {
        return this.firstOfLoopID
    }

    fun setArray(array: ArrayList<String>?){
        this.array = array
    }

    fun getArray(): ArrayList<String>? {
        return this.array
    }
}