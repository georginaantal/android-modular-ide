package hu.bme.aut.onlab.activityBlocks

class ArrayBlock: Block {
    private var  array: ArrayList<String>? = null

    internal constructor() : super() {}
    internal constructor(source: ArrayBlock?) : super(source!!) {}

    override fun clone(): Block {
        return ArrayBlock(this)
    }

    open fun setArray(array: ArrayList<String>){
        this.array = array
    }

    open fun getArray() : ArrayList<String>? {
        return this.array
    }
}