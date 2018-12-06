@file:Suppress("PackageDirectoryMismatch")

package day24

fun main(args: Array<String>) {
    /*val input = """0/2
2/2
2/3
3/4
3/5
0/1
10/1
9/10"""*/
    val input = """42/37
28/28
29/25
45/8
35/23
49/20
44/4
15/33
14/19
31/44
39/14
25/17
34/34
38/42
8/42
15/28
0/7
49/12
18/36
45/45
28/7
30/43
23/41
0/35
18/9
3/31
20/31
10/40
0/22
1/23
20/47
38/36
15/8
34/32
30/30
30/44
19/28
46/15
34/50
40/20
27/39
3/14
43/45
50/42
1/33
6/39
46/44
22/35
15/20
43/31
23/23
19/27
47/15
43/43
25/36
26/38
1/10"""
    println(Day24a.solveFor(input.split('\n').map(Component.Companion::from)))
}

object Day24a {

    fun solveFor(components: List<Component>): Pair<Int, Int> {
        val bridge = mutableListOf(Component.ZERO)

        return backtrack(bridge, components.toMutableList(), 0, 0)
    }

    fun backtrack(bridge: List<Component>, components: List<Component>, maxStrength: Int, maxLength: Int): Pair<Int, Int> {

        var localMaxStrength = maxStrength
        var localMaxLength = maxLength
/*
        if (bridge.size > 35) {
            println(bridge)
            println(bridge.strength())
        }*/

        if (bridge.size > localMaxLength) {
            localMaxStrength = bridge.strength()
            localMaxLength = bridge.size
        } else if (bridge.size == localMaxLength && bridge.strength() > localMaxStrength) {
            localMaxStrength = bridge.strength()
        }

        val next = components.filter { bridge.last().matching(it) } + components.map(Component::rotate).filter {
            bridge.last().matching(it)
        }

        next.forEach {
            val toRemove = if (it.rotated) it.rotate() else it
            val (localStrength, localLength) = backtrack(bridge + it, components - toRemove, localMaxStrength, localMaxLength)

            if (localLength > localMaxLength) {
                localMaxLength = localLength
                localMaxStrength = localStrength
            } else if (localLength == localMaxLength) {
                if (localStrength > localMaxStrength) {
                    localMaxStrength = localStrength
                }
            }
        }

        return localMaxStrength to localMaxLength
    }

}

private fun Iterable<Component>.strength(): Int {
    return sumBy(Component::strength)
}

class Component private constructor(private val leftPort: Int, private val rightPort: Int, val rotated: Boolean) {

    companion object {
        fun from(raw: String): Component {
            val (left, right) = raw.split('/')
            return Component(left.toInt(), right.toInt(), false)
        }

        val ZERO = Component(0, 0, false)
    }

    val strength = leftPort + rightPort

    fun matching(other: Component) = this.rightPort == other.leftPort && (other.rightPort != other.leftPort || !other.rotated)

    fun rotate() = Component(this.rightPort, this.leftPort, !this.rotated)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as Component

        if (leftPort != other.leftPort) return false
        if (rightPort != other.rightPort) return false
        if (rotated != other.rotated) return false

        return true
    }

    override fun hashCode(): Int {
        var result = leftPort
        result = 31 * result + rightPort
        result = 31 * result + rotated.hashCode()
        return result
    }

    override fun toString(): String {
        return "Component(leftPort=$leftPort, rightPort=$rightPort, rotated=$rotated)"
    }

}

