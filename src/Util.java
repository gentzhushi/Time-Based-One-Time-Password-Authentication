import java.math.BigInteger;

public class Util {
    public static double generateRandom(double lowEnd, double highEnd){
        return ((Math.random() * (highEnd - lowEnd)) + lowEnd);
    }

}
