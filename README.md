# Hello AI 🌎


<details><summary> Hello World </summary>

# Week 1 
A program in Java that prints "Hello World!!!!!!"
  
## How to set up environment 
* Download OpenJDK Java 17 with the dmg package. I am using macOS. 
* Double click on the application and click install.
* You can double check that java and its compiler are installed by running this in your terminal
  ```java --version```
 ``` javac --version```

## How to compile 
I used VSCode to write the Hello World program. I can now compile it, or translate the code so the computer can understand the instructions. 

To compile 
```
javac HelloWorld.java
```
You can check that the program has been compiled by calling for the list of directories. 
```
ls
```

## How to run the program 

```
java HelloWorld 
```

## Potential issues 
* Be careful and remember to name the file with the extension ".java"
* I had worked on a file called "Hello World", realized my mistake, but kept working on the wrong file! As a result, I kept trying to instantiate a class on an empty file.
* Other potential issues include not having compatible versions of java and javac. 

</details>

<details> <summary>Email Extractor</summary>

# Week 2 
A program in java that takes a mbox file of downloaded emails and retrieves information of who the email is from, who it is sent to, and the content of each email into a csv file. 

## Download emails from mailbox 
I downloaded my emails from my apple inbox simply by selecting one of my inboxes --> mailbox --> export mailbox 

## The mbox file 
It's important to understand the format of the mbox file. I didn't do this at first and it took a lot longer! 

Each email has a different Content-type. I ignored contents with image and video files and only looked for contents with plain text or html. 

## OOP 
I created three functions under the EmailExtractor4 class: 
1. extractKeyInformation - takes in an input file path and a StringBuilder to look for "From: ", "To: ", and Content.
2. establishHeader - takes in a String Builder to establish the header for the csv file. 
3. extract - takes in an input and output file path to call helper functions and write content into the csv file. 


 </details>
