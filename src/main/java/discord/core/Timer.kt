package discord.core

import java.util.concurrent.TimeUnit

class Timer(val time: Int, val unit: TimeUnit) {

    val MS: Long
        get() = when (unit) {
            TimeUnit.DAYS -> TimeUnit.DAYS.toMillis(time.toLong())
            TimeUnit.HOURS -> TimeUnit.MILLISECONDS.toMillis(time.toLong())
            TimeUnit.MINUTES -> TimeUnit.MINUTES.toMillis(time.toLong())
            TimeUnit.SECONDS -> TimeUnit.SECONDS.toMillis(time.toLong())
            else -> time.toLong()
        }

    val timeLeft: String
        get() = time.toString() + " " + unit.toString().toLowerCase()

}