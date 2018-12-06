package day18

fun main(args: Array<String>) {
    /*val input = """set a 1
add a 2
mul a a
mod a 5
snd a
set a 0
rcv a
jgz a -1
set a 1
jgz a -2"""*/
    /* val input = """snd 1
 snd 2
 snd p
 rcv a
 rcv b
 rcv c
 rcv d"""*/
    val input = """set i 31
set a 1
mul p 17
jgz p p
mul a 2
add i -1
jgz i -2
add a -1
set i 127
set p 464
mul p 8505
mod p a
mul p 129749
add p 12345
mod p a
set b p
mod b 10000
snd b
add i -1
jgz i -9
jgz a 3
rcv b
jgz b -1
set f 0
set i 126
rcv a
rcv b
set p a
mul p -1
add p b
jgz p 4
snd a
set a b
jgz 1 3
snd b
set f 1
add i -1
jgz i -11
snd a
jgz f -16
jgz a -19"""
//    println(Day18a.solveFor(input.split('\n')))
    println(Day18b.solveFor(input.split('\n')))
}
/*

object Day18a {
    fun solveFor(rawInstructions: List<String>): Long {
        val registers = mutableMapOf<Char, Long>()
        val instructions = InstructionParser.parse(rawInstructions, registers)
        var index = 0
        while (index in 0 until instructions.size) {
            val instruction = instructions[index]
            instruction.execute()
            when (instruction) {
                is JumpIfGreaterThanZero -> index += if (instruction.condition.getValue() > 0) instruction.value.getValue().toInt() else 1
                is Recover -> if (instruction.value.getValue() != 0L) return PlaySound.lastPlayed!! else index++
                else -> index++
            }
        }
        throw IllegalStateException("No non-zero recover!!!")
    }

}

object InstructionParser {
    fun parse(
            rawInstructions: List<String>,
            registers: MutableMap<Char, Long>): List<Instruction> = rawInstructions.map {
        val tokens = it.split(' ')
        when (tokens[0]) {
            "snd" -> PlaySound(ValueParser.parse(tokens[1], registers))
            "set" -> SetRegister(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "add" -> Add(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "mul" -> Multiply(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "mod" -> Modulo(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "rcv" -> Recover(ValueParser.parse(tokens[1], registers))
            "jgz" -> JumpIfGreaterThanZero(
                    ValueParser.parse(tokens[1], registers),
                    ValueParser.parse(tokens[2], registers)
            )
            else -> throw IllegalArgumentException("${tokens[0]} not valid")
        }
    }
}

object ValueParser {

    fun parse(rawValue: String, registers: Map<Char, Long>): Value = when {
        rawValue.first() in 'a'..'z' -> RegisterValue(rawValue.first(), registers)
        else -> NumericValue(rawValue.toLong())
    }
}

sealed class Instruction {
    abstract fun execute()
}

class PlaySound(private val value: Value) : Instruction() {

    companion object {
        var lastPlayed: Long? = null
    }

    override fun execute() {
        lastPlayed = value.getValue()
    }
}

class SetRegister(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = value.getValue()
    }
}

class Add(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) + value.getValue()
    }
}

class Multiply(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) * value.getValue()
    }
}

class Modulo(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) % value.getValue()
    }
}

class Recover(val value: Value) : Instruction() {
    override fun execute() {
        // nothing
    }
}

class JumpIfGreaterThanZero(val condition: Value, val value: Value) : Instruction() {
    override fun execute() {
        // nothing
    }
}

sealed class Value {
    abstract fun getValue(): Long
}

class RegisterValue(private val register: Char, private val registers: Map<Char, Long>) : Value() {
    override fun getValue(): Long = registers[register] ?: 0L
}

class NumericValue(private val value: Long) : Value() {
    override fun getValue(): Long = value
}*/

object Day18b {
    fun solveFor(rawInstructions: List<String>): Int {
        val program0 = Program(0, rawInstructions)
        val program1 = Program(1, rawInstructions)

        var sentByProgram0 = 0
        var sentByProgram1 = 0

        while (!(program0.ended || program0.blocked) || !(program1.ended || program1.blocked)) {
            if (!(program0.ended || program0.blocked)) {
                sentByProgram0 += program0.execute(program1)
            }
            if (!(program1.ended || program1.blocked)) {
                sentByProgram1 += program1.execute(program0)
            }
        }

        return sentByProgram1
    }

}

class Program(id: Int, rawInstructions: List<String>) {

    private val registers = mutableMapOf('p' to id.toLong())
    private val instructions = InstructionParser.parse(rawInstructions, registers)
    private val inbox = mutableListOf<Long>()
    private var deadlock = false
    private var index = 0

    fun execute(otherProgram: Program): Int {
        val instruction = instructions[index]
        instruction.execute()
        when (instruction) {
            is JumpIfGreaterThanZero -> index += if (instruction.condition.getValue() > 0) instruction.value.getValue().toInt() else 1
            is Receive -> {
                if (inbox.isEmpty()) {
                    deadlock = true
                } else {
                    registers[instruction.register] = inbox.removeAt(0)
                    index++
                }
            }
            is Send -> {
                otherProgram.accept(instruction.value.getValue())
                index++
                return 1
            }
            else -> index++
        }

        return 0
    }


    private fun accept(value: Long) {
        deadlock = false
        inbox.add(value)
    }

    val ended: Boolean
        get() = index !in 0 until instructions.size

    val blocked: Boolean
        get() = deadlock
}


object InstructionParser {
    fun parse(
            rawInstructions: List<String>,
            registers: MutableMap<Char, Long>): List<Instruction> = rawInstructions.map {
        val tokens = it.split(' ')
        when (tokens[0]) {
            "snd" -> Send(ValueParser.parse(tokens[1], registers))
            "set" -> SetRegister(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "add" -> Add(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "mul" -> Multiply(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "mod" -> Modulo(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "rcv" -> Receive(tokens[1].single())
            "jgz" -> JumpIfGreaterThanZero(
                    ValueParser.parse(tokens[1], registers),
                    ValueParser.parse(tokens[2], registers)
            )
            else -> throw IllegalArgumentException("${tokens[0]} not valid")
        }
    }
}

object ValueParser {

    fun parse(rawValue: String, registers: Map<Char, Long>): Value = when {
        rawValue.first() in 'a'..'z' -> RegisterValue(rawValue.first(), registers)
        else -> NumericValue(rawValue.toLong())
    }
}

sealed class Instruction {
    abstract fun execute()
}

class Send(val value: Value) : Instruction() {

    override fun execute() {
    }
}

class SetRegister(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = value.getValue()
    }
}

class Add(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) + value.getValue()
    }
}

class Multiply(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) * value.getValue()
    }
}

class Modulo(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) % value.getValue()
    }
}

class Receive(val register: Char) : Instruction() {
    override fun execute() {
        // nothing
    }
}

class JumpIfGreaterThanZero(val condition: Value, val value: Value) : Instruction() {
    override fun execute() {
        // nothing
    }
}

sealed class Value {
    abstract fun getValue(): Long
}

class RegisterValue(private val register: Char, private val registers: Map<Char, Long>) : Value() {
    override fun getValue(): Long = registers[register] ?: 0L
}

class NumericValue(private val value: Long) : Value() {
    override fun getValue(): Long = value
}