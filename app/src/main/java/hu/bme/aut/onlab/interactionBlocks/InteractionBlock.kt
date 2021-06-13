package hu.bme.aut.onlab.interactionBlocks

import androidx.appcompat.app.AppCompatActivity

open class InteractionBlock {
    private var item: android.view.View? = null
    private var id: Int? = null
    private var nextBlockId: Int? = null
    private var order: Int = 0
    private var text: String = "Enter something"
    companion object { //there is no static in kotlin
        var blocks: HashMap<Int, InteractionBlock> = HashMap() //companion object works only if it is not protected
    }

    internal constructor(id: Int) { this.setId(id) }
    internal constructor(source: InteractionBlock) {
        this.setId(source.getId())
        this.setNextBlockId(source.getNextBlockId());
    }

    open fun clone(): InteractionBlock {
        return InteractionBlock(this)
    }

    open fun initWith(components: Array<String>) {}

    open fun create(context: AppCompatActivity) {}

    open fun getItem() : android.view.View? {
        return this.item
    }

    open fun setOrder(order: Int) {
        this.order = order
    }

    open fun getOrder() : Int {
        return this.order
    }

    fun setNextBlockId(nextBlockId: Int?) {
        this.nextBlockId = nextBlockId
    }

    fun getNextBlockId(): Int? {
        return this.nextBlockId
    }

    fun setId(id: Int?){
        this.id = id
    }

    fun getId() : Int? {
        return this.id
    }

    open fun setText(text: String) {
        this.text = text
    }

    open fun getText() : String {
        return this.text
    }
}