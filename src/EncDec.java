import java.util.ArrayList;


public class EncDec {
    private String alphabet = "";
    
    public EncDec() {
        this.alphabet = " !\"#$%&\'()*+,-./0123456789:;<=>?@ABCDEFGHIJKLMNOPQRSTUVWXYZ[\\]^_`abcdefghijklmnopqrstuvwxyz{|}~";
    }

    public EncDec(String alphabet) {
        this.alphabet = alphabet;
    }

    public String getAlphabet() {
        return this.alphabet;
    }

    public void setAlphabet(String alphabet) {
        this.alphabet = alphabet;
    }

    // Naive encoder converts each character to an integer value -32 and stores it in an ArrayList
    // Starting at 32 begins at ' ' and ends at the last printable character '~'
    public ArrayList<Integer> encode(String s) {
        ArrayList<Integer> encoded = new ArrayList<Integer>();
        for (int i = 0; i < s.length(); i++) {
            encoded.add((int) s.charAt(i)-32);
        }
        return encoded;
    }

    // Naive decoder converts each integer value to a character by adding 32
    // reverses encoder()
    public String decode(ArrayList<Integer> encoded) {
        String decoded = "";
        for (int i = 0; i < encoded.size(); i++) {
            decoded += (char) (encoded.get(i)+32);
        }
        return decoded;
    }
}