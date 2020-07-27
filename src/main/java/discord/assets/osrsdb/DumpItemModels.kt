package discord.assets.osrsdb

import com.google.common.base.Stopwatch
import java.io.File
import java.io.IOException
import java.net.URL
import java.nio.file.Files
import java.nio.file.Paths
import java.util.concurrent.TimeUnit

fun main() {

    val stopwatch = Stopwatch.createStarted()
    println("Starting the dump...")

    for (id in 0..29999) {

        if (File("./data/discord/assets/image/items/$id.png").exists()) {
            println("Skipping item $id since it already exists")
            continue
        }

        try {
            URL("https://www.osrsbox.com/osrsbox-db/items-icons/$id.png").openStream().use { `in` -> Files.copy(`in`, Paths.get("./data/discord/assets/image/items/$id.png")) }
            println("Saving ID $id")
        } catch (e : IOException) {
            println("Could not find image for $id")
            continue
        }

    }

    println("A total of 24867 items has been saved in ${stopwatch.stop().elapsed(TimeUnit.MILLISECONDS)} MS")

}