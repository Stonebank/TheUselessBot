package discord.core

interface ServiceProcessor {

    val timer: Timer?
    val delay: Int
    fun init()

}