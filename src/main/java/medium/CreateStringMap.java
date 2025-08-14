package medium;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class CreateStringMap {

    public static void main(String[] args) {
        List<String> list = List.of("apple", "ball", "cat", "dog", "elephant", "fox");
        Map<Character, String> map = list.stream()
            .collect(Collectors.toMap(
                str -> str.charAt(0),  // key mapper: gets first character
                str -> str             // value mapper: the string itself
            ));
        System.out.println(map);
    }
}