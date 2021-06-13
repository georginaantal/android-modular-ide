package hu.bme.aut.onlab.interactionBlocks

import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.RadioButton
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity

class RadioButtonBlock : InteractionBlock {
    private var radioButton: RadioButton? = null

    internal constructor(id: Int) : super(id) { }
    internal constructor(source: InteractionBlock) : super(source) {}

    override fun clone(): InteractionBlock {
        return RadioButtonBlock(this)
    }

    override fun initWith(components: Array<String>){ //RadioButtonBlock;0;1;Hello;1
        this.setId(components[1].toInt())
        InteractionBlock.blocks[components[1].toInt()] = this
        this.setOrder(components[2].toInt())
        this.setText(components[3])
        this.setNextBlockId(components[4].toInt())
    }

    override fun create(context: AppCompatActivity) {
        this.setRadioButton(RadioButton(context))
        radioButton!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        radioButton!!.text = this.getText()
        radioButton!!.setOnClickListener {
            Toast.makeText(context, "IM A RADIOBUTTON", Toast.LENGTH_SHORT).show()
        }
    }

    open fun addToLayout(layout: LinearLayout){
        layout.addView(this.getItem())
    }

    open fun setRadioButton(radioButton: RadioButton){
        this.radioButton = radioButton
    }

    override fun getItem() : RadioButton? {
        return this.radioButton
    }
}