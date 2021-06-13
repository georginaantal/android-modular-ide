package hu.bme.aut.onlab.activityBlocks
import android.widget.TextView


open class IfBlock : Block {
    private var trueID: Int? = null
    private var falseID: Int? = null
    private var exprID: String? = null
    private var expr: String? = null

    internal constructor() : super() {}
    internal constructor(source: IfBlock?) : super(source!!) {}

    override fun clone(): Block {
        return IfBlock(this)
    }

    override fun initWith(components: Array<String>){ //components example: IfBlock;0;d;a < b;1;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setExprID(components[2])
        this.setExpr(components[3])
        this.setTrueID(components[4].toInt())
        this.setFalseID(components[5].toInt())
        this.setNextBlockId(components[4].toInt())
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "IfBlock"
        dataText.text = this.getExpr()

        context!!.setValue(this.getExprID(), this.getExpr())

        val components: Array<String> = ("EvaluateBlock;0;0;" + this.getExprID() + ";" + this.getExpr() + ";0").split(";".toRegex()).toTypedArray()
        var evaluateBlock: EvaluateBlock = EvaluateBlock()
        evaluateBlock.initWith(components)
        evaluateBlock.evaluate(context)

        val result = context.getValue(evaluateBlock.getExpressionID())

        return if(result == 1.0.toString()){
            this.getBlocks(trueID)
        }
        else
            this.getBlocks(falseID)
    }

    //Setters and getters
    open fun setTrueID(trueID: Int){
        this.trueID = trueID
    }

    open fun getTrueID() : Int? {
        return this.trueID
    }

    open fun setFalseID(falseID: Int){
        this.falseID = falseID
    }

    open fun getFalseID() : Int? {
        return this.falseID
    }

    open fun setExprID(exprID: String){
        this.exprID = exprID
    }

    open fun getExprID() : String? {
        return this.exprID
    }

    open fun setExpr(expr: String) {
        this.expr = expr
    }

    open fun getExpr() : String? {
        return this.expr
    }
}