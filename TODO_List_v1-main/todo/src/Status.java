public enum Status {
    TRUE("True"),
    FALSE("False");
    private final String status;
    Status(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return status;
    }
}
