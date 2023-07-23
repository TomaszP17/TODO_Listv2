public enum Category {
    ID("ID"),
    TASK("TASK"),
    IS_DONE("IS DONE"),
    PRIORITY("PRIORITY"),
    DATA("DATA");
    private final String category;

    Category(String category) {
        this.category = category;
    }

    @Override
    public String toString() {
        return category;
    }
}
