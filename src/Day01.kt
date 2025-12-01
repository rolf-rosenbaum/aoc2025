fun main() {
    fun part1(input: List<String>): Int {

        val code = input.toIntList().iterator()
        var dial = 50
        var count = 0
        while (code.hasNext()) {
            dial = (dial + code.next()) % 100
            if (dial == 0) count++
        }
        return count
    }

    fun part2(input: List<String>): Long {
        val code = input.toIntList().iterator()
        var dial = 50
        var count = 0L
        while (code.hasNext()) {
            val n = code.next()
            if (n > 0) {
                (1..n).forEach {
                    dial = (dial + 1) % 100
                    if (dial == 0)
                        count++
                }
            } else {
                (n..-1).forEach {
                    dial = (dial - 1) % 100
                    if (dial == 0)
                        count++
                }
            }
        }
        return count
    }
    // Or read a large test input from the `src/Day01_test.txt` file:
    val testInput = readInput("Day01_test")
    check(part1(testInput) == 3)
    check(part2(testInput) == 6L)

    // Read the input from the `src/Day01.txt` file.
    val input = readInput("Day01")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()


}

fun List<String>.toIntList(): List<Int> {
    return map { line ->
        if (line.startsWith("R"))
            line.drop(1).toInt()
        else
            line.drop(1).toInt() * (-1)
    }
}
