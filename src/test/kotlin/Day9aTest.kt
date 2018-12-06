import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Test

class Day9aTest {

    @Test
    fun scoreOfEmptyGarbageIs0() {
        assertThat(Day9a.solveFor("<>"), CoreMatchers.equalTo(0))
    }

    @Test
    fun scoreOfOneGroupIs1() {
        assertThat(Day9a.solveFor("{}"), CoreMatchers.equalTo(1))
    }

    @Test
    fun scoreOfThreeNestedGroupsIs6() {
        assertThat(Day9a.solveFor("{{{}}}"), CoreMatchers.equalTo(6))
    }

    @Test
    fun scoreOfTwoGroupsInAGroupIs5() {
        assertThat(Day9a.solveFor("{{},{}}"), CoreMatchers.equalTo(5))
    }

    @Test
    fun scoreOfMultipleNestedGroupsIsCorrect() {
        assertThat(Day9a.solveFor("{{{},{},{{}}}}"), CoreMatchers.equalTo(16))
    }

    @Test
    fun scoreOfOneGroupWithGarbageIs1() {
        assertThat(Day9a.solveFor("{<a>,<a>,<a>,<a>}"), CoreMatchers.equalTo(1))
    }

    @Test
    fun scoreOfFourGroupsWithGarbageInOneGroupIs9() {
        assertThat(Day9a.solveFor("{{<ab>},{<ab>},{<ab>},{<ab>}}"), CoreMatchers.equalTo(9))
    }

    @Test
    fun scoreOfFourGroupsWithGarbageOfDoubleIgnoringCharactersInOneGroupIs9() {
        assertThat(Day9a.solveFor("{{<!!>},{<!!>},{<!!>},{<!!>}}"), CoreMatchers.equalTo(9))
    }

    @Test
    fun scoreOfGroupWithGarbageIs1() {
        assertThat(Day9a.solveFor("{<{},{},{{}}>}"), CoreMatchers.equalTo(1))
    }

    @Test
    fun scoreOfNestedGroupWithGarbageOfIgnoredCharactersIs3() {
        assertThat(Day9a.solveFor("{{<a!>},{<a!>},{<a!>},{<ab>}}"), CoreMatchers.equalTo(3))
    }

    @Test
    fun scoreOfOneNestedGroupAndOtherIgnoredIs3() {
        assertThat(Day9a.solveFor("{<!!!>{}>,{}}"), CoreMatchers.equalTo(3))
    }
}