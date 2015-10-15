/*************************************************************************
 *
 * Compilation:  javac ReadFile.java
 * Execution: java ReadFile input6.txt
 * Dependencies: none
 *
 *
 *************************************************************************/

import java.io.*;
import java.util.Scanner;

/**
 * read a file from terminal command
 * 
 * @author Feng Liu 
 * @version 08/17/2015
 */
public class ReadFile
{
    public static void main(String args[]) throws IOException
    {
        String filename = args[0];
        File file = new File(filename);
        Scanner sc = new Scanner(file);

        while (sc.hasNextLine())
        {
            String line = sc.nextLine();
            System.out.println(line);
        }

        sc.close();	
    }
}
