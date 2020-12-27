import java.util.ArrayList;

public class Lex {
    private ArrayList<Token> tokens;
    private String text;
    private char current;
    private int pos;

    private final char ADD = '+';
    private final char SUB = '-';
    private final char MULT = '*';
    private final char DIV = '/';
    private final char LEFTP = '(';
    private final char RIGHTP = ')';
    private final char SPACE = ' ';
    private final String NUM = "0123456789";

    public Lex(String text) {
        tokens = new ArrayList<>();
        this.text = text;
        pos = -1;
    }

    public void compileTokens() {
        StringBuilder sb = new StringBuilder();
        int flush = 0;

        while (true) {
            if (!next()) flush = 1;
            else sb.append(current);

            if (current == SPACE) flush++;

            if (flush == 1) {
                String token = sb.toString().strip();

                if (token.length() == 1) {
                    switch (token.charAt(0)) {
                        case ADD -> tokens.add(new Token(token, new Type("ADD")));
                        case SUB -> tokens.add(new Token(token, new Type("SUB")));
                        case MULT -> tokens.add(new Token(token, new Type("MULT")));
                        case DIV -> tokens.add(new Token(token, new Type("DIV")));
                        case LEFTP -> tokens.add(new Token(token, new Type("LEFTP")));
                        case RIGHTP -> tokens.add(new Token(token, new Type("RIGHTP")));
                        default -> tokens.add(new Token(token, new Type("NUM")));
                    }
                } else {
                    if (token.charAt(0) == LEFTP) {
                        while (token.charAt(0) == LEFTP) {
                            tokens.add(new Token("(", new Type("LEFTP")));
                            token = token.substring(1);
                        }
                        tokens.add(new Token(token, new Type("NUM")));
                    } else if (token.charAt(token.length() - 1) == RIGHTP) {
                        tokens.add(new Token(token.substring(0, token.indexOf(')')), new Type("NUM")));
                        token = token.substring(token.indexOf(')'));
                        for (int i = 0; i < token.length(); i++) {
                            tokens.add(new Token(")", new Type("RIGHTP")));
                        }
                    } else {
                        tokens.add(new Token(token, new Type("NUM")));
                    }
                }

                sb = new StringBuilder();
                flush = 0;
            }

            if (pos >= text.length()) return;
        }
    }

    public boolean next() {
        pos++;

        if (pos < text.length()) {
            current = text.charAt(pos);
            return true;
        } else {
            return false;
        }
    }

    public static void main(String[] args) {
        Lex test = new Lex("230 + ((2 - 3) + (52 - (3 * 4))");
        test.compileTokens();

        System.out.print("{");
        for (int i = 0; i < test.tokens.size(); i++) {
            Token token = test.tokens.get(i);

            if (i < test.tokens.size() - 1) System.out.printf("%s:%s, ", token.getType().getName(), token.getValue());
            else System.out.printf("%s:%s", token.getType().getName(), token.getValue());
        }
        System.out.println("}");
    }
}
