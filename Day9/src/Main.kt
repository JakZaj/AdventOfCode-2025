import java.awt.Point
import kotlin.math.abs
import java.io.File

fun main() {
    val inputData = loadInput()

    partOne(inputData)
}

private fun partOne(input: List<Point>) {
    val area = prepareAreaFromPoints(input)
    println(area.last().third)
}

private fun prepareAreaFromPoints(input: List<Point>): MutableList<Triple<Int, Int, Long>> {
    val n = input.size
    val area = mutableListOf<Triple<Int, Int, Long>>()

    for (i in 0 until n) {
        for (j in i + 1 until n) {
            val a = calculateAreaBetweenPoints(input[i], input[j])
            area += Triple(i, j, a)
        }
    }

    area.sortBy { it.third }

    return area
}

private fun calculateAreaBetweenPoints(p1: Point, p2: Point): Long =
    ((abs(p1.x - p2.x) + 1).toLong() * (abs(p1.y - p2.y) + 1).toLong())


private fun loadInput(): List<Point> =
    File("input.txt")
        .readLines()
        .map { line ->
            val (x, y) = line.split(',').map { it.trim().toInt() }
            Point(x, y)
        }