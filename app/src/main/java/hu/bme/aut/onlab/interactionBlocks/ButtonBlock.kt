package hu.bme.aut.onlab.interactionBlocks

import android.view.ViewGroup
import android.widget.Button
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hu.bme.aut.onlab.R

class ButtonBlock : InteractionBlock {
    private var button: Button? = null

    internal constructor(id: Int) : super(id) { }
    internal constructor(source: InteractionBlock) : super(source) {}

    override fun clone(): InteractionBlock {
        return ButtonBlock(this)
    }

    override fun initWith(components: Array<String>){ //ButtonBlock;0;1;Hello;1
        this.setId(components[1].toInt())
        blocks[components[1].toInt()] = this
        this.setOrder(components[2].toInt())
        this.setText(components[3])
        this.setNextBlockId(components[4].toInt())
    }

    override fun create(context: AppCompatActivity) {
        this.setButton(android.widget.Button(context))
        //button!!.typeface = ResourcesCompat.getFont(context, R.font.my_font)
        button!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        button!!.text = this.getText()
        button!!.setTextColor(ContextCompat.getColor(context, R.color.colorMy6))
        button!!.setBackgroundResource(R.drawable.custom_button)
        button!!.setOnClickListener {
            Toast.makeText(context, "IM A NEW BUTTON", Toast.LENGTH_SHORT).show()
        }
    }

    open fun addToLayout(layout: LinearLayout){
        layout.addView(this.getItem())
    }

    open fun setButton(button: Button){
        this.button = button
    }

    override fun getItem() : Button? {
        return this.button
    }
}