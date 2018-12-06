fun main(args: Array<String>) {
//    val input = "3,4,1,5" to 5
    val input = "197,97,204,108,1,29,5,71,0,50,2,255,248,78,254,63" to 256
    println(Day10a.solveFor(input.first.split(',').map { it.toInt() }, input.second))
    println(Day10b.solveFor(input.first))
}

object Day10a {
    fun solveFor(lengths: List<Int>, listSize: Int): Int {
        var numbers = Array(listSize, { it }).toMutableList()
        var currentPosition = 0
        var skipSize = 0

        lengths.forEach { length ->
            if (length > 1) {
                if (currentPosition + length < listSize) {
                    val range = currentPosition until currentPosition + length
                    val reversed = numbers.slice(range).reversed()
                    numbers = numbers.filterIndexed { index, _ -> index !in range }.toMutableList()
                    numbers.addAll(currentPosition, reversed)
                } else {
                    val indices = (currentPosition until listSize) + (0 until (currentPosition + length) % listSize)
                    val reversed = numbers.slice(indices).reversed()
                    numbers = numbers.filterIndexed { index, _ -> index !in indices }.toMutableList()
                    numbers.addAll(0, reversed.slice(listSize - currentPosition until length))
                    numbers.addAll(reversed.slice(0 until listSize - currentPosition))
                }
            }
            currentPosition += length + skipSize
            currentPosition %= listSize
            skipSize++
        }

        return numbers[0] * numbers[1]
    }

}

object Day10b {
    fun solveFor(input: String): String {
        val lengths = input.map { it.toInt() } + listOf(17, 31, 73, 47, 23)
        val listSize = 256

        var numbers = Array(listSize, { it }).toMutableList()
        var currentPosition = 0
        var skipSize = 0

        for (i in 1..64) {
            lengths.forEach { length ->
                if (length > 1) {
                    if (currentPosition + length < listSize) {
                        val range = currentPosition until currentPosition + length
                        val reversed = numbers.slice(range).reversed()
                        numbers = numbers.filterIndexed { index, _ -> index !in range }.toMutableList()
                        numbers.addAll(currentPosition, reversed)
                    } else {
                        val indices = (currentPosition until listSize) + (0 until (currentPosition + length) % listSize)
                        val reversed = numbers.slice(indices).reversed()
                        numbers = numbers.filterIndexed { index, _ -> index !in indices }.toMutableList()
                        numbers.addAll(0, reversed.slice(listSize - currentPosition until length))
                        numbers.addAll(reversed.slice(0 until listSize - currentPosition))
                    }
                }
                currentPosition += length + skipSize
                currentPosition %= listSize
                skipSize++
            }
        }

        val sparseHash = Array(16) {
            var hash = numbers[it * 16]
            for (i in 1 until 16) {
                hash = hash xor numbers[it * 16 + i]
            }
            hash
        }

        return sparseHash.joinToString("") { Integer.toHexString(it).padStart(2, '0') }

    }
}
