package hu.bme.aut.onlab.activityBlocks

public class Context {
    private var context: HashMap<String, String> = HashMap()

    fun setValue(k: String?, v: String?) {
        context[k!!] = v!!
    }

    fun getValue(k: String?): String? {
        return context[k]
    }

    fun setMap(map: HashMap<String, String>){
        this.context = map
    }

    fun getMap(): HashMap<String, String>? {
        return this.context
    }
}