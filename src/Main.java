import cli.MainCLI;
import managers.*;
import ui.StudentUI;

public class Main {
    public static void main(String[] args) {
        StudentManager sm = new StudentManager();
        CourseManager cm = new CourseManager();
        EnrollmentManager em = new EnrollmentManager();

        if (args.length > 0 && args[0].equalsIgnoreCase("-cli")) {
            MainCLI.run(sm, cm, em);
        } else if (args.length > 0 && args[0].equalsIgnoreCase("-ui")) {
            javax.swing.SwingUtilities.invokeLater(() -> new StudentUI(sm, cm, em));
        } else {
            System.out.println("Usage: java Main [-cli | -ui]");
        }
    }
}