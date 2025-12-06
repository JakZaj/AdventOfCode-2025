import java.io.File

fun main() {
    val numbers2DList = readNumbers()
    val operationList = readOperation()
    val numbers2DListPartTwo = readNumbersPartTwo()

    partOne(numbers2DList, operationList)
    partTwo(numbers2DListPartTwo, operationList)
}

private fun partOne(numbers2DList: List<List<Long>>, operationList: List<String>) {
    var result = 0L
    val indices: MutableList<Int> = mutableListOf()

    repeat(numbers2DList.size) {index ->
        indices.add(index)
    }

    operationList.forEachIndexed() { index, it ->
        when (it) {
            "*" -> result += indices.fold(1L) { acc, i ->
                acc * numbers2DList[i][index]
            }

            "+" -> result += indices.fold(0L) { acc, i ->
                acc + numbers2DList[i][index]
            }
        }
    }

    println(result)
}

private fun partTwo(numbers2DList: List<List<Long>>, operationList: List<String>) {
    var result = 0L

    operationList.forEachIndexed() { index, it ->

        when (it) {
            "*" -> result += numbers2DList[index].fold(1L) { acc, i ->
                acc * i
            }

            "+" -> result += numbers2DList[index].fold(0L) { acc, i ->
                acc + i
            }
        }
    }

    println(result)
}

private fun readNumbers(): List<List<Long>> =
    File("input.txt")
        .readLines()
        .dropLast(1)
        .map { line ->
            line.split(" ")
                .filter { it.isNotBlank() }
                .map(String::toLong)
        }

private fun readNumbersPartTwo(): List<List<Long>> {
    val numbers = File("input.txt")
        .readLines()
        .dropLast(1)
        .fold(emptyList<String>()) { acc, line ->
            line.mapIndexed { index, c ->
                (acc.getOrNull(index) ?: "") + c
            }
        }

    return getNumberFromStringListToSplit(numbers)
}

private fun getNumberFromStringListToSplit(listToSplit: List<String>): List<List<Long>> =
    listToSplit.fold(mutableListOf(mutableListOf<Long>())) { acc, list ->
        if (list.isBlank()) {
            acc.add(mutableListOf())
        } else {
            acc.last().add(list.trim().toLong())
        }
        acc
    }

private fun readOperation(): List<String> =
    File("input.txt")
        .readLines()
        .takeLast(1)
        .flatMap { s ->
            s.split(" ")
                .filter { it.isNotBlank() }
        }
