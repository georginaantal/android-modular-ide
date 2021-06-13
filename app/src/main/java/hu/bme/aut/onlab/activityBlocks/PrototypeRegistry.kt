package hu.bme.aut.onlab.activityBlocks

class PrototypeRegistry {
    var blocks: HashMap<String, Block> = HashMap()

    fun addBlock(blockName: String, block: Block) {
        blocks[blockName] = block
    }

    fun getByString(blockName: String): Block? {
        return blocks[blockName]
    }

    fun deleteBlock(blockName: String) {
        blocks.remove(blockName)
    }

}
