fun main() {
    val n = readln().toInt()
    val incomes = IntArray(n) { readln().toInt() }
    val taxes = IntArray(n) { readln().toInt() }

    var max = -1;
    var resultIndex = -1
    incomes.forEachIndexed { index, i ->
        val paidTax = taxes[index] * i
        if (paidTax > max) {
            resultIndex = index + 1
            max = paidTax
        }
    }

    println(resultIndex)
}