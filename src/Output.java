/**Utility class to allow for indenting and coloring output to the terminal */
public class Output {
    private int indent;

    public static class Colors {
        public static final String RESET = "\u001B[0m";
        public static final String BLACK = "\u001B[30m";
        public static final String RED = "\u001B[31m";
        public static final String GREEN = "\u001B[32m";
        public static final String YELLOW = "\u001B[33m";
        public static final String BLUE = "\u001B[34m";
        public static final String PURPLE = "\u001B[35m";
        public static final String CYAN = "\u001B[36m";
        public static final String WHITE = "\u001B[37m";
    }

    /**Increases indentation level by 1*/
    public void indent() {
        indent++;
    }

    /**Decreases indentation level by 1 */
    public void outdent() {
        indent--;
    }
    
    /**
     * Parallels {@code System.out.println(Object x)} with custom indentation added
     * @param x - the Object to be printed
     */
    public void println(Object x) {
        println(x.toString());
    }

    /**
     * Parallels {@code System.out.println(String x)} with custom indentation added
     * @param x - the String to be printed
     */
    public void println(String x) {
        print(x + "\n");
    }

    /**Parallels {@code System.out.println()} with custom indentation added */
    public void println() {
        print("\n");
    }

    /**
     * Parallels {@code System.out.print(Object obj)} with custom indentation added
     * @param obj - the Object to be printed
     */
    public void print(Object obj) {
        print(obj.toString());
    }

    /**
     * Parallels {@code System.out.print(String s)} with custom indentation added
     * @param s - the String to be printed
     */
    public void print(String s) {
        System.out.print("\t".repeat(indent) + s);
    }

    /**
     * Colors text to be outputted in the terminal
     * @param text - the text to be colored
     * @return the text surrounded by color and reset escape sequences
     */
    public static String error(String text) {
        return Colors.RED + text + Colors.RESET;
    }
}
