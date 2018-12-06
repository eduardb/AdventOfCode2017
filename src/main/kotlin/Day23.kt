@file:Suppress("PackageDirectoryMismatch")

package day23


fun main(args: Array<String>) {
    val input = """set b 81
set c b
jnz a 2
jnz 1 5
mul b 100
sub b -100000
set c b
sub c -17000
set f 1
set d 2
set e 2
set g d
mul g e
sub g b
jnz g 2
set f 0
sub e -1
set g e
sub g b
jnz g -8
sub d -1
set g d
sub g b
jnz g -13
jnz f 2
sub h -1
set g b
sub g c
jnz g 2
jnz 1 3
sub b -17
jnz 1 -23"""

    println(Day23a.solveFor(input.split('\n')))
    println(Day23b.solveFor(input.split('\n')))
}

object Day23a {

    fun solveFor(rawInstructions: List<String>): Int {
        val registers = mutableMapOf<Char, Long>()
        val instructions = InstructionParser.parse(rawInstructions, registers)
        var index = 0
        var muls = 0

        while (index in 0 until instructions.size) {
            val instruction = instructions[index]
            instruction.execute()
            when (instruction) {
                is JumpIfNotZero -> index += if (instruction.condition.getValue() != 0L) instruction.value.getValue().toInt() else 1
                is Multiply -> {
                    muls++
                    index++
                }
                else -> index++
            }
        }

        return muls
    }

}

object Day23b {

    fun solveFor(rawInstructions: List<String>): Long? {
        val registers = mutableMapOf('a' to 1L)
        val instructions = InstructionParser.parse(rawInstructions, registers)
        var index = 0

        while (index in 0 until instructions.size) {
            val instruction = instructions[index]
            instruction.execute()
            when (instruction) {
                is JumpIfNotZero -> index += if (instruction.condition.getValue() != 0L) instruction.value.getValue().toInt() else 1
                else -> index++
            }
        }

        return registers['h']
    }
}

object InstructionParser {
    fun parse(
            rawInstructions: List<String>,
            registers: MutableMap<Char, Long>): List<Instruction> = rawInstructions.map {
        val tokens = it.split(' ')
        when (tokens[0]) {
            "set" -> SetRegister(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "sub" -> Subtract(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "mul" -> Multiply(tokens[1].single(), ValueParser.parse(tokens[2], registers), registers)
            "jnz" -> JumpIfNotZero(
                    ValueParser.parse(tokens[1], registers),
                    ValueParser.parse(tokens[2], registers)
            )
            else -> throw IllegalArgumentException("${tokens[0]} not valid")
        }
    }
}

sealed class Instruction {
    abstract fun execute()
}

class SetRegister(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = value.getValue()
    }
}

class Subtract(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) - value.getValue()
    }
}

class Multiply(private val register: Char, private val value: Value, private val registers: MutableMap<Char, Long>) :
        Instruction() {
    override fun execute() {
        registers[register] = (registers[register] ?: 0) * value.getValue()
    }
}

class JumpIfNotZero(val condition: Value, val value: Value) : Instruction() {
    override fun execute() {
        // nothing
    }
}

object ValueParser {

    fun parse(rawValue: String, registers: Map<Char, Long>): Value = when {
        rawValue.first() in 'a'..'z' -> RegisterValue(rawValue.single(), registers)
        else -> NumericValue(rawValue.toLong())
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