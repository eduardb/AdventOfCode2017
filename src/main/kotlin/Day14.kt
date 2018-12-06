import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
//    val input = "nbysizxe"
    val input = "flqrgnkx"
    println(
            measureTimeMillis {
                println(Day14a.solveFor(input))
            }
    )
    println(
            measureTimeMillis {
                println(Day14b.solveFor(input))
            }
    )
}

object Day14a {
    fun solveFor(input: String): Int =
            (0..127).sumBy { (knotHashToBinary(knotHash(input + "-" + it))).count { it } }
}


object Day14b {
    fun solveFor(input: String): Int {
        val disk: MutableList<MutableList<Boolean>> = (0..127)
                .map { (knotHashToBinary(knotHash(input + "-" + it))).toMutableList() }.toMutableList()
        var regions = 0

        (0..127).forEach { row ->
            (0..127).forEach { column ->
                if (disk[row][column]) {
                    regions++
                    fill(disk, row, column)
                }
            }
        }

        return regions
    }

    private val dir = listOf(-1 to -1, -1 to 1, 1 to -1, 1 to 1)

    private fun fill(disk: MutableList<MutableList<Boolean>>, row: Int, column: Int) {
        val queue = mutableListOf(row to column)
        while (queue.isNotEmpty()) {
            val coord = queue.removeAt(0)
            disk[coord.first][coord.second] = false
            dir.forEach {
                val pair = coord + it
                if (disk at pair) {
                    queue.add(pair)
                }
            }
        }
    }
}

operator fun Pair<Int, Int>.plus(other: Pair<Int, Int>): Pair<Int, Int> = this.first + other.first to this.second + other.second

infix fun List<List<Boolean>>.at(pair: Pair<Int, Int>) =
        (0 until size).contains(pair.first) && (0 until this[0].size).contains(pair.second) && this[pair.first][pair.second]

fun knotHash(input: String) = Day10b.solveFor(input)

fun knotHashToBinary(hash: String) = hash.flatMap {
    Integer.valueOf(it.toString(), 16).toString(2).padStart(4, '0').map { it == '1' }
}
