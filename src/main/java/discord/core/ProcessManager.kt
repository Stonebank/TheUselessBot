package discord.core

import discord.utils.Utils
import java.util.concurrent.Executors
import java.util.function.Consumer

class ProcessManager {

    private val service = Executors.newScheduledThreadPool(Runtime.getRuntime().availableProcessors())
    private val services: List<Any> = Utils.getObjects(ProcessManager::class.java.getPackage().name + ".container")

    fun init() {
        services.forEach(Consumer { service: Any -> start(service as ServiceProcessor) })
        println(services.size.toString() + " processes started")
    }

    private fun start(processor: ServiceProcessor) {
        service.scheduleAtFixedRate({
            try {
                processor.init()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }, processor.delay.toLong(), processor.timer!!.time.toLong(), processor.timer!!.unit)
    }

    companion object {
        val instance = ProcessManager()
    }

}