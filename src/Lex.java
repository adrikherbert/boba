import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;

public class Lex {
    private ArrayList<Token> tokens;
    private ArrayList<String> lines;

    private final char ADD = '+';
    private final char SUB = '-';
    private final char MULT = '*';
    private final char DIV = '/';
    private final char MOD = '%';
    private final char LEFTP = '(';
    private final char RIGHTP = ')';
    private final String DIGITS = "0123456789";

    public Lex(String fileName) {
        File file = new File(fileName);
        lines = new ArrayList<>();
        tokens = new ArrayList<>();

        try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
            String currentLine;

            while ((currentLine = reader.readLine()) != null) {
                lines.add(currentLine);
            }
        } catch (IOException e) {
            System.out.println("Provided file not found!");
        }
    }

    public void compileTokens() {
        for (String text : lines) {
            String[] splitLine = text.split(" ");

            for (String token : splitLine) {
                if (token.length() == 1) {
                    char tokenChar = token.charAt(0);

                    switch (tokenChar) {
                        case ADD -> tokens.add(new Token(token, new Type("ADD")));
                        case SUB -> tokens.add(new Token(token, new Type("SUB")));
                        case MULT -> tokens.add(new Token(token, new Type("MULT")));
                        case DIV -> tokens.add(new Token(token, new Type("DIV")));
                        case MOD -> tokens.add(new Token(token, new Type("MOD")));
                        default -> {
                            if (DIGITS.contains(token)) tokens.add(new Token(token, new Type("NUM")));
                            else tokens.add(new Token(token, new Type("CHAR")));
                        }
                    }
                } else {
                    char currentChar = token.charAt(0);

                    /* CHECK AND ADD ALL LEFT PARENTHESES */
                    if (currentChar == LEFTP) {
                        while ((currentChar = token.charAt(0)) == LEFTP) {
                            tokens.add(new Token(String.valueOf(currentChar), new Type("LEFTP")));
                            token = token.substring(1);
                        }
                    }

                    /* CHECK AND ADD FOR KEYWORDS AND DATA TYPES */
                    int tokenLength;
                    StringBuilder tb = new StringBuilder();
                    while ((tokenLength = token.length()) > 0 && (currentChar = token.charAt(0)) != RIGHTP) {
                        tb.append(currentChar);
                        token = token.substring(1);
                    }

                    //TODO: CHECK FOR KEYWORDS IN SWITCH OR IF/ELSEIF/ELSE HERE

                    /* CHECK FOR DATA TYPES */
                    String sectString = tb.toString();
                    for (int i = 0; i < sectString.length(); i++) {
                        char c = sectString.charAt(i);
                        if (!DIGITS.contains(String.valueOf(c))) {
                            tokens.add(new Token(sectString, new Type("STR")));
                            break;
                        }

                        if (i == sectString.length() - 1) {
                            tokens.add(new Token(sectString, new Type("NUM")));
                        }
                    }

                    while (token.length() > 0) {
                        tokens.add(new Token(String.valueOf(token.charAt(0)), new Type("RIGHTP")));
                        token = tb.substring(1);
                    }
                }
            }
        }
    }

    public void printTokens() {
        System.out.print('{');
        for (int i = 0; i < tokens.size(); i++) {
            Token currentToken = tokens.get(i);

            if (i < tokens.size() - 1) {
                System.out.printf("%s:%s, ", currentToken.getType().getName(), currentToken.getValue());
            } else {
                System.out.printf("%s:%s", currentToken.getType().getName(), currentToken.getValue());
            }
        }
        System.out.println('}');
    }

    public static void main(String[] args) {
        Lex test = new Lex("first.poco");

        test.compileTokens();
        test.printTokens();
    }
}
