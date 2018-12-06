fun main(args: Array<String>) {
//    val input = "0\t2\t7\t0"
    val input = "14\t0\t15\t12\t11\t11\t3\t5\t1\t6\t8\t4\t9\t1\t8\t4"
    println(Day6a.solveFor(input.split('\t').map { it.toInt() }.toMutableList()))
    println(Day6b.solveFor(input.split('\t').map { it.toInt() }.toMutableList()))
}

object Day6a {
    fun solveFor(banks: MutableList<Int>): Int {

        val configurations = mutableSetOf<List<Int>>()
        configurations.add(listOf(*banks.toTypedArray()))

        val banksNumber = banks.size
        var cycle = 0

        while (true) {

            cycle++

            var max = banks.max()!!
            var maxBank = banks.indexOfFirst { it == max }

            banks[maxBank] = 0

            while (max > 0) {
                banks[++maxBank % banksNumber]++
                max--
            }
            if (configurations.contains(banks)) {
                return cycle
            }
            configurations.add(listOf(*banks.toTypedArray()))
        }
    }
}

object Day6b {
    fun solveFor(banks: MutableList<Int>): Int {

        val configurations = mutableSetOf<List<Int>>()
        configurations.add(listOf(*banks.toTypedArray()))

        val banksNumber = banks.size
        val toLookFor: List<Int>

        while (true) {
            var max = banks.max()!!
            var maxBank = banks.indexOfFirst { it == max }

            banks[maxBank] = 0

            while (max > 0) {
                banks[++maxBank % banksNumber]++
                max--
            }
            if (configurations.contains(banks)) {
                toLookFor = listOf(*banks.toTypedArray())
                break
            }
            configurations.add(listOf(*banks.toTypedArray()))
        }

        var cycle = 0
        while (true) {

            cycle++

            var max = banks.max()!!
            var maxBank = banks.indexOfFirst { it == max }

            banks[maxBank] = 0

            while (max > 0) {
                banks[++maxBank % banksNumber]++
                max--
            }
            if (toLookFor == banks) {
                return cycle
            }
        }
    }
}
