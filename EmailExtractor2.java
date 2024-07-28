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
public class EmailExtractor2 {

    public static String content = "";
    public static String line; 

    public String csv(String emailLine) {
    /**
    * This function  uses StringBuilder to append the information of From, To, and Content into a csv file. 
    **/
    //StringBuilder need to do... 

      //find the boundary

      //find where the first instance of boundary shows up 
          
      //capture the stuff in between the first & last boundary 
          
      //find last boundary 
    
    return emailLine;

      

    } 

    public  void  betweenBoundary(List<String> allBoundaries) {
    /**
    * This function collects the information between two boundaries.
    * In other words, the function returns the information of each email when passed in a list of all boundaries. 
    **/
        String readAgain; 


            //String readLineTwice = reader.readLine();
        
            for (String boundary : allBoundaries) {
          

                System.out.println("Boundary in list: " + boundary);
                System.out.println("**********************************");
                
                try {
                    //FileReader file = new FileReader("/Users/natalieho/Desktop/HelloAI/OneEmail.txt");
                    FileReader file = new FileReader("/Users/natalieho/Desktop/HelloAI/Admin.mbox/mbox");//put in name of file 
                    BufferedReader reader  = new BufferedReader(file); 
                    Scanner sc = new Scanner(System.in);

                        while ((readAgain  = reader.readLine()) != null) {
                  
                            System.out.println("Reading the line " + readAgain);

                            if (readAgain.contains(boundary)) {
                                System.out.println("TRUE");
                                //Go to the next line
                                System.out.println("Next line is " + readAgain);
                                //Keep adding this line into content, until line contains the boundary again
                                String readNext = reader.readLine(); 

                                    while (readNext != null && !readNext.contains(boundary)) {
                                        System.out.println("The line we need to save is: " + readNext);
    
                                        content += readNext + "\n"; 

                                        readNext = reader.readLine();

                                        //System.out.println("Content is " + content + "\n");

                                        //String readNextNext = reader.readLine();

                                    } 
                   System.out.println("CONTENT = " + content);
                              } 
                        }
                 }
               catch(Exception e) {
                  e.printStackTrace();
                  System.err.println("An exception occurred.");
                }

           }   
    }

    public void readEmailLines() {
    
      
      //String[]  email = new ArrayList<>();
      int firstQuote;
      int secondQuote;
      String boundary;
      List<String> allBoundaries = new ArrayList<>(); 
      String line;  
      String content = "";


      try {
        //FileReader fr = new FileReader("/Users/natalieho/Desktop/HelloAI/OneEmail.txt");
        FileReader fr = new FileReader("/Users/natalieho/Desktop/HelloAI/Admin.mbox/mbox");//put in name of file 
        BufferedReader br = new BufferedReader(fr);
      
        //create a new csv file 
        File csvFile = new File("/Users/natalieho/Desktop/HelloAI/Emails.csv");
        FileWriter outputfile = new FileWriter(csvFile); 
              
        String[] header = {"From", "To", "Content"};
        //writer.writeNext(header);
        
        //line = br.readLine();
        //System.out.println("The line is: " + line);
        while ((line = br.readLine()) != null) {
        //Now, identify lines with important information like From, Delivered To, and Content. 

          //Check that this works before tackling the CSV part. 
          if(line.startsWith("From: ")) {
             System.out.println("Yes! This line contains the sender: " + line);
              //email.add(line);
              //add to csv file 
              //writer.writeNext(line);
          } else if (line.startsWith("To: ")) {
              System.out.println("Yes! This line  contains who it is delivered to: " + line);
              //email.add(line); 
          } else if (line.startsWith("Content-Type:")) {
              //check if line contains boundary 
              if (line.contains("boundary")) { 
                firstQuote = line.indexOf('"');
                secondQuote = line.lastIndexOf('"');

            
                boundary = line.substring(firstQuote + 1, secondQuote); 
                System.out.println("The boundary is: " + boundary);
 
                //store boundaries somewhere 
                allBoundaries.add(boundary);
      
              } 

          }
          
     
        } //bcket for while 

       //Collect content in between boundary 

       /**
       Scanner sc = new Scanner(System.in);

        for (String boundaryPhrase : allBoundaries) {
            //System.out.println("Boundary in list: " + boundary);

            //Now that we have the boundary, if the line contains the boundary, need to start collecting content 
            if (line.contains(boundaryPhrase)) {
                //Go to the next line
                String nextLine = sc.nextLine();
                //Keep adding this line into content, until line contains the boundary again
                while ((nextLine.contains(boundaryPhrase)) == false) {
    
                 content = content + nextLine; 
                 System.out.println("CONTENT: " + content);
 
                } 
             
            } 

             
        }
        **/
        betweenBoundary(allBoundaries);
        //System.out.println(content); 


        //System.out.println(allBoundaries);
      
     // System.out.println("Reading the content between boundaries: "); 
      

      } catch (Exception e) {
         System.err.println("An exception occurred.");
         e.printStackTrace();
      } finally {
         System.out.println("Print this at the end.");
      }

      }


    public static void main (String[] args) {
      
        System.out.println("Hello");
        //create email object 
        System.out.println("Creating new email object: ");
        EmailExtractor2  email = new EmailExtractor2();

        System.out.println("Reading email lines: ");
        email.readEmailLines();

        System.out.println("Done reading emails!");


    } //bracket for main 



} //bracket for EmailExtracto2 









