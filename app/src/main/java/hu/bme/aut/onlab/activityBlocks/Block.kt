package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView


open class Block : Prototype {
    private var id: Int? = null
    private var context: Context? = null
    private var nextBlockId: Int? = null
    companion object { //there is no static in kotlin
        var blocks: HashMap<Int, Block> = HashMap() //companion object works only if it is not protected
    }

    internal constructor() {
    }
    internal constructor(source: Block) {
        this.setContext(source.getContext());
        this.setNextBlockId(source.getNextBlockId());
    }

    override fun clone(): Block {
        return Block(this)
    }

    open fun initWith(components: Array<String>){}

    open fun execute(context: Context?, text: TextView, dataText: TextView): Block? { return null }

    //Setters and getters
    open fun setNextBlockId(nextBlockId: Int?) {
        this.nextBlockId = nextBlockId
    }

    open fun getNextBlockId(): Int? {
        return this.nextBlockId
    }

    open fun getId(): Int? {
        return this.id
    }

    open fun setId(id: Int){
        this.id = id
    }

    open fun setContext(context: Context?) {
        this.context = context
    }

    open fun getContext(): Context? {
        return context
    }

    open fun setBlocks(id: Int, b: Block) {
        blocks[id] = b
    }

    open fun getBlocks(blockID: Int?): Block? {
        return blocks[blockID]
    }
}
