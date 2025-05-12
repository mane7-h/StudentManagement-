package core;

public class Course {
    private String code;
    private String title;
    private int credits;

    public Course(String code, String title, int credits) {
        this.code = code;
        this.title = title;
        this.credits = credits;
    }

    public String getCode() { return code; }
    public String getTitle() { return title; }
    public int getCredits() { return credits; }

    @Override
    public String toString() {
        return String.format("[Code: %s, Title: %s, Credits: %d]", code, title, credits);
    }
}