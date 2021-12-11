package day11;

import org.junit.Before;
import org.junit.Test;
import utils.MyUtilities;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class DumboOctopusTest {

    private List<String> testOctopusEnergyLevelsLines;
    private DumboOctopus dumboOctopus;

    @Before
    public void testSetUp() throws IOException {
        testOctopusEnergyLevelsLines = MyUtilities.getInputLines("src/test/java/day11/testInput.txt");
        dumboOctopus = new DumboOctopus();
    }

    @Test
    public void check_answer_for_part_one_on_example_input() throws IOException {
        // given - by testSetUp()

        // when
        int result = dumboOctopus.getResultForPartOne(testOctopusEnergyLevelsLines, 100);

        // then
        assertEquals(result, 1656);
    }

    @Test
    public void check_answer_for_part_two_on_example_input() throws IOException {
        // given - by testSetUp()

        // when
        int result = dumboOctopus.getResultForPartTwo(testOctopusEnergyLevelsLines);

        // then
        assertEquals(result, 195);
    }

}