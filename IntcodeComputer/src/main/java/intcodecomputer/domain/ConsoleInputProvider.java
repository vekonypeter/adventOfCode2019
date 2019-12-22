package intcodecomputer.domain;

import java.util.Scanner;

public class ConsoleInputProvider implements InputProvider {

  private Scanner scanner = new Scanner(System.in);

  @Override
  public long getInput() {
    System.out.print("INPUT NEEDED: ");
    return Long.parseLong(scanner.nextLine());
  }
}
