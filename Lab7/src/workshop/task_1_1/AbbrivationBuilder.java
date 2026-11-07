package workshop.task_1_1;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;


public class AbbrivationBuilder {


    public static String build(List<String> list) {
        return list.stream()
                .filter(Objects::nonNull)
                .filter(s -> !s.isEmpty())
                .map(s -> String.valueOf(s.charAt(0)))
                .collect(Collectors.joining());
    }

}
