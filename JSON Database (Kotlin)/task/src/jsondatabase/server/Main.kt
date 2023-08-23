package jsondatabase.server

const val ERROR = "ERROR"
const val OK = "OK"
const val size = 100
val database = Array<String?>(size) { null }
fun main() {
    var command = readln().split(" ")
    while (command[0] != "exit") {
        try {
            val index = command[1].toInt() - 1
            when (command[0]) {
                "get" -> {
                    val item = database[index]
                    if (item == null) {
                        println(ERROR)
                    } else {
                        println(item)
                    }
                }

                "set" -> {
                    database[index] = command.subList(2, command.lastIndex + 1).joinToString(" ")
                    println(OK)
                }

                "delete" -> {
                    database[index] = null
                    println(OK)
                }
            }
        } catch (e: Exception) {
            println(ERROR)
        }

        command = readln().split(" ")
    }
}