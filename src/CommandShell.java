import java.util.Scanner;

/**
 * Command shell sequence for Poco
 */
public class CommandShell {
    public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);

        while (true) {
            System.out.print("(poco)>> ");
            String input = scan.nextLine();

            if (input.equals("exit")) break;
        }

        scan.close();
    }
}
