fun main(args: Array<String>) {
//    val a = 65L
//    val b = 8921L
    val a = 873L
    val b = 583L
    println(Day15a.solveFor(a, b))
    println(Day15b.solveFor(a, b))
}

const val genA = 16807
const val genB = 48271
const val divider = 2147483647L

object Day15a {
    const val numbersToConsider = 40_000_000
    fun solveFor(a: Long, b: Long): Int {
        var prevA = a
        var prevB = b
        var matches = 0
        (1..numbersToConsider).forEach {
            val newA = (prevA * genA) % divider
            val newB = (prevB * genB) % divider

            if (newA and 0xFFFF == newB and 0xFFFF) {
                matches++
            }
            prevA = newA
            prevB = newB
        }
        return matches
    }

}
object Day15b {
    const val numbersToConsider = 5_000_000
    fun solveFor(a: Long, b: Long): Int {
        var prevA = a
        var prevB = b
        var matches = 0
        (1..numbersToConsider).forEach {
            var newA = (prevA * genA) % divider
            while (newA % 4 != 0L) {
                prevA = newA
                newA = (prevA * genA) % divider
            }
            var newB = (prevB * genB) % divider
            while (newB % 8 != 0L) {
                prevB = newB
                newB = (prevB * genB) % divider
            }

            if (newA and 0xFFFF == newB and 0xFFFF) {
                matches++
            }
            prevA = newA
            prevB = newB
        }
        return matches
    }

}
