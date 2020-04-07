package parking

import java.util.Scanner

data class Car(val plate: String, val color: String)

class ParkingLot {
    private var parkingLot: Array<Car?>? = null

    private fun checkPark(fn: () -> String): String = if (parkingLot == null) {
        "Sorry, parking lot is not created."
    } else {
        fn()
    }

    fun park(car: Car): String = checkPark {
        val i = parkingLot!!.indexOfFirst { it == null }
        if (i > -1) {
            parkingLot!![i] = car
            "${car.color} car parked on the spot ${i + 1}."
        } else {
            "Sorry, parking lot is full."
        }
    }

    fun leave(spot: Int): String = checkPark {
        parkingLot!![spot - 1] = null
        "Spot $spot is free."
    }

    fun create(spots: Int): String {
        this.parkingLot = Array(spots) { null }
        return "Created a parking lot with $spots spots."
    }

    fun status(): String = checkPark {
        if (parkingLot!!.all { it == null }) {
            "Parking lot is empty."
        } else {
            val sb = StringBuilder()
            parkingLot!!.forEachIndexed { index, car ->
                if (car != null) {
                    sb.appendln("${index + 1} ${car.plate} ${car.color}")
                }
            }
            sb.removeSuffix("\n").toString()
        }
    }

    fun getByColor(color: String): String = checkPark {
        val uColor = color.trim().toUpperCase()
        val list: List<String> = this.parkingLot
                ?.filter { it?.color?.toUpperCase() == uColor }
                ?.map { it!!.plate }
                ?: emptyList()
        if (list.isEmpty()) "No cars with color $uColor were found." else list.joinToString(", ")
    }

    fun spotByColor(color: String): String = checkPark {
        val uColor = color.trim().toUpperCase()
        val list: List<Int> = parkingLot
                ?.withIndex()
                ?.filter { it.value?.color?.toUpperCase() == uColor }
                ?.map { (index, _) -> index + 1}
                ?: emptyList()
        if (list.isEmpty()) "No cars with color $uColor were found." else list.joinToString(", ")
    }

    fun spotByPlate(plate: String): String = checkPark {
        val tPlate = plate.trim()
        val i: Int? = parkingLot
                ?.withIndex()
                ?.filter { it.value?.plate?.contains(tPlate) ?: false }
                ?.map { (index, _) -> index + 1 }
                ?.getOrNull(0)

        i?.toString() ?: "No cars with registration number $tPlate were found."
    }
}

fun main() {
    val parkingLot = ParkingLot()

    val scanner = Scanner(System.`in`)
    var exit = false

    loop@ while (!exit) {
        when (val command: String = scanner.next()) {
            "" -> continue@loop
            "exit" -> exit = true
            else -> println(when (command) {
                "reg_by_color" -> parkingLot.getByColor(scanner.nextLine())
                "spot_by_color" -> parkingLot.spotByColor(scanner.nextLine())
                "spot_by_reg" -> parkingLot.spotByPlate(scanner.nextLine())
                "create" -> parkingLot.create(scanner.nextInt())
                "status" -> parkingLot.status()
                "park" -> parkingLot.park(Car(scanner.next(), scanner.next()))
                "leave" -> parkingLot.leave(scanner.nextInt())
                else -> throw IllegalArgumentException("Unrecognized command")
            })
        }
    }
}
