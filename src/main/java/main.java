import Classes.HTML;
import org.codehaus.jettison.json.JSONObject;


import java.io.*;
import java.util.Scanner;

public class main {
    public static void main(String[] args) {
        Scanner sc= new Scanner(System.in);
        System.out.print("Enter a .json file name: \n");
        String file = sc.nextLine();

        try
        {
            //BRANJE DATOTEKE
            //FileReader prebere datoteko ter jo String Builder napise v JSON string
            FileReader fileReader = new FileReader(file);
            StringBuilder jsonStringBuilder = new StringBuilder();

            int i;
            while((i = fileReader.read())!= -1) {
                jsonStringBuilder.append((char)i);
            }

            //JSON OBJECT je LinkedHashMap (importano z gradle), da se ohrani zaporedje elementov
            String jsonString = jsonStringBuilder.toString();
            JSONObject jsonFile = new JSONObject(jsonString);

            HTML html = new HTML(jsonFile);
            System.out.println(html.toString());

            //ZAPIS V DATOTEKO V ROOT FILE
            FileWriter fileWriter = new FileWriter("JSON to HTML.html");
            fileWriter.write(html.toString());

            //CLOSE FILE READERS
            fileReader.close();
            fileWriter.close();
        } catch (FileNotFoundException ex) {
            System.out.println("ERROR: file not" + file + "found");
            return;
        }
        catch (Exception ex) {
            System.out.println("ERROR: " + ex.toString());
            return;
        }
    }
}