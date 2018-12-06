import kotlin.system.measureTimeMillis

fun main(args: Array<String>) {
//    val input = 3
    val input = 314
    println(
            measureTimeMillis {
                println(Day17a.solveFor(input))
            }
    )
    println(
            measureTimeMillis {
                println(Day17b2.solveFor(input))
            }
    )
}

object Day17a {
    fun solveFor(steps: Int): Int {
        val state = mutableListOf(0)
        var currentPosition = 0
        (1..2017).forEach {
            currentPosition = (currentPosition + steps) % state.size
            if (currentPosition == state.size) {
                state.add(it)
            } else {
                state.add(currentPosition + 1, it)
                currentPosition++
            }
        }
        return state[currentPosition + 1]
    }

}

object Day17b {
    fun solveFor(steps: Int): Int {
        val state = CircularBuffer(0)
        var toSkip: Int
        (1..50000000).forEach {
        //        (1..2017).forEach {
            toSkip = steps % state.size
            (0 until toSkip).forEach { _ -> state.next() }
            state.insert(it)
            state.next()
        }

        return state[1]
    }

}

object Day17b2 {
    fun solveFor(steps: Int): Int? {
        var state1: Int? = null
        var currentPosition = 0
        (1..50000000).forEach {
    //        (1..2017).forEach {
            currentPosition = (currentPosition + steps) % it
            if (currentPosition == 0) {
                state1 = it
            }
            currentPosition++
        }

        return state1
    }

}

class CircularBuffer<T>(firstElement: T) {

    private val firstNode = Node(firstElement)

    private var current = firstNode

    var size = 1
        private set

    data class Node<T>(val data: T, var next: Node<T>? = null)

    fun next() {
        current = current.next ?: firstNode
    }

    fun insert(it: T) {
        val next = current.next
        val node = Node(it, next)
        current.next = node
        size++
    }

    operator fun get(index: Int): T {
        var node = firstNode
        (0 until index).forEach { node = node.next ?: firstNode }
        return node.data
    }
}

