import kotlin.math.max
import kotlin.math.min

fun main() {
    fun part1(input: List<String>): Int {
        val ranges = input.toFreshRanges()
        return input.toIds().count { id ->
            ranges.any { range -> range.contains(id) }
        }
    }

    fun part2(input: List<String>): Long {
        var p = input.toFreshRanges().sortedBy { it.first }.toMutableSet() to true
        do {
            p = mergeOverlappingRanges(p.first)
        } while (p.second)
        return p.first
            .sumOf { it.last - it.first + 1 }
    }

    val testInput = readInput("Day05_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 14L)

    val input = readInput("Day05")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

private fun mergeOverlappingRanges(ranges: MutableSet<LongRange>): Pair<MutableSet<LongRange>, Boolean> {
    var range1: LongRange? = null
    var range2: LongRange? = null
    var changed = false
    ranges.forEach { r1 ->
        ranges.find { r2 ->
            if (r1 != r2 && r1.overlapsWith(r2)) true.also {
                range1 = r1
                range2 = r2
            } else false
        }
    }
    if (range1 != null && range2 != null) {
        changed = true
        ranges.remove(range1)
        ranges.remove(range2)
        ranges.add(range1.union(range2)!!)
    }
    return ranges to changed
}

fun List<String>.toFreshRanges(): List<LongRange> {
    return takeWhile { it.isNotBlank() }.map { line ->
        line.split("-").let {
            min(it.first().toLong(), it.second().toLong())..max(it.first().toLong(), it.second().toLong())
        }
    }
}

fun List<String>.toIds() = takeLastWhile { it.isNotBlank() }.map { it.toLong() }