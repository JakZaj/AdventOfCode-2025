import java.awt.Point
import java.io.File

data class TachyonManifold(
    val ySize: Int,
    val startPoint: Point,
    val splitterPoints: List<Point>
)

fun main() {
    val tachyonManifold = loadFile()

    partOne(tachyonManifold)
    partTwo(tachyonManifold)
}

private fun partOne(t: TachyonManifold) {
    val beams = MutableList(t.ySize) { mutableSetOf<Int>() }
    beams[t.startPoint.y].add(t.startPoint.x)

    var result = 0

    for (y in 0..<t.ySize - 1) {
        for (x in beams[y]) {

            val isSplitter = Point(x, y) in t.splitterPoints

            val nextPositions =
                if (isSplitter) {
                    result++
                    listOf(x - 1, x + 1)
                } else {
                    listOf(x)
                }

            beams[y + 1].addAll(nextPositions)
        }
    }

    println(result)
}

private fun partTwo(tachyonManifold: TachyonManifold) {
    val cache = mutableMapOf<Point, Long>()
    println(recBeam(tachyonManifold, tachyonManifold.startPoint, cache))
}

private fun recBeam(tachyonManifold: TachyonManifold, current: Point, cache: MutableMap<Point, Long>): Long {
    cache[current]?.let { return it }

    if (current.y == tachyonManifold.ySize) {
        cache[current] = 1
        return 1
    }

    var sum = 0L

    if (current in tachyonManifold.splitterPoints) {
        val left = Point(current.x - 1, current.y + 1)
        val right = Point(current.x + 1, current.y + 1)

        sum += recBeam(tachyonManifold, left, cache)
        sum += recBeam(tachyonManifold, right, cache)

    } else {
        val down = Point(current.x, current.y + 1)
        sum += recBeam(tachyonManifold, down, cache)
    }

    cache[current] = sum

    return sum
}

private fun loadFile(): TachyonManifold {
    val lines = File("input.txt").readLines()

    val points = lines.flatMapIndexed { y, row ->
        row.mapIndexed { x, c -> c to Point(x, y) }
    }

    val startPoint = points.first { (c, _) -> c == 'S' }.second

    val splitterPoints = points
        .filter { (c, _) -> c == '^' }
        .map { it.second }

    return TachyonManifold(lines.size, startPoint, splitterPoints)
}