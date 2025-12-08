fun main() {
    fun part1(input: List<String>): Int {
        val tachyonLab = input.toTachyonLab()

        val last = generateSequence(tachyonLab) { lab ->
            lab.nextGeneration()
        }.takeWhile { it.beams.keys.maxY() < it.splitters.maxY() }.last()

        return last.countSplits()
    }

    fun part2(input: List<String>): Int {
        val tachyonLab = input.toTachyonLab()

        return tachyonLab.countTimelines(Point(tachyonLab.splitters.first().x, 0))
    }

    val testInput = readInput("Day07_test")
    check(part1(testInput) == 21)
    check(part2(testInput).also { it.writeToConsole() } == 40)

    val input = readInput("Day07")
    println("Part 1:")
    part1(input).writeToConsole()
    println("Part 2:")
    part2(input).writeToConsole()
}

data class TachyonLab(val splitters: Set<Point>, val beams: Map<Point, Int>) {
    fun nextGeneration(): TachyonLab {
        val newBeams = mutableMapOf<Point, Int>()
        beams.keys.filter {it.y == beams.keys.maxY()}.forEach { p ->
            if (splitters.contains(Point(p.x, p.y + 1))) {
                newBeams[Point(p.x - 1, p.y + 1)] = beams[Point(p.x - 1, p.y + 1)]?.plus(1) ?:1 
                newBeams[Point(p.x + 1, p.y + 1)] = beams[Point(p.x + 1, p.y + 1)]?.plus(1) ?:1 
            } else {
                newBeams[Point(p.x, p.y + 1)] = 1
            }
        }
        return copy(beams = beams + newBeams)
    }

    private val cache = mutableMapOf<Point, Int>()

    fun countTimelines(p: Point): Int = cache.getOrPut(p) {
        if (p.y > splitters.maxY() + 1)  0
        else if (splitters.contains(p)) {
            1 + listOf(Point(p.x - 1, p.y + 1), Point(p.x + 1, p.y + 1)).sumOf { countTimelines(it) }
        } else countTimelines(Point(p.x, p.y+1))
    }

    fun countSplits() = splitters.count { beams.contains(Point(it.x, it.y - 1)) }
}


fun List<String>.toTachyonLab(): TachyonLab {
    val splitters = mutableSetOf<Point>()
    forEachIndexed { y, line ->
        line.forEachIndexed { x, c ->
            if (c == '^') splitters.add(Point(x, y))
        }
    }
    val start = Point(first().indexOf('S'), 0)
    val beams = mapOf(Point(start.x, 1) to 1)
    return TachyonLab(splitters = splitters, beams = beams)
}