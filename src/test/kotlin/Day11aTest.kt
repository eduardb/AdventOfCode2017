import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test

class Day11aTest {

    @Test
    fun stepsFromWhereStartedIs0() {
        assertThat(Day11a.solveFor(parseDay11Input("ne,ne,sw,sw")), equalTo(0))
    }

    @Test
    fun stepsFor3StraightStepsIs3() {
        assertThat(Day11a.solveFor(parseDay11Input("ne,ne,ne")), equalTo(3))
    }

    @Test
    fun stepsFor4StepsWithAChangeOfDirectionToStraightDirectionIs2() {
        assertThat(Day11a.solveFor(parseDay11Input("ne,ne,s,s")), equalTo(2))
    }

    @Test
    fun stepsFor5StepsWithMultipleChangesOfDirectionIs3() {
        assertThat(Day11a.solveFor(parseDay11Input("se,sw,se,sw,sw")), equalTo(3))
    }


}