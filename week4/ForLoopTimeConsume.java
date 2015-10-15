
/**
 * Write a description of class forLoopTimeConsume here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class ForLoopTimeConsume
{
    final int LENGTH = 5000000;
    int[] temp = new int[LENGTH];
    int startTime = 0;
    int endTime = 0;

    public ForLoopTimeConsume () {
        //initial array
        for (int i = 0; i < LENGTH; i++) {
            this.temp[i] = i;
        }
    }

    //#1. get length in for
    public void test1() {
        Stopwatch watchOne = new Stopwatch();
        for (int i = 0; i < temp.length; i++) {
            int something = temp[i]*temp[i]*temp[i]*temp[i];
        }
        double timeOne = watchOne.elapsedTime();
        System.out.println("#1 " + timeOne);
    }

    //#2. get length before for
    public void test2() {
        Stopwatch watchTwo = new Stopwatch();
        int length = temp.length;
        for (int i = 0; i < length; i++) {
            int something = temp[i]*temp[i]*temp[i]*temp[i];
        }
        double timeTwo = watchTwo.elapsedTime();
        System.out.println("#2 " + timeTwo);
    }

    //#3. foreach
    public void test3() {
        Stopwatch watchThree = new Stopwatch();
        for (int i : temp) {
            int something = i*i*i*i;
        }
        double timeThree = watchThree.elapsedTime();
        System.out.println("#3 " + timeThree);
    }

    public static void main(String[] args) {
        ForLoopTimeConsume object = new ForLoopTimeConsume();
        object.test2();
        object.test3();
        object.test1();

        /**
         * each of the 3 method of for loop doesnt significatively optimalize the running time
         */
    }
}
