import kotlin.math.abs

fun main() {
    fun part1(input: List<String>): Int {
        return countZeros(code = input.toIntList().iterator())
    }

    fun part2(input: List<String>): Int {
        return countZeros(code = input.toIntList().iterator(), countIntermediate = true)
    }

    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6)

    val input = readInput("Day01")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

private fun countZeros(code: Iterator<Int>, countIntermediate: Boolean = false): Int {
    var count = 0
    var dial = 50
    while (code.hasNext()) {
        val n = code.next()
        repeat(abs(n)) {
            dial = (dial + n / abs(n)) % 100
            if (dial == 0 && countIntermediate) count++
        }
        if (dial == 0 && !countIntermediate) count++
    }
    return count
}

fun List<String>.toIntList(): List<Int> {
    return map { line ->
        if (line.startsWith("R"))
            line.drop(1).toInt()
        else
            line.drop(1).toInt() * (-1)
    }
}
