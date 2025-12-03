import java.io.File

fun main() {

    val input = File("input.txt").readLines()

    partOne(input)
    partTwo(input)
}

private fun partOne(input: List<String>) {
    var totalOutputJoltage = 0

    input.forEach {
        val firstNumber = it.dropLast(1).max()
        val secondNumber = it.drop(it.indexOf(firstNumber) + 1).max()

        totalOutputJoltage += (firstNumber.toString() + secondNumber.toString()).toInt()
    }

    println(totalOutputJoltage)
}

private fun partTwo(input: List<String>) {
    var totalOutputJoltage: Long = 0

    input.forEach {
        var tmp = it
        var numberFound = ""

        repeat(12) { index ->
            val maxNumber = tmp.dropLast(11 - index).max()
            tmp = tmp.drop(tmp.indexOf(maxNumber) + 1)

            numberFound += maxNumber
        }

        totalOutputJoltage += numberFound.toLong()
    }

    println(totalOutputJoltage)
}
