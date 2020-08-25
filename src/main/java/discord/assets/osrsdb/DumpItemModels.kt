package discord.assets.osrsdb

import com.google.common.base.Stopwatch
import discord.utils.Utils
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

val averageMills = arrayOfNulls<Long>(Utils.getItemDefinitionsSize())

fun main() {

    var itemSaved = 0
    val size = Utils.getItemDefinitionsSize()

    val stopwatch = Stopwatch.createStarted()
    println("Starting the dump...")

    for (id in 0..size) {

        val localStopwatch = Stopwatch.createStarted()

        if (File("./data/discord/assets/image/items/$id.png").exists()) {
            println("Skipping item $id since it already exists")
            localStopwatch.stop()
            continue
        }

        try {
            URL("https://www.osrsbox.com/osrsbox-db/items-icons/$id.png").openStream().use { `in` -> Files.copy(`in`, Paths.get("./data/discord/assets/image/items/$id.png")) }
            println("Found ID $id, saving the item model to folder")
            itemSaved++
            averageMills[id] = localStopwatch.stop().elapsed(TimeUnit.MILLISECONDS)
        } catch (e : IOException) {
            println("Could not find image for $id")
            continue
        }

    }

    println("A total of $itemSaved items has been saved in ${stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)} MS")
    println("Average fetching time: ${getSum()/averageMills.size} milliseconds")

}

fun getSum() : Long {
    var sum: Long = 0
    for (averageMill in averageMills) {
        if (averageMill != null) {
            sum += averageMill
        }
    }
    return sum
}