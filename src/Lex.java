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
    private final char LEFTB = '[';
    private final char RIGHTB = ']';
    private final String DIGITS = "0123456789";

    public Lex(String fileName) throws UnknownFileDisaster {
        File file = new File(fileName);
        lines = new ArrayList<>();
        tokens = new ArrayList<>();

        String extension = fileName.split("\\.")[1];
        if (!extension.equals("poco")) {
            throw new UnknownFileDisaster(extension);
        }

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
                            else tokens.add(new Token(token, new Type("KEY")));
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
                    } else if (currentChar == LEFTB) {
                        while ((currentChar = token.charAt(0)) == LEFTB) {
                            tokens.add(new Token(String.valueOf(currentChar), new Type("LEFTB")));
                            token = token.substring(1);
                        }
                    }

                    /* CHECK AND ADD FOR KEYWORDS AND DATA TYPES */
                    int tokenLength;
                    StringBuilder tb = new StringBuilder();
                    while ((tokenLength = token.length()) > 0 && (currentChar = token.charAt(0)) != RIGHTP && currentChar != RIGHTB) {
                        tb.append(currentChar);
                        token = token.substring(1);
                    }

                    //TODO: CHECK FOR KEYWORDS IN SWITCH OR IF/ELSEIF/ELSE HERE

                    /* CHECK FOR DATA TYPES */
                    String sectString = tb.toString();
                    String type = evaluateType(sectString);
                    tokens.add(new Token(sectString, new Type(type)));

                    while (token.length() > 0) {
                        currentChar = token.charAt(0);

                        if (currentChar == RIGHTP) tokens.add(new Token(String.valueOf(currentChar), new Type("RIGHTP")));
                        else if (currentChar == RIGHTB) tokens.add(new Token(String.valueOf(currentChar), new Type("RIGHTB")));
                        token = tb.substring(1);
                    }
                }
            }
        }
    }

    public String evaluateType(String token) {
        for (int i = 0; i < token.length(); i++) {
            char c = token.charAt(i);
            if (!DIGITS.contains(String.valueOf(c))) {
                if (token.charAt(0) == '"' && token.charAt(token.length() - 1) == '"') return "STR";
                else if (token.charAt(0) == '\'' && token.charAt(token.length() - 1) == '\'') return "CHAR";
                else return "KEY";
            }

            if (i == token.length() - 1) {
                return "NUM";
            }
        }

        return "VOID";
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
        Lex test = null;

        try {
            test = new Lex("first.poco");
        } catch (UnknownFileDisaster e) {
            System.out.println(e.getMessage());
            return;
        }

        test.compileTokens();
        test.printTokens();
    }
}
