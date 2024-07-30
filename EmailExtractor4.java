import java.io.*; 
import java.util.*;

/**
*  Building an Email extractor in Java. 
*
*
* @author: Natalie Ho
* @date: July 29, 2024 
**/ 

//First, try to read the file of emails 
public class EmailExtractor4 {

    private void extractKeyInformation(String inputFile, StringBuilder sb) throws IOException { 
      String line    = null;  
      String sender  = null;
      String to      = null;
      String content = "";
      
      boolean inContent = false; 
      
      StringBuilder contentBuilder = new StringBuilder();
 
      FileReader fr     = new FileReader(inputFile);
      BufferedReader br = new BufferedReader(fr); 

      while ((line = br.readLine()) != null) {
        if(line.startsWith("From ")) {
          if ( sender != null) {
            sb.append(sender).append(",").append(to).append(",").append(contentBuilder.toString()).append("\n"); 
          }
           contentBuilder.setLength(0);
           inContent = false; 
           sender = line;
        } else if (line.startsWith("To: ")) {
          to = line; 
        } else if ( line.startsWith("Content-Type: text") ) {
          inContent = true; 
        } else if (inContent == true) {
          contentBuilder.append(line);
        }
      }
    } 
   
    private void establishHeader(StringBuilder sb) {
      sb.append("From").append(",");
      sb.append("To").append(",");
      sb.append("Content").append("\n");
    }

    // Read line by line to look for From, To, Content
    // and write to the csv file
    public void extract(String inputFile, String outputFile) {
      StringBuilder sb  = new StringBuilder();
      establishHeader(sb);

      try {
        extractKeyInformation(inputFile, sb);

        BufferedWriter writer = new BufferedWriter(new FileWriter(outputFile));
        writer.write(sb.toString());

        System.out.println("Data has been written to " + outputFile);
      } catch (IOException e) {
          e.printStackTrace();
      }  
    }

    public static void main (String[] args) {
      
        System.out.println("Hello");
        //create email object 
        System.out.println("Creating new email object: ");
        EmailExtractor4  extractor = new EmailExtractor4();

        System.out.println("Reading email lines: ");
        extractor.extract("/Users/natalieho/Desktop/HelloAI/Admin.mbox/mbox", "email.csv");

        System.out.println("Done reading emails!");


    } 

} 









