package day_16;

import day_16.domain.FftCalculator;
import java.io.IOException;
import java.net.URISyntaxException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

public class Main {

  public static void main(String[] args) throws URISyntaxException, IOException {
    final String fileName = "day_16_input";
    List<Integer> input =
        Arrays.stream(
                Files.readString(
                        Paths.get(Main.class.getResource("/" + fileName + ".txt").toURI()),
                        StandardCharsets.UTF_8)
                    .split(""))
            .map(Integer::parseInt)
            .collect(Collectors.toList());

    // PART 1.
    List<Integer> output = FftCalculator.calculate(input, 100);
    System.out.println(String.format("FIRST EIGHT DIGITS OF THE OUTPUT: %s", output.subList(0, 8)));
  }
}
