package hu.bme.aut.onlab.interactionBlocks

import android.view.ViewGroup
import android.widget.*
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import hu.bme.aut.onlab.R

class EditTextBlock : InteractionBlock {
    private var editText: EditText? = null
    private var button: ButtonBlock? = null
    companion object {
        private var dataInput: HashMap<Int?, String> = HashMap() //stores the EditTextBlock's id and the input in it
    }

    internal constructor(id: Int) : super(id) { }
    internal constructor(source: EditTextBlock) : super(source) {}

    override fun clone(): InteractionBlock {
        return EditTextBlock(this)
    }

    override fun initWith(components: Array<String>){ //EditTextBlock;1;0;0;Show this text;2
        this.setId(components[1].toInt())
        blocks[components[1].toInt()] = this
        this.setOrder(components[2].toInt())
        this.setButton(blocks[components[3].toInt()] as ButtonBlock)
        if(components[4] != "")
            this.setText(components[4])
        this.setNextBlockId(components[5].toInt())
    }

    override fun create(context: AppCompatActivity) {
        editText = EditText(context)
        editText!!.hint = this.getText()
        editText!!.layoutParams = LinearLayout.LayoutParams(LinearLayout.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
        editText!!.setTextColor(ContextCompat.getColor(context, R.color.colorMyPrimary))
        editText!!.setHintTextColor(ContextCompat.getColor(context, R.color.colorMyPrimary))
        editText!!.setOnClickListener(){
            if(this.getButton() == null || button!!.getItem() == null){
                Toast.makeText(context, "NO BUTTON ATTACHED", Toast.LENGTH_LONG).show()
            }
        }
        if(this.getButton() != null && button!!.getItem() != null){
            button!!.getItem()!!.setOnClickListener {
                if(editText!!.text.isEmpty()){
                    Toast.makeText(context, "INPUT IS EMPTY", Toast.LENGTH_LONG).show()
                }
                else{
                    this.setData(this.getId(), editText!!.text.toString())
                    Toast.makeText(context, "INPUT ADDED", Toast.LENGTH_LONG).show()
                }
            }
        }
    }

    open fun addToLayout(layout: LinearLayout){
        layout.addView(this.getItem())
    }

    open fun setEditText(editText: EditText){
        this.editText = editText
    }

    override fun getItem() : EditText? {
        return this.editText
    }

    open fun setButton(buttonBlock: ButtonBlock?){
        this.button = buttonBlock
    }

    open fun getButton() : ButtonBlock? {
        return this.button
    }

    open fun setData(id: Int?, input: String) {
        dataInput[id] = input
    }

    open fun getData(id: Int) : String? {
        return dataInput[id]
    }

}