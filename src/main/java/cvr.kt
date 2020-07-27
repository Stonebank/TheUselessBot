
import com.google.common.base.Stopwatch
import org.jsoup.Jsoup
import java.util.*
import java.util.concurrent.TimeUnit

private var virksomheder = 0

fun main() {
    loadCompany(fetchdata = true)
}

fun loadCompany(fetchdata: Boolean) {

    when (fetchdata) {

        true -> {

            val stopwatch = Stopwatch.createStarted()
            println("Tilføjer virksomheder, vent venligst...")

            for (page in 1..20) {

                val connection = Jsoup.connect("https://datacvr.virk.dk/data/visninger?page=$page&soeg=&oprettet=null&ophoert=null&branche=&type=undefined&language=da")
                        .timeout(TimeUnit.SECONDS.toMillis(10).toInt()).userAgent("Mozilla/5.0").get()
                println("Fanger data fra side $page...")
                Thread.sleep(TimeUnit.SECONDS.toMillis(10))

                for (i in 0..9) {
                    val navn = connection.select("h2.name")[i].text()
                    val address = connection.select("p.address")[i].text()
                    val cvr = connection.select("div.cvr")[i].text().replace("[^\\d.]".toRegex(), "")
                    val status = connection.select("div.status")[i].text().replace("Status ", "")
                    val type = connection.select("div.type")[i].text().replace("Virksomhedstype ", "")
                    println("Tilføjet fra side $page: $navn - $address - $cvr - $status - $type")
                    Virksomhed.add(navn, address, cvr, status, type)
                    virksomheder++
                }

            }

            println("Antal virksomheder tilføjet: $virksomheder, det tog ${stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)} MS")

            println("Gemmer...")
            Virksomhed.save()
            println("Data er nu gemt.")
        }

        else -> {

            println("Indlæser virksomheder...")

            Virksomhed.parse().load()

            println("Indlæst alle virksomheder samt data fra virksomhed.json...")

            for (virksomhed in Virksomhed.virksomhed) {
                println("${virksomhed.navn} - ${virksomhed.address} - ${virksomhed.cvr} - ${virksomhed.status} - ${virksomhed.type}")
                println("------------------------------------------------------------------------------------------------------------")
            }

            println("\nØnsker du at søge? Du kan søge for navn, addresse, cvr, status eller type.")

            val scanner = Scanner(System.`in`)
            val input = scanner.next()
            var result = 0

            for (virksomhed in Virksomhed.virksomhed) {

                if (!virksomhed.navn.toLowerCase().contains(input.toLowerCase()))
                    continue

                result++

                println("${virksomhed.navn} - ${virksomhed.address} - ${virksomhed.cvr} - ${virksomhed.status} - ${virksomhed.type}")
                println("------------------------------------------------------------------------------------------------------------")

            }

            println("$result/${Virksomhed.virksomhed.size} virksomheder havde $input i navnet")

        }

    }

}