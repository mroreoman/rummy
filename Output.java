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

    public void indent() {
        println();
        indent++;
    }

    public void outdent() {
        indent--;
    }
    
    public void println(Object o) {
        println(o.toString());
    }

    public void println(String text) {
        print(text + "\n");
    }

    public void println() {
        print("\n");
    }

    public void print(Object o) {
        print(o.toString());
    }

    public void print(String text) {
        System.out.print("\t".repeat(indent) + text);
    }

    public static String error(String text) {
        return Colors.RED + text + Colors.RESET;
    }
}
