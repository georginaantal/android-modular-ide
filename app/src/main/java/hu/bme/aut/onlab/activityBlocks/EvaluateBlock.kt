package hu.bme.aut.onlab.activityBlocks

import android.widget.TextView
import hu.bme.aut.onlab.Expr

open class EvaluateBlock : Block {
    private var expressionID: String? = null
    private var expression: String = ""

    internal constructor() : super() {}
    internal constructor(source: EvaluateBlock?) : super(source!!) {}

    override fun clone(): Block {
        return EvaluateBlock(this)
    }

    override fun execute(context: Context?, text: TextView, dataText: TextView): Block? {
        text.text = "EvaluateBlock"
        return null
    }

    override fun initWith(components: Array<String>){ //components example: EvaluateBlock;5;f;expression;a < b;3
        blocks[components[1].toInt()] = this
        this.setId(components[1].toInt())
        this.setExpressionID(components[3])
        this.setExpression(components[4])
        this.setNextBlockId(components[5].toInt())
    }

    fun evaluate(context: Context?) {
        val e = Expr.Builder().parse(this.getExpression(), null, null)
        context!!.setValue(this.getExpressionID(), e!!.eval().toString())
    }

    //Setters and getters
    open fun  setExpressionID(expressionID: String?) {
        this.expressionID = expressionID
    }

    open fun getExpressionID() : String? {
        return this.expressionID
    }

    open fun setExpression(expression: String){
        this.expression = expression
    }

    open fun getExpression(): String {
        return this.expression
    }

        /*
        // TODO evaluate expression
        // the ExpressionFactory implementation is de.odysseus.el.ExpressionFactoryImpl
        val factory: ExpressionFactory = ExpressionFactoryImpl()
        // package de.odysseus.el.util provides a ready-to-use subclass of ELContext
        val contextExpr = SimpleContext()
        // map function math:max(int, int) to java.lang.Math.max(int, int)
        contextExpr.setFunction("math", "min", Math::class.java.getMethod("min", Int::class.javaPrimitiveType, Int::class.javaPrimitiveType))
        // map variable foo to 0
        contextExpr.setVariable("foo", factory.createValueExpression(5, Int::class.javaPrimitiveType))
        // parse our expression
        val e: ValueExpression = factory.createValueExpression(contextExpr, "\${math:min(foo,bar)}", Int::class.javaPrimitiveType)
        // set value for top-level property "bar" to 1
        factory.createValueExpression(contextExpr, "\${bar}", Int::class.javaPrimitiveType).setValue(contextExpr, 3)
        // get value for our expression
        text.text =  e.getValue(contextExpr).toString() // (e.getValue(contextExpr)) // --> 1
        // a < b     "\$(a+b)"
        */
}