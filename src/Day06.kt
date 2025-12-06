fun main() {
    fun part1(input: List<String>): Long {
        return input.toCMPs().sumOf { it.calculate() }
    }

    fun part2(input: List<String>): Long {
        return input.toCMPs().sumOf { it.calculate2() }
    }

    val testInput = readInput("Day06_test")
    check(part1(testInput) == 4277556L)
    check(part2(testInput) == 3263827L)

    val input = readInput("Day06")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

fun List<String>.toCMPs(): List<CephaolpodMathProblem> {
    val separatorCols = (0 until first().length).mapNotNull { col ->
        if (this.all { it[col].isWhitespace() }) col else null
    }.toMutableList()
    separatorCols.add(0, 0)
    val cols = mutableListOf<List<String>>()
    separatorCols.windowed(2) { (first, second) ->
        val col = mutableListOf<String>()
        (0 until size).forEach {
            col.add(get(it).substring(first, second))
        }
        cols.add(col)
    }

    return cols.map {
        CephaolpodMathProblem(operator = it.last().trim(), numbers = it.dropLast(1))
    }
}

data class CephaolpodMathProblem(val operator: String, val numbers: List<String>) {
    val maxLength get() = numbers.maxOf { it.length }

    fun calculate(): Long {
        return when (operator) {
            "+" -> numbers.sumOf { it.trim().toLong() }
            "*" -> numbers.map { it.trim().toLong() }.reduce { acc, n -> acc * n }
            else -> error("no operator")
        }
    }

    fun calculate2(): Long {
        val nums = (maxLength - 1 downTo 0).map { x ->
            numbers.map {
                it[x]
            }.joinToString("").trim()
        }.map {
            if (it.isEmpty())
                if (operator == "*") 1L else 0L
            else it.toLong()

        }
        return when (operator) {
            "+" -> nums.sum()
            "*" -> nums.reduce { acc, n -> acc * n }
            else -> error("no operator")
        }
    }
}
