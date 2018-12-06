import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test

class Day9bTest {

    @Test
    fun emptyGarbageHas0GarbageCharacters() {
        assertThat(Day9b.solveFor("<>"), equalTo(0))
    }

    @Test
    fun garbageWithRandomCharactersHas17GarbageCharacters() {
        assertThat(Day9b.solveFor("<random characters>"), equalTo(17))
    }

    @Test
    fun garbage3OpenAngularBracketsHas3GarbageCharacters() {
        assertThat(Day9b.solveFor("<<<<>"), equalTo(3))
    }
}