public class Main {
    public static boolean compareTwoStrings(String a, String b) {
        if (a.length() != b.length()) {
            return false;
        }
        for (int i = 0; i < a.length(); i++) {
            if (a.charAt(i) != b.charAt(i)) {
                return false;
            }
        }
        return true;
    }

    public static void main(String[] args) {
        EncDec e = new EncDec();
        String s = "hiii there encode function!";

        System.out.println(e.encode(s));
        System.out.println(e.decode(e.encode(s)));
        
    }
}
