import kotlin.collections.dropLast

fun main() {
    fun part1(input: List<String>): Long {
        return input.sumOf {
            toBatteryBank(it).recursiveJoltage(2).toLong()
        }
    }

    fun part2(input: List<String>): Long {
        return input.sumOf {
            toBatteryBank(it).recursiveJoltage(12).toLong()
        }
    }

    val testInput = readInput("Day03_test")
    check(part1(testInput) == 357L)
    check(part2(testInput) == 3121910778619L)

    val input = readInput("Day03")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

private fun toBatteryBank(string: String): List<Long> = string.map { c -> c.digitToInt().toLong() }

fun List<Long>.recursiveJoltage(num: Int): String {
    if (num == 1) return max().toString()
    val max = dropLast(num - 1).max()
    return max.toString() + drop(indexOf(max) +1).recursiveJoltage(num -1)
}