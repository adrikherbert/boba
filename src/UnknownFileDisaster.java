public class UnknownFileDisaster extends Exception {
    private final String extension;
    private final String message;

    public UnknownFileDisaster(String extension) {
        this.message = String.format("Unknown File Disaster! -->> Incorrect file extension!\nExpected: \".boba\"\nWas: \".%s\"", extension);
        this.extension = extension;
    }

    public UnknownFileDisaster(String message, String extension) {
        this.message = message;
        this.extension = extension;
    }

    public String getExtension() {
        return extension;
    }

    public String getMessage() {
        return message;
    }
}
