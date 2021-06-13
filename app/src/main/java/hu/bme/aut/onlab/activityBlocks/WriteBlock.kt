package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView

open class WriteBlock : Block {
    private var variable: String? = ""
    private var text: String? = null
    internal constructor() : super() {}
    internal constructor(source: WriteBlock?) : super(source!!) {}

    override fun clone(): Block {
        return WriteBlock(this)
    }

    override fun initWith(components: Array<String>) { //WriteBlock;2;i;Iterator: ;0
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setVariable(components[2])
        this.setText(components[3])
        this.setNextBlockId(components[4].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "WriteBlock"
        val output: String = this.text + context!!.getValue(variable)
        dataText.text = output
        return this.getBlocks(this.getNextBlockId())
    }



    //Setters and getters
    open fun setVariable(variable: String?){
        this.variable = variable
    }

    open fun getVariable(): String? {
        return this.variable
    }

    open fun setText(text: String?){
        this.text = text
    }

    open fun getText(): String?{
        return this.text
    }
}

