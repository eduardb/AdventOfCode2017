import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class Day1bTest {

    @Test
    fun solveFor1212() {
        assertThat(Day1b.solveFor("1212"), equalTo(6))
    }

    @Test
    fun solveFor1221() {
        assertThat(Day1b.solveFor("1221"), equalTo(0))
    }

    @Test
    fun solveFor123425() {
        assertThat(Day1b.solveFor("123425"), equalTo(4))
    }

    @Test
    fun solveFor123123() {
        assertThat(Day1b.solveFor("123123"), equalTo(12))
    }

    @Test
    fun solveFor12131415() {
        assertThat(Day1b.solveFor("12131415"), equalTo(4))
    }
}