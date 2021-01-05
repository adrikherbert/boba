public class SVVariable {
    private final String name;
    private final Type type;
    private String value;

    enum Type {
        INT,
        FLOAT,
        STRING,
        CHARACTER,
        BOOLEAN
    }

    public SVVariable(String name, Type type, String value) {
        this.name = name;
        this.type = type;
        this.value = value;
    }

    public String getName() {
        return name;
    }

    public Type getType() {
        return type;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
