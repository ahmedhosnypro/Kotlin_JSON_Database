fun main() {
    val arr = Array(readln().toInt()) { readln().toInt() }
    val m = readln().toInt()
    arr.count { it == m }.run {
        println(this)
    }
}