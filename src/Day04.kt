fun main() {
    fun part1(input: List<String>): Int {
        val grid = input.toGrid()
        return grid.count {
            it.allNeighbours().filter { n -> n in grid }.size < 4
        }
    }

    fun part2(input: List<String>): Int {
        val grid = input.toGrid()
        val lastGrid = generateSequence(grid.toMutableSet() to true) { (g, _) ->
            val newGrid = g.filter { p ->
                p.allNeighbours().filter { n -> n in g }.size >= 4
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

fun List<String>.toGrid(): Set<Point> {
    val grid = mutableSetOf<Point>()
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '@') grid.add(Point(x, y))
        }
    }
    return grid
}