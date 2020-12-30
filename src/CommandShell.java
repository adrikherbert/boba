import java.util.Scanner;

/**
 * Command shell sequence for Poco
 */
public class CommandShell {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("poco >> ");
            String input = scan.nextLine();

            if (input.equals("exit")) break;
            else if (input.substring(0, 5).equals("poco ")) {
                String fileName = input.substring(5);
                Lex lexer = null;

                try {
                    lexer = new Lex(fileName);
                } catch (UnknownFileDisaster e) {
                    System.out.println(e.getMessage());
                    return;
                }

                lexer.compileTokens();
                lexer.printTokens();
            } else {
                System.out.println("unknown command");
            }
        }

        scan.close();
    }
}
