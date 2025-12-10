import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Long {
        val redTiles = input.toPoints()
        val rectangles = redTiles.flatMapIndexed { index, left ->
            redTiles.drop(index + 1).map { right ->
                Rectangle.of(left, right)
            }
        }.sortedByDescending { it.area }
        return rectangles.first().area
    }


    fun part2(input: List<String>): Long {
        val redTiles = input.toPoints()
        val rectangles = redTiles.flatMapIndexed { index, left ->
            redTiles.drop(index + 1).map { right ->
                Rectangle.of(left, right)
            }
        }.sortedByDescending { it.area }
        val lines = (redTiles + redTiles.first())
            .zipWithNext().map { (left, right) ->
                Rectangle.of(left, right)
            }
        return rectangles.first { r ->
            lines.none { it.overlaps(r.inner()) }
        }.area


    }

    val testInput = readInput("Day09_test")
    check(part1(testInput) == 50L)
    check(part2(testInput) == 24L)

    val input = readInput("Day09")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

data class Rectangle(val x: IntRange, val y: IntRange) {
    val area = x.size().toLong() * y.size()

    fun overlaps(other: Rectangle): Boolean =
        x.overlapsWith(other.x) && y.overlapsWith(other.y)

    fun inner() = Rectangle(x.first + 1..<x.last, y.first + 1..<y.last())

    companion object {
        fun of(a: Point, b: Point): Rectangle = Rectangle(
            min(a.x, b.x)..max(a.x, b.x),
            min(a.y, b.y)..max(a.y, b.y),
        )
    }
}

fun List<String>.toPoints(): List<Point> =
    map { line ->
        line.split(",").map { it.toInt() }.let { Point(it.first(), it.second()) }
    }

fun IntRange.size() = last - first + 1