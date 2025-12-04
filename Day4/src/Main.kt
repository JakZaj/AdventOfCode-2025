import java.io.File

fun main() {
    val input = loadData()

    PartOne(input)
    PartTwo(input)
}

private fun PartOne(input: List<Pair<Int, Int>>) {
    println(getNumberAndPositionsToRemovedRollsOfPaper(input).first)
}

private fun PartTwo(input: List<Pair<Int, Int>>) {
    val inputList = input.toMutableList()
    var totalRollsOfPaper = 0

    do {
        val totalRollsOfPaperInLastRepeat = totalRollsOfPaper
        val numberAndPositionToRemove = getNumberAndPositionsToRemovedRollsOfPaper(inputList)

        totalRollsOfPaper += numberAndPositionToRemove.first

        numberAndPositionToRemove.second.forEach {
            inputList.remove(it)
        }

    } while (totalRollsOfPaper != totalRollsOfPaperInLastRepeat)

    println(totalRollsOfPaper)
}

private fun getNumberAndPositionsToRemovedRollsOfPaper(input: List<Pair<Int, Int>>): Pair<Int, MutableList<Pair<Int, Int>>> {

    var totalRollsOfPaper = 0
    val listOfRollsOfPaperToRemove: MutableList<Pair<Int, Int>> = mutableListOf()

    input.forEach {
        var rollsOfPaperCount = -1

        repeat(3) { row ->
            repeat(3) { col ->
                if (input.contains(Pair(it.first - 1 + row, it.second - 1 + col)))
                    rollsOfPaperCount++
            }
        }

        if (rollsOfPaperCount < 4) {

            listOfRollsOfPaperToRemove.add(it)
            totalRollsOfPaper++
        }
    }

    return Pair(totalRollsOfPaper, listOfRollsOfPaperToRemove)
}

private fun loadData(): List<Pair<Int, Int>> = File("input.txt").readLines()
    .flatMapIndexed { row, line ->
        line.mapIndexedNotNull { col, char ->
            if (char == '@') row to col else null
        }
    }