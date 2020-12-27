public class Type {
    private final String name;
    private char emblem;

    public Type(String name, char emblem) {
        this.name = name;
        this.emblem = emblem;
    }

    public Type(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
