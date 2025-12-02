import java.io.File

fun main() {
    val input = loadInput()

    part(input, ::isValidID)
    part(input, ::isValidIDPartTwo)
}

private fun part(input: List<Pair<Long, Long>>, validFun: (Long) -> Long) {
    var sumOfInvalidID: Long = 0

    input.forEach { sumOfInvalidID += getSumInvalidIdInRange(it, validFun) }

    println(sumOfInvalidID)
}

private fun getSumInvalidIdInRange(data: Pair<Long, Long>, validFun: (Long) -> Long): Long {
    var sumInvalidId: Long = 0

    for (i in data.first..data.second)
        sumInvalidId += validFun(i)

    return sumInvalidId
}

private fun isValidID(id: Long): Long {
    val idLength = getLength(id)

    if (idLength % 2 != 0)
        return 0L

    val firstPart: Long = id / pow10L(idLength / 2)
    val secondPart: Long = id % pow10L(idLength / 2)

    if (firstPart != secondPart)
        return 0L

    return id
}

private fun isValidIDPartTwo(id: Long): Long {
    val idLength = getLength(id)

    for (i in 1..idLength / 2) {
        if (isLongHaveSequence(id, id / pow10L(idLength - i)))
            return id
    }

    return 0L
}

private fun isLongHaveSequence(long: Long, seq: Long): Boolean {
    val longLength = getLength(long)
    val seqLength = getLength(seq)

    if (longLength % seqLength != 0)
        return false

    for (i in 1..longLength / seqLength) {
        val divisor = pow10L(longLength - (i + 1) * seqLength)

        val longToCheck = long / divisor % pow10L(seqLength)

        if (seq != longToCheck)
            return false
    }

    return true
}

private fun getLength(long: Long): Int {
    var longToCheck: Long = long
    var len: Int = 0

    while (longToCheck != 0L) {
        longToCheck /= 10
        len++
    }

    return len
}

private fun pow10L(exp: Int): Long {
    var result = 1L
    repeat(exp) {
        result *= 10L
    }
    return result
}

private fun loadInput() =
    File("input.txt").readText()
        .replace("\r\n", "")
        .split(",")
        .map { pair ->
            val (a, b) = pair.split("-")
            a.toLong() to b.toLong()
        }