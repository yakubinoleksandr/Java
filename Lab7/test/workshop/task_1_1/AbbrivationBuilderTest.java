package workshop.task_1_1;

import org.junit.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

public class AbbrivationBuilderTest {


    @Test
    public void build_AllParametersNotNull() {
        List<String> list = Arrays.asList("Name", "Petronomics", "Surname");
        assertEquals("NPS", AbbrivationBuilder.build(list));
    }

    @Test
    public void build_OneOfParametersIsNull() {
        List<String> list = Arrays.asList("Name", null, "Surname");
        assertEquals("NS", AbbrivationBuilder.build(list));
    }

    @Test
    public void build_OneOfParametersIsEmptyString() {
        List<String> list = Arrays.asList("Name", "", "Surname");
        assertEquals("NS", AbbrivationBuilder.build(list));
    }
}