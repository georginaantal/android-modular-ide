package hu.bme.aut.onlab.activityBlocks

interface Prototype : java.io.Serializable{
    fun clone(): Prototype
}