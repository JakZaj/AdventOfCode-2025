import java.io.File
import kotlin.math.pow
import kotlin.math.sqrt

data class BoxPosition(
    val x: Double,
    val y: Double,
    val z: Double
) {
    fun distanceTo(other: BoxPosition): Double =
        sqrt(
            (this.x - other.x).pow(2.0) +
                    (this.y - other.y).pow(2.0) +
                    (this.z - other.z).pow(2.0)
        )
}

class DSU(n: Int) {
    private val parent = IntArray(n) { it }
    private val size = IntArray(n) { 1 }

    fun find(x: Int): Int {
        if (parent[x] != x) parent[x] = find(parent[x])
        return parent[x]
    }

    fun union(a: Int, b: Int): Boolean {
        val pa = find(a)
        val pb = find(b)
        if (pa == pb) return false
        if (size[pa] < size[pb]) {
            parent[pa] = pb
            size[pb] += size[pa]
        } else {
            parent[pb] = pa
            size[pa] += size[pb]
        }
        return true
    }

    fun sizeOf(x: Int) = size[find(x)]
}

fun main() {
    val input = loadBoxes()

    partOne(input)
    partTwo(input)
}

private fun partOne(boxPositions: List<BoxPosition>) {
    val n = boxPositions.size
    val dsu = DSU(n)
    val edges = prepareEdges(boxPositions)

    var idx = 0
    val targetPairs = 1000

    while (idx < targetPairs) {
        val (a, b, _) = edges[idx]
        dsu.union(a, b)
        idx++
    }

    val componentCount = mutableMapOf<Int, Int>()
    for (i in 0 until n) {
        val root = dsu.find(i)
        componentCount[root] = (componentCount[root] ?: 0) + 1
    }

    val result = componentCount.values .sortedDescending() .take(3) .reduce { acc, v -> acc * v }

    println(result)
}

private fun partTwo(boxPositions: List<BoxPosition>){
    val n = boxPositions.size
    val dsu = DSU(n)
    val edges = prepareEdges(boxPositions)

    var lastEdge: Triple<Int, Int, Double>? = null

    for ((i, j, d) in edges) {

        if (dsu.union(i, j)) {
            lastEdge = Triple(i, j, d)

            if (dsu.sizeOf(i) == n) break
        }
    }

    val result = (boxPositions[lastEdge?.first!!].x * boxPositions[lastEdge.second].x).toInt()
    println(result)
}

private fun prepareEdges(boxPositions: List<BoxPosition>): MutableList<Triple<Int, Int, Double>> {
    val n = boxPositions.size
    val edges = mutableListOf<Triple<Int, Int, Double>>()

    for (i in 0 until n) {
        for (j in i + 1 until n) {
            val d = boxPositions[i].distanceTo(boxPositions[j])
            edges += Triple(i, j, d)
        }
    }

    edges.sortBy { it.third }

    return edges
}

private fun loadBoxes(): List<BoxPosition> =
    File("input.txt")
        .readLines()
        .map { line ->
            val (x, y, z) = line.split(',').map { it.trim().toDouble() }
            BoxPosition(x, y, z)
        }