import java.util.*;
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
        boolean hasPrinted = false;
        boolean firstPass = true;
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
                if(s.trim().equals("}"))
                {
                    expertSingle = false;
                }
                if(!(s.trim().equals("{") || s.trim().equals("}") || s.trim().equals("[ExpertSingle]")) )
                {
                    System.out.println("Expert Single is true");
                    if(!hasPrinted)
                    {
                        out.write("\"noteTimes\" : {");
                        out.newLine();
                        out.write("\"note\" : [");
                        out.newLine();
                        hasPrinted = true;
                    }
                    if(!firstPass)
                    {
                        out.write(",");
                        out.newLine();
                    }
                    System.out.println("The string evaluated is: " + s);
                    int posEquals = s.indexOf("=");
                    System.out.println("position of equals: " + posEquals);
                    sStart = s.substring(0, posEquals > 0 ? posEquals : s.length());
                    int posFirstSpaceAfterEqual = s.indexOf(" ", posEquals);
                    System.out.println("The first space after = " + posFirstSpaceAfterEqual);
                    int posSecondSpaceAfterEqual = s.indexOf(" ", posFirstSpaceAfterEqual+1);
                    int posThirdSpaceAfterEqual = s.indexOf(" ", posSecondSpaceAfterEqual+1);
                    System.out.println("The second space after = " + posSecondSpaceAfterEqual);
                    String sType = s.substring(posFirstSpaceAfterEqual+1, posSecondSpaceAfterEqual);
                    String sButton = s.substring(posSecondSpaceAfterEqual+1, posThirdSpaceAfterEqual);
                    String sLength = s.substring(posThirdSpaceAfterEqual+1);
                    sEnd = s.substring(s.indexOf("\"") > 0 ? s.indexOf("\"") : s.indexOf("=") > 0 ? s.indexOf("=") :0);
                    //out.write({"value": "New", "onclick": "CreateNewDoc()"},
                    out.write("{\"time\":"+ sStart + ",\"type\":" + "\"" + sType.trim() + "\""+ 
                        ",\"button\":"  + sButton.trim() +
                        ",\"length\":"  + sLength.trim() + "}");
                    firstPass = false;
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
                out.write("\"album\" : " + sEnd+",");
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
        out.write("]");
        out.newLine();
        out.write("}");
        out.newLine();
        out.write("}");
        out.close();
    }
}
