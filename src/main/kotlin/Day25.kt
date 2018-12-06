fun main(args: Array<String>) {
    println(Day25.solve())
}

enum class State {
    A, B, C, D, E, F
}

object Day25 {


    fun solve(): Int {

        var state = State.A
        var pos = 0
        val tape = mutableMapOf(0 to 0)

        repeat(12964419) {
            when(state) {
                State.A -> {
                    if (tape[pos] ?: 0 == 0) {
                        tape[pos] = 1
                        pos++
                        state = State.B
                    } else if (tape[pos] == 1) {
                        tape[pos] = 0
                        pos++
                        state = State.F
                    }
                }
                State.B -> {
                    if (tape[pos] ?: 0 == 0) {
                        tape[pos] = 0
                        pos--
                        state = State.B
                    } else if (tape[pos] == 1) {
                        tape[pos] = 1
                        pos--
                        state = State.C
                    }
                }
                State.C -> {
                    if (tape[pos] ?: 0 == 0) {
                        tape[pos] = 1
                        pos--
                        state = State.D
                    } else if (tape[pos] == 1) {
                        tape[pos] = 0
                        pos++
                        state = State.C
                    }
                }
                State.D -> {
                    if (tape[pos] ?: 0 == 0) {
                        tape[pos] = 1
                        pos--
                        state = State.E
                    } else if (tape[pos] == 1) {
                        tape[pos] = 1
                        pos++
                        state = State.A
                    }
                }
                State.E -> {
                    if (tape[pos] ?: 0 == 0) {
                        tape[pos] = 1
                        pos--
                        state = State.F
                    } else if (tape[pos] == 1) {
                        tape[pos] = 0
                        pos--
                        state = State.D
                    }
                }
                State.F -> {
                    if (tape[pos] ?: 0 == 0) {
                        tape[pos] = 1
                        pos++
                        state = State.A
                    } else if (tape[pos] == 1) {
                        tape[pos] = 0
                        pos--
                        state = State.E
                    }
                }
            }
        }
        return tape.values.filter { it == 1 }.count()
    }
}