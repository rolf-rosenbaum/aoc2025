fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.toGrid()
        return grid.count {
            it.adjacentPaperRolls(grid).size < 4
        }
    }

    fun part2(input: List<String>): Int {
        val grid = input.toGrid()
        val lastGrid = generateSequence(grid.toMutableSet() to true) { (g, _) ->
            val newGrid = g.filter { p ->
                p.adjacentPaperRolls(g).size >= 4
            }.toMutableSet()
            newGrid to (newGrid.size < g.size)
        }.takeWhile { it.second }.last().first

        return grid.size - lastGrid.size
    }

    val testInput = readInput("Day04_test")
    check(part1(testInput).also { it.writeToConsole() } == 13)
    check(part2(testInput) == 43)

    val input = readInput("Day04")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

private fun Point.adjacentPaperRolls(grid: Set<Point>): List<Point> = allNeighbours().filter { n -> n in grid }

fun List<String>.toGrid(): Set<Point> {
    val grid = mutableSetOf<Point>()
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '@') grid.add(Point(x, y))
        }
    }
    return grid
}