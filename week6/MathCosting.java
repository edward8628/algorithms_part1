
/**
 * Write a description of class mathCosting here.
 * 
 * @author Feng Liu
 * @version (a version number or a date)
 */
public class MathCosting
{
    public static int mySqrt(int x) {
        if (x == 0 || x == 1) return x;

        int big = x;    // big cache the latest big mid
        int mid = x/2;

        while (true) {
            if (x/mid < mid) { // y*y is so big and caused Integer overflow
                big = mid;
                mid = big/2;
            }
            else if (x/mid > mid) {
                if ((big-mid)/2 == 0) break;
                mid = mid + (big-mid)/2;
            }
            else return mid;
        }

        return mid;
    }

    public static void main(String args[]) {
        int a = 321;
        int b = 0;
        double times = 1000000;

        Stopwatch addWatch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            b = a+a;
        }
        double addMath = addWatch.elapsedTime();
        addMath = addMath/times;
        StdOut.println("add time " + addMath);

        Stopwatch multiplyWatch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            b = a*a;
        }
        double multiplyMath = multiplyWatch.elapsedTime();
        multiplyMath = multiplyMath/times;
        StdOut.println("multiply time " + multiplyMath);

        Stopwatch sqrtWatch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            Math.sqrt(a);
        }
        double sqrtMath = sqrtWatch.elapsedTime();
        sqrtMath = sqrtMath/times;
        StdOut.println("Math.sqrt time " + sqrtMath);

        Stopwatch MySqrtWatch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            mySqrt(a);
        }
        double MySqrtMath = MySqrtWatch.elapsedTime();
        MySqrtMath = MySqrtMath/times;
        StdOut.println("mySqrt time " + MySqrtMath);

        Stopwatch atan2Watch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            Math.atan(a);
        }
        double atan2Math = atan2Watch.elapsedTime();
        atan2Math = atan2Math/times;
        StdOut.println("atan time " + atan2Math);

        Stopwatch powWatch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            Math.pow(a, a);
        }
        double powMath = powWatch.elapsedTime();
        powMath = powMath/times;
        StdOut.println("pow time " + powMath);

        Stopwatch sinWatch = new Stopwatch();
        for (int i = 0; i < times; i++) {
            Math.sin(a);
        }
        double sinMath = sinWatch.elapsedTime();
        sinMath = sinMath/times;
        StdOut.println("sin time " + sinMath);
    }
}
