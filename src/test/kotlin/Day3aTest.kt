import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class Day3aTest(private val input: Int, private val steps: Int, private val row: Int, private val rowStart: Int) {

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{index}: steps({0})={1}, row={2}, rowStart={3}")
        fun data(): Collection<Array<Int>> {
            return listOf(
                    arrayOf(2, 1, 1, 2),
                    arrayOf(3, 2, 1, 2),
                    arrayOf(9, 2, 1, 2),
                    arrayOf(12, 3, 2, 10),
                    arrayOf(23, 2, 2, 10),
                    arrayOf(25, 4, 2, 10),
                    arrayOf(26, 5, 3, 26),
                    arrayOf(1024, 31, 16, 962)
            )
        }
    }

    @Test
    fun shouldReturnExpectedStepsForInput() {
        assertThat(Day3a.solveFor(input), equalTo(steps))
    }

    @Test
    fun shouldReturnExpectedRowForInput() {
        assertThat(Day3a.rowFor(input), equalTo(row))
    }

    @Test
    fun shouldReturnExpectedRowStartForInput() {
        assertThat(Day3a.rowStartFor(Day3a.rowFor(input)), equalTo(rowStart))
    }
}