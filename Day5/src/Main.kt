import java.io.File

data object InputData {
    val freshIngredientRanges: MutableList<Pair<Long, Long>> = mutableListOf()
    val availableIngredient: MutableMap<Long, Int> = mutableMapOf()
}

fun main() {

    val inputData = loadData()

    partOne(inputData)
    partTwo(inputData)
}

private fun partOne(inputData: InputData) {
    inputData.availableIngredient.forEach {
        repeat(inputData.freshIngredientRanges.size) { index ->
            if (inputData.freshIngredientRanges[index].first <= it.key && it.key <= inputData.freshIngredientRanges[index].second)
                inputData.availableIngredient[it.key] = it.value + 1
        }
    }

    var freshCount = 0

    inputData.availableIngredient.forEach {
        if (it.value > 0)
            freshCount++
    }

    println(freshCount)
}

private fun partTwo(inputData: InputData) {

    var rageDone: MutableList<Pair<Long, Long>> = mutableListOf()

    inputData.freshIngredientRanges.forEach {
        rageDone.add(Pair(it.first, it.second))
    }

    rageDone = fixOverlap(rageDone)
    var freshCount = 0L

    rageDone.forEach {
        freshCount += it.second - it.first + 1
    }

    println(freshCount)
}

private fun fixOverlap(inputList: MutableList<Pair<Long, Long>>): MutableList<Pair<Long, Long>> {

    do {
        var fixeInThisLoop = false

        for (x in 0..<inputList.size) {
            for (i in 0..<inputList.size) {
                if (i == x)
                    continue

                if (isRangeOverlapOnFirstLeft(inputList[x], inputList[i])) {
                    inputList[x] = Pair(inputList[i].first, inputList[x].second)
                    fixeInThisLoop = true
                }

                if (isRangeOverlapOnFirstRight(inputList[x], inputList[i])) {
                    inputList[x] = Pair(inputList[x].first, inputList[i].second)
                    fixeInThisLoop = true
                }
            }
        }

    } while (fixeInThisLoop)

    return inputList.distinct().toMutableList()
}

private fun isRangeOverlapOnFirstLeft(firstRange: Pair<Long, Long>, secondRange: Pair<Long, Long>): Boolean =
    firstRange.first > secondRange.first && firstRange.first <= secondRange.second

private fun isRangeOverlapOnFirstRight(firstRange: Pair<Long, Long>, secondRange: Pair<Long, Long>): Boolean =
    firstRange.second >= secondRange.first && firstRange.second < secondRange.second


private fun loadData(): InputData {
    val inputData = InputData

    File("input.txt").readLines()
        .map { it.trim() }
        .filter { it.isNotEmpty() }
        .forEach { token ->
            when {
                "-" in token -> {
                    val (a, b) = token.split("-").map(String::toLong)
                    inputData.freshIngredientRanges.add(Pair(a, b))
                }
                
                else -> inputData.availableIngredient[token.toLong()] = 0
            }
        }

    return inputData
}
