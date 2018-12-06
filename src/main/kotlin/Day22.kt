@file:Suppress("PackageDirectoryMismatch")

package day22

// Clean nodes are shown as .; infected nodes are shown as #.

fun main(args: Array<String>) {
/*    val input = """..#
#..
..."""*/
    val input = """#.....##.####.#.#########
.###..#..#..####.##....#.
..#########...###...####.
.##.#.##..#.#..#.#....###
...##....###..#.#..#.###.
###..#...######.####.#.#.
#..###..###..###.###.##..
.#.#.###.#.#...####..#...
##........##.####..##...#
.#.##..#.#....##.##.##..#
###......#..##.####.###.#
....#..###..#######.#...#
#####.....#.##.#..#..####
.#.###.#.###..##.#..####.
..#..##.###...#######....
.#.##.#.#.#.#...###.#.#..
##.###.#.#.###.#......#..
###..##.#...#....#..####.
.#.#.....#..#....##..#..#
#####.#.##..#...##..#....
##..#.#.#.####.#.##...##.
..#..#.#.####...#........
###.###.##.#..#.##.....#.
.##..##.##...#..#..#.#..#
#...####.#.##...#..#.#.##"""

    println(Day22a.solveFor(input))
    println(Day22b.solveFor(input))

}


object GridParser {

    fun parseInto(grid: MutableMap<Pair<Int, Int>, Boolean>, input: String) {
        val split = input.split('\n')
        val rows = split.size
        split.forEachIndexed { rowIndex, row ->
            val columns = row.length
            row.forEachIndexed { columnIndex, char ->
                grid[columnIndex - columns / 2 to rowIndex - rows / 2] = char == '#'
            }
        }
    }
}

object Day22a {

    private const val ITERATIONS = 10000

    fun solveFor(input: String): Int {

        val grid = mutableMapOf<Pair<Int, Int>, Boolean>()

        GridParser.parseInto(grid, input)

        var infected = 0
        var position = 0 to 0
        var direction: Direction = Up

        repeat(ITERATIONS) {
            if (grid[position] == true) {
                direction = direction.turnRight()
                grid[position] = false
            } else {
                direction = direction.turnLeft()
                grid[position] = true
                infected++
            }
            position = direction.advanceFrom(position)
        }

        return infected
    }
}

object Day22b {
    private const val ITERATIONS = 10000000

    fun solveFor(input: String): Int {

        val oldGrid = mutableMapOf<Pair<Int, Int>, Boolean>()

        GridParser.parseInto(oldGrid, input)

        val grid = oldGrid.mapValues {
            if (it.value) Infected else Clean
        }.toMutableMap()

        var infected = 0
        var position = 0 to 0
        var direction: Direction = Up

        repeat(ITERATIONS) {
            val state = grid[position] ?: Clean
            direction = state.rotate(direction)
            grid[position] = state.nextState()
            if (grid[position] == Infected) {
                infected++
            }
            position = direction.advanceFrom(position)
        }

        return infected
    }

}

sealed class Direction {
    abstract fun turnRight(): Direction
    abstract fun turnLeft(): Direction
    abstract fun advanceFrom(position: Pair<Int, Int>): Pair<Int, Int>
}

sealed class State {
    abstract fun nextState(): State
    abstract fun rotate(direction: Direction): Direction
}

object Clean : State() {
    override fun nextState(): State = Weakened
    override fun rotate(direction: Direction) = direction.turnLeft()
}

object Weakened : State() {
    override fun nextState(): State = Infected
    override fun rotate(direction: Direction) = direction
}

object Infected : State() {
    override fun nextState(): State = Flagged
    override fun rotate(direction: Direction) = direction.turnRight()
}

object Flagged : State() {
    override fun nextState(): State = Clean
    override fun rotate(direction: Direction) = direction.turnLeft().turnLeft()
}

object Up : Direction() {

    override fun turnRight(): Direction = Right

    override fun turnLeft(): Direction = Left

    override fun advanceFrom(position: Pair<Int, Int>): Pair<Int, Int> = position.first to position.second - 1
}

object Down : Direction() {

    override fun turnRight(): Direction = Left

    override fun turnLeft(): Direction = Right

    override fun advanceFrom(position: Pair<Int, Int>): Pair<Int, Int> = position.first to position.second + 1
}

object Left : Direction() {

    override fun turnRight(): Direction = Up

    override fun turnLeft(): Direction = Down

    override fun advanceFrom(position: Pair<Int, Int>): Pair<Int, Int> = position.first - 1 to position.second
}

object Right : Direction() {

    override fun turnRight(): Direction = Down

    override fun turnLeft(): Direction = Up

    override fun advanceFrom(position: Pair<Int, Int>): Pair<Int, Int> = position.first + 1 to position.second
}