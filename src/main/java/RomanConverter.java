import com.github.chaosfirebolt.converter.RomanInteger;

public class RomanConverter {
    public static int romanToArabic(String text) {
        String romanNumber = text.split(" ")[1];
        return RomanInteger.parse(romanNumber).getArabic();
    }
}
