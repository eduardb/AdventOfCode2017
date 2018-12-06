fun main(args: Array<String>) {
//    val input = """0: 3
//1: 2
//4: 4
//6: 4"""
    val input = """0: 3
1: 2
2: 4
4: 4
6: 5
8: 6
10: 6
12: 8
14: 6
16: 6
18: 9
20: 8
22: 8
24: 8
26: 12
28: 8
30: 12
32: 12
34: 12
36: 10
38: 14
40: 12
42: 10
44: 8
46: 12
48: 14
50: 12
52: 14
54: 14
56: 14
58: 12
62: 14
64: 12
66: 12
68: 14
70: 14
72: 14
74: 17
76: 14
78: 18
84: 14
90: 20
92: 14"""
    println(Day13a.solveFor(input.split('\n').map { it.split(": ") }.map { it[0].toInt() to it[1].toInt() }))
    println(Day13b.solveFor(input.split('\n').map { it.split(": ") }.map { it[0].toInt() to it[1].toInt() }))
}

object Day13a {
    fun solveFor(recording: List<Pair<Int, Int>>): Int {
        var tripSeverity = 0

        recording.forEach {
            if (position(it.first, it.second) == 0) {
                tripSeverity += it.first * it.second
            }
        }

        return tripSeverity
    }
}

object Day13b {
    fun solveFor(recording: List<Pair<Int, Int>>): Int {
        var delay = 1

        w@ while (true) {

            for (it in recording)
                if (position(it.first + delay, it.second) == 0) {
                    delay++
                    continue@w
                }
            break
        }

        return delay
    }
}

private fun position(picosecond: Int, range: Int): Int {
    val cycle = range * 2 - 2
    val inCycle = picosecond % cycle
    return if (inCycle < range) {
        inCycle
    } else {
        cycle - inCycle
    }
}
