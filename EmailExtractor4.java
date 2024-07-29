import java.io.*; 
import java.util.*;

/**
* Second attempt at building an Email extractor in Java. 
*
*
* @author: Natalie Ho
* @date: July 16, 2024 
**/ 

//First, try to read the file of emails 
public class EmailExtractor4 {

    //public static String content = "";
    //public static String line; 


    // Read line by line to look for From, To, Content
    // and write to the csv file
    public void extract() {
      String line;  
      String sender = null;
      String to = null;
      String content = "";
      
      int firstQuote;
      int secondQuote;
      String boundary;
      boolean inContent = false; 
      
      StringBuilder contentBuilder = new StringBuilder();
      StringBuilder sb = new StringBuilder();
      sb.append("From").append(",");
      sb.append("To").append(",");
      sb.append("Content").append("\n");

      try {
        FileReader fr = new FileReader("/Users/natalieho/Desktop/HelloAI/Admin.mbox/mbox");
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
       catch (Exception e) {
         System.err.println("An exception occurred.");
         e.printStackTrace();
       } 

        String filePath = "output.csv";

        // Write the content of the StringBuilder to the CSV file
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(filePath))) {
            writer.write(sb.toString());
            System.out.println("Data has been written to " + filePath);
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
        extractor.extract();

        System.out.println("Done reading emails!");


    } 

} 









