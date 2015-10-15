import java.util.Arrays;
import java.io.PrintWriter;
import java.util.ArrayList;

/**
 * Write a description of class PlayGroundString here.
 * 
 * @author (your name) 
 * @version (a version number or a date)
 */
public class PlayGround
{   
    public static void stringSplit() {
        String s = "   a   b ";
        String[] words = s.trim().split("\\s+");
        String reverse = new String();

        for (int i = words.length-1; i >= 0; i--) {
            //if (words[i].equals("")) continue; //use \\s+ to remove this line
            reverse = reverse.concat(words[i]);
            if (i != 0) reverse = reverse.concat(" ");
        }
        System.out.println(reverse);
    }

    public static void Atoi() {
        String str = new String("feng liu");
        char[] charString = str.toCharArray();
        for (int i = 0; i < charString.length; i++) {
            System.out.println(charString[i]);
        }
    }

    public static void sortFile(String filename) {
        In in = new In(filename);
        //this will mistakenly cut a string having whitespace
        //String[] company = in.readAll().trim().split("\\s+");

        ArrayList<String> lines = new ArrayList<String>();
        while (!in.isEmpty()) {
            lines.add(in.readLine().trim());
        }
        String[] company = lines.toArray(new String[0]);
        in.close();

        Arrays.sort(company);

        Out out = new Out(filename);
        for (String str : company)
            out.println(str);
        out.close();
    }

    public static void main(String args[]) {
        if (args.length > 0) PlayGround.sortFile(args[0]);
    }
}
