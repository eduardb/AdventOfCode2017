import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test

class Day1aTest {

    @Test
    fun solveFor1122() {
        assertThat(Day1a.solveFor("1122"), equalTo(3))
    }

    @Test
    fun solveFor1111() {
        assertThat(Day1a.solveFor("1111"), equalTo(4))
    }

    @Test
    fun solveFor1234() {
        assertThat(Day1a.solveFor("1234"), equalTo(0))
    }

    @Test
    fun solveFor91212129() {
        assertThat(Day1a.solveFor("91212129"), equalTo(9))
    }
}