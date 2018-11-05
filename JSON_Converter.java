import java.util.*;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.*;
/* Converter to turn clone hero music files into Guitar Zhero JSON file 
Author: Derrick Gatewood
Date: 11/5/2018
 */

public class JSON_Converter
{
    public static void main(String args[]) throws IOException
    {
        Scanner in = new Scanner(System.in);
        System.out.println("Enter a filename that you want to convert.");
        System.out.println("Note that this file must be in the main directory of the java files.");
        //String fileName = in.nextLine();
        convertFile("notes.chart");

    }

    public static void convertFile(String fileName) throws IOException {
        File file = new File(fileName);
        Scanner scanner = new Scanner(file);
        String outputName = fileName.substring(0, fileName.indexOf(".")) + ".json";
        System.out.println("The output file name is: " + outputName);
        BufferedWriter out = new BufferedWriter(new FileWriter(outputName));
        boolean nextSection = true;
        boolean expertSingle = false;
        boolean song = false;
        boolean syncTrack = false;
        boolean events = false;
        out.write("{");
        out.newLine();
        while(scanner.hasNextLine())
        {
            String s = scanner.nextLine();
            System.out.println(s);
            String sStart = s.substring(0, s.indexOf("=") > 0 ? s.indexOf("=") : s.length());
            String sEnd = s.substring(s.indexOf("\"") > 0 ? s.indexOf("\"") : s.indexOf("=") > 0 ? s.indexOf("=") :0);
            if(expertSingle)
            {
                System.out.println(sStart.trim());
                if(!sStart.trim().equals("{") || !s.trim().equals("[ExpertSingle]") )
                    {
                System.out.println("Expert Single is true");
                out.write("\"noteTimes\" : {");
                out.newLine();
                out.write("\"note\" : [");
                System.out.println(s);
                sStart = s.substring(0, s.indexOf("=") > 0 ? s.indexOf("=") : s.length());
                int posFirstSpaceAfterEqual = s.indexOf(" ", s.indexOf("="));
                System.out.println("The first space after = " + posFirstSpaceAfterEqual);
                int posSecondSpaceAfterEqual = s.indexOf(" ", posFirstSpaceAfterEqual+1);
                System.out.println("The second space after = " + posSecondSpaceAfterEqual);
                String sType = s.substring(posFirstSpaceAfterEqual+1, posSecondSpaceAfterEqual);
                String sLength = s.substring(posSecondSpaceAfterEqual+1);
                sEnd = s.substring(s.indexOf("\"") > 0 ? s.indexOf("\"") : s.indexOf("=") > 0 ? s.indexOf("=") :0);
                //out.write({"value": "New", "onclick": "CreateNewDoc()"},
                out.write("{\"time\":"+ sStart + "\"type\":" + "\"" + sType.trim() + "\"" + 
                    "\"length\":" + "\"" + sLength.trim() + "\"}");
                }
            }
            else if(sStart.trim().equals("Name"))
            {
                out.write("\"songname\" : " + sEnd+",");
                out.newLine();
            }
            else if(sStart.trim().equals("Artist"))
            {
                out.write("\"artist\" : " + sEnd+",");
                out.newLine();
            }
            else if(sStart.trim().equals("Album"))
            {
                out.write("\"artist\" : " + sEnd+",");
                out.newLine();
            }
            else if(sStart.trim().equals("Year"))
            {
                out.write("\"year\" : " + sEnd+",");
                out.newLine();
            }
            else if(sStart.trim().equals("MusicStream"))
            {
                out.write("\"filename\" : " + sEnd+",");
                out.newLine();
            }
            else if(s.trim().equals("[SyncTrack]"))
            {
                syncTrack = true;
                song = false;
            }
            else if(s.trim().equals("[Events]"))
            {
                events = true;
                syncTrack = false;
            }
            else if(s.trim().equals("[ExpertSingle]"))
            {
                System.out.println("Inside expert single notes");
                expertSingle = true;
                events = false;

            }

            
            }


        out.close();
    }
}
