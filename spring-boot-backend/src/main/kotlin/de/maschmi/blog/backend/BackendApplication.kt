package de.maschmi.blog.backend

import application.CommissionStorageCabinet
import application.GetListOfStorageCabinets
import domain.Room
import domain.RoomName
import domain.StorageCabinetName
import org.springframework.boot.ApplicationArguments
import org.springframework.boot.ApplicationRunner
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.stereotype.Component
import kotlin.system.exitProcess

@SpringBootApplication
class BackendApplication

fun main(args: Array<String>) {
	runApplication<BackendApplication>(*args)
}


@Component
class AppStartupRunner(
    private val commissionStorageCabinet: CommissionStorageCabinet,
    private val getListOfStorageCabinets: GetListOfStorageCabinets
) : ApplicationRunner {
    override fun run(args: ApplicationArguments) {
        println("Hello from AppRunner")
        commissionStorageCabinet.execute(
			name = StorageCabinetName("test"),
			room = Room(RoomName("test-room"))
		)
        val cabinets = getListOfStorageCabinets.execute()
        println("List of Storage Cabinets: $cabinets")
        exitProcess(0)
    }
}
