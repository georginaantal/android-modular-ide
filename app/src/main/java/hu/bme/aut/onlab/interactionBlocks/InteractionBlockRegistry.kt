package hu.bme.aut.onlab.interactionBlocks

class InteractionBlockRegistry {
    var displayBlocks: HashMap<String, InteractionBlock> = HashMap()

    fun addDisplayBlock(type: String, displayBlock: InteractionBlock) {
        displayBlocks[type] = displayBlock
    }

    fun getDisplayByName(type: String): InteractionBlock? {
        return displayBlocks[type]
    }

    fun deleteDisplayBlock(type: String) {
        displayBlocks.remove(type)
    }

}