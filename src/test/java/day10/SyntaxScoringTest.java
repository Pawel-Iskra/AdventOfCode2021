package day10;

import org.junit.Before;
import utils.MyUtilities;
import org.junit.Test;

import java.io.IOException;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class SyntaxScoringTest {

    private List<String> testSymbolLines;
    private SyntaxScoring syntaxScoring;

    @Before
    public void testSetUp() throws IOException {
        testSymbolLines = MyUtilities.getInputLines("src/test/java/day10/testInput.txt");
        syntaxScoring = new SyntaxScoring();
    }

    @Test
    public void check_answer_for_part_one_on_example_input() throws IOException {
        // given - by testSetUp()

        // when
        int result = syntaxScoring.getResultForPartOne(testSymbolLines);

        // then
        assertEquals(result, 26397);
    }

    @Test
    public void check_answer_for_part_two_on_example_input() throws IOException {
        // given - by testSetUp()

        // when
        long result = syntaxScoring.getResultForPartTwo(testSymbolLines);

        // then
        assertEquals(result, 288957);
    }

}