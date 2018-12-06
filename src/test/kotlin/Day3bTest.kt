import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class Day3bTest(private val input: Int, private val value: Int) {
    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: value({0})={1}")
        fun data(): Collection<Array<Int>> {
            return listOf(
                    arrayOf(1, 2),
                    arrayOf(2, 4),
                    arrayOf(3, 4),
                    arrayOf(26, 54)
            )
        }
    }

    @Test
    fun shouldReturnExpectedValueForInput() {
        assertThat(Day3b.solveFor(input), equalTo(value))
    }
}
