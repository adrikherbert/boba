import java.util.ArrayList;

// BASIC TODO: ROBUSTIFY. TRACK ERROR LOCATIONS. IMPLEMENT MORE BASE LANGUAGE FUNCTIONALITY. FIX STRING SPACE ISSUE.

public class Interpreter {
    private final Lex lex;
    private final ArrayList<SVVariable> svVariables;
    private ArrayList<String> knownKeys;
    private final ArrayList<Token> tokens;
    private Token current;
    private int pos;

    public Interpreter(Lex lex) {
        this.lex = lex;
        svVariables = new ArrayList<>();
        knownKeys = new ArrayList<>();
        tokens = lex.getTokens();
        pos = -1;

        knownKeys.add("make");
        knownKeys.add("change");
    }

    public void interpret() {
        while (true) {
            if (!advance()) break;

            if (current.getValue().equals("make")) {
                if (!advance()) break;
                if (!make()) {
                    System.out.println("Couldn't make!");
                    return;
                }
            } else if (current.getValue().equals("print")) {
                if (!advance()) break;
                if (!print()) {
                    System.out.println("Couldn't print!");
                    return;
                }
            } else if (current.getValue().equals("change")) {
                if (!advance()) break;
                if (!change()) {
                    System.out.println("Couldn't change!");
                    return;
                }
            }
        }
    }

    public boolean make() {
        if (current.getType().equals("KEY")) {
            switch (current.getValue()) {
                case "number" -> {
                    if (!advance()) return false;
                    String name = current.getValue();

                    if (!advance()) return false;
                    SVVariable variable;
                    if (current.getValue().contains(".")) {
                        variable = new SVVariable(name, SVVariable.Type.FLOAT, current.getValue());
                    } else {
                        variable = new SVVariable(name, SVVariable.Type.INT, current.getValue());
                    }
                    svVariables.add(variable);
                }
                case "string" -> {
                    if (!advance()) return false;
                    String name = current.getValue();

                    if (!advance()) return false;
                    SVVariable variable = new SVVariable(name, SVVariable.Type.STRING, current.getValue());
                    svVariables.add(variable);
                }
                case "character" -> {
                    if (!advance()) return false;
                    String name = current.getValue();

                    if (!advance()) return false;
                    SVVariable variable = new SVVariable(name, SVVariable.Type.CHARACTER, current.getValue());
                    svVariables.add(variable);
                }
                case "boolean" -> {
                    if (!advance()) return false;
                    String name = current.getValue();

                    if (!advance()) return false;
                    SVVariable variable = new SVVariable(name, SVVariable.Type.BOOLEAN, current.getValue());
                    svVariables.add(variable);
                }
            }
        } else return false;

        return true;
    }

    public boolean print() {
        switch (current.getType()) {
            case "STRING" -> {
                String s = current.getValue();
                System.out.println(s);
            }
            case "INT", "FLOAT" -> {
                if (current.getValue().contains(".")) {
                    Float f = Float.valueOf(current.getValue());
                    System.out.println(f);
                } else {
                    Integer i = Integer.valueOf(current.getValue());
                    System.out.println(i);
                }
            }
            case "CHARACTER" -> {
                Character c = current.getValue().charAt(0);
                System.out.println(c);
            }
            case "KEY" -> {
                for (SVVariable variable : svVariables) {
                    if (current.getValue().equals(variable.getName())) {
                        System.out.println(variable.getValue());
                    }
                }
            }
            default -> { return false; }
        }

        return true;
    }

    public boolean change() {
        if (current.getType().equals("KEY")) {
            SVVariable currentVariable = null;

            for (SVVariable variable : svVariables) {
                if (current.getValue().equals(variable.getName())) {
                    currentVariable = variable;
                    break;
                }
            }

            if (currentVariable == null) {
                System.out.println("Couldn't identify variable!");
                return false;
            }

            if (!advance()) return false;

            if (!current.getValue().equals("to")) {
                System.out.println("Incorrect syntax! Need <to>");
                return false;
            }

            if (!advance()) return false;

            if (current.getType().equals("STRING") && currentVariable.getType() == SVVariable.Type.STRING) currentVariable.setValue(current.getValue());
            else if (current.getType().equals("CHARACTER") && currentVariable.getType() == SVVariable.Type.CHARACTER) currentVariable.setValue(current.getValue());
            else if (current.getType().equals("INT") && currentVariable.getType() == SVVariable.Type.INT) currentVariable.setValue(current.getValue());
            else if (current.getType().equals("FLOAT") && currentVariable.getType() == SVVariable.Type.FLOAT) currentVariable.setValue(current.getValue());
            else if (current.getType().equals("BOOLEAN") && currentVariable.getType() == SVVariable.Type.BOOLEAN) currentVariable.setValue(current.getValue());
            else {
                System.out.println("Couldn't identify Type!");
                return false;
            }
        } else {
            System.out.println("`change` needs a variable KEY!");
            return false;
        }

        return true;
    }

    public ArrayList<SVVariable> getSvVariables() {
        return svVariables;
    }

    public boolean advance() {
        pos++;
        if (pos < tokens.size()) {
            current = tokens.get(pos);
            return true;
        } else return false;
    }
}
