import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.google.gson.JsonObject
import java.io.FileWriter
import java.io.IOException
import java.util.*

class Virksomhed(val navn: String, val address: String, val cvr: String, val status: String, val type: String) {

    companion object {
        val virksomhed = ArrayList<Virksomhed>()
        fun add(navn: String, address: String, cvr: String, status: String, type: String) {
            virksomhed.add(Virksomhed(navn, address, cvr, status, type))
        }

        fun save() {
            val gson = GsonBuilder().setPrettyPrinting().create()
            val jsonElement = gson.toJsonTree(virksomhed)
            try {
                FileWriter("./virksomhed.json").use { fileWriter -> fileWriter.write(gson.toJson(jsonElement)) }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }

        fun parse(): JSONLoader {
            return object : JSONLoader() {
                override fun load(reader: JsonObject, builder: Gson) {
                    val navn = reader["navn"].asString
                    val address = reader["address"].asString
                    val cvr = reader["cvr"].asString
                    val status = reader["status"].asString
                    val type = reader["type"].asString
                    add(navn, address, cvr, status, type)
                }

                override fun filePath(): String {
                    return "./virksomhed.json"
                }
            }
        }
    }

}