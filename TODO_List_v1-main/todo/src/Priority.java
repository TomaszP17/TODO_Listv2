public enum Priority {

    NOT_IMPORTANT("Not important"),
    IMPORTANT("Important"),
    VERY_IMPORTANT("Very important");

    private final String priority;

    Priority(String priority) {
        this.priority = priority;
    }

    @Override
    public String toString() {
        return priority;
    }
}
