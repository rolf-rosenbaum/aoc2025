fun main() {
    fun part1(input: List<String>): Long {
        return Long::isInvalidID.sumUp(input)
    }

    fun part2(input: List<String>): Long {
        return Long::isInvalidID2.sumUp(input)
    }

    val testInput = readInput("Day02_test")
    check(part1(testInput) == 1227775554L)
    check(part2(testInput) == 4174379265L)

    val input = readInput("Day02")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

private fun ((Long) -> Boolean).sumUp(input: List<String>): Long =
    input.toRanges().sumOf { range ->
        range.filter {
            this(it)
        }.sum()//.also { it.writeToConsole() }
    }

private fun Long.isInvalidID(): Boolean {
    val s = toString()
    return if (s.length % 2 == 1) false
    else {
        s.take(s.length / 2) == s.substring(s.length / 2)
    }
}

private fun Long.isInvalidID2(): Boolean {
    val s = toString()
    (1..s.length / 2).forEach {
        val patterns = s.windowed(it, it)
        if (patterns.distinct().size == 1 && patterns.first().length * patterns.size == s.length)
            return true
    }
    return false
}

fun List<String>.toRanges(): List<LongRange> {
    return first().split(",").map { range ->
        range.split("-").let {
            it.first().toLong()..it.second().toLong()
        }
    }
}
