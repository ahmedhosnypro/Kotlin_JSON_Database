fun main() {
    val nums = IntArray(readln().toInt()) { readln().toInt() }
    println(if (readln().toInt() in nums) "YES" else "NO")
}