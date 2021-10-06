import org.junit.jupiter.api.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.MethodSource;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Stream;

import static org.hamcrest.CoreMatchers.*;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class WordOccurrencesTest {
    private Path workingDir;
    private WordOccurrences sample;

    @BeforeAll
    void initDir() {
        this.workingDir = Path.of("", "src/test/resources");
    }

    @BeforeEach
    public void setUp() {
        this.sample = new WordOccurrences();
    }

    @Test
    @DisplayName("File not found")
    public void wrongFile() {
        String file = String.valueOf(this.workingDir.resolve("in_wrong.txt"));

        Exception exception =  assertThrows(NoSuchFieldException.class, () -> this.sample.process(file));

        String actualMessage = exception.getMessage();
        String expectedMessage = "File not found";

        assertTrue(actualMessage.contains(expectedMessage));
    }

    @Test
    @DisplayName("File is read correctly")
    public void correctFile() throws Exception {
        String file = String.valueOf(this.workingDir.resolve("in.txt"));
        var temp = new HashMap<String, Integer>();
        temp.put("lorem", 1);
        temp.put("ipsum", 1);
        var res = this.sample.process(file);

        assertThat(res, is(instanceOf(Map.class)));
        assertThat(res, is(temp));
    }

    @Test
    @DisplayName("Path is read correctly")
    public void output() throws IOException {
        String input = String.valueOf(this.workingDir.resolve("in.txt"));
        InputStream in = new ByteArrayInputStream(input.getBytes());
        System.setIn(in);

        assertEquals(input, this.sample.getPath());
    }

    @ParameterizedTest
    @MethodSource("process")
    @DisplayName("Parameterized test")
    void testWithExplicitLocalMethodSource(String argument) throws IOException, NoSuchFieldException {
        assertNotNull(this.sample.process(argument));
    }

    static Stream<String> process() {
        String path = "/Users/ksishka/Documents/university/3nd_course/sys_prog/Lab_1_v3/src/test/resources/in.txt";
        return Stream.of(path);
    }
}