package hu.bme.aut.onlab.interactionBlocks

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.Drawable
import android.graphics.drawable.GradientDrawable
import android.util.TypedValue
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hu.bme.aut.onlab.R

class TextViewBlock : InteractionBlock{
    private var textView: TextView? = null
    private var textSize: Float = 40f

    internal constructor(id: Int) : super(id) { }
    internal constructor(source: InteractionBlock) : super(source) {}

    override fun clone(): InteractionBlock {
        return TextViewBlock(this)
    }

    override fun initWith(components: Array<String>){ //TextViewBlock;0;1;Hello;70;1
        this.setId(components[1].toInt())
        blocks[components[1].toInt()] = this
        this.setOrder(components[2].toInt())
        this.setText(components[3])
        this.setTextSize(components[4].toFloat())
        this.setNextBlockId(components[5].toInt())
    }

    override fun create(context: AppCompatActivity) {
        textView = TextView(context)
        textView!!.background = roundedCornersDrawable(
                3.dpToPixels(context), // border width in pixels
                ContextCompat.getColor(context, R.color.colorMy5), // border color
                3.dpToPixels(context).toFloat(), // corners radius
                ContextCompat.getColor(context, R.color.colorMyPrimary)
        )
        textView!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        textView!!.setPadding(30, 30, 30, 30)
        textView!!.text = this.getText()
        textView!!.setTextColor(ContextCompat.getColor(context, R.color.colorMy6))
        textView!!.setTextSize(TypedValue.COMPLEX_UNIT_SP, this.getTextSize())
        textView!!.setOnClickListener {
            Toast.makeText(context, "IM A TEXTVIEW", Toast.LENGTH_SHORT).show()
        }
    }

    // extension function to get rounded corners border
    fun roundedCornersDrawable(
            borderWidth: Int = 10, // border width in pixels
            borderColor: Int = Color.BLACK, // border color
            cornerRadius: Float = 25F, // corner radius in pixels
            bgColor: Int = Color.TRANSPARENT // view background color
    ): Drawable {
        return GradientDrawable().apply {
            shape = GradientDrawable.RECTANGLE
            setStroke(borderWidth, borderColor)
            setColor(bgColor)
            // make it rounded corners
            this.cornerRadius = cornerRadius
        }
    }

    // extension function to convert dp to equivalent pixels
    fun Int.dpToPixels(context: Context):Int = TypedValue.applyDimension(
            TypedValue.COMPLEX_UNIT_DIP, this.toFloat(), context.resources.displayMetrics
    ).toInt()

    open fun setTextSize(size: Float) {
        this.textSize = size
    }

    open fun getTextSize() : Float {
        return this.textSize
    }

    open fun addToLayout(layout: LinearLayout){
        layout.addView(this.getItem())
    }

    open fun setTextView(textView: TextView){
        this.textView = textView
    }

    override fun getItem() : TextView? {
        return this.textView
    }
}