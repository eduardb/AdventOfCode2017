fun main(args: Array<String>) {
    val input = 289326
    println(Day3a.solveFor(input))
    println(Day3b.solveFor(input))
}

fun Int.sqrt() = Math.sqrt(this.toDouble())

fun Double.ceil(): Int = Math.ceil(this).toInt()

fun Int.square(): Int = this * this

object Day3a {
    fun solveFor(input: Int): Int {
        if (input == 1) {
            return 0
        }

        val row = rowFor(input)
        val rowStart = rowStartFor(row)
        val segmentSize = row * 2
        val segmentDelta = (input - rowStart) % segmentSize

        return row + Math.abs(segmentDelta - row + 1)
    }

    fun rowFor(input: Int) = input.sqrt().ceil() / 2

    fun rowStartFor(row: Int): Int {
        return (row * 2 - 1).square() + 1
    }

}

object Day3b {

    data class Cell(val x: Int, val y: Int)

    fun solveFor(input: Int): Int {
        val spiral: MutableMap<Cell, Int> = mutableMapOf(Cell(0, 0) to 1)

        var x = 1
        var y = 0

        fun findLargerThanInput(segmentSize: Int, followDirection: () -> Unit): Int {
            for (i in 0 until segmentSize) {
                val sum = sumOfNeighbors(x, y, spiral)
                if (sum > input) {
                    return sum
                }
                spiral[Cell(x, y)] = sum
                followDirection()
            }
            return -1
        }

        while (true) {

            val segmentSize = x * 2

            var found: Int

            // bottom right upwards
            found = findLargerThanInput(segmentSize - 1, { y-- })
            if (found > input) {
                return found
            }

            // top right to left
            found = findLargerThanInput(segmentSize, { x-- })
            if (found > input) {
                return found
            }

            // top left downwards
            found = findLargerThanInput(segmentSize, { y++ })
            if (found > input) {
                return found
            }

            // bottom left to right
            found = findLargerThanInput(segmentSize + 1, { x++ })
            if (found > input) {
                return found
            }
        }
    }

    private val dir = listOf(-1 to -1, -1 to 0, -1 to 1, 0 to -1, 0 to 1, 1 to -1, 1 to 0, 1 to 1)

    private fun sumOfNeighbors(x: Int, y: Int, spiral: MutableMap<Cell, Int>): Int = dir.fold(0) { acc, pair ->
        acc + (spiral[Cell(pair.first + x, pair.second + y)] ?: 0)
    }

}


