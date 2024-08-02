package org.example;

import java.io.FileWriter;
import java.util.Properties;

import jakarta.mail.*;
import jakarta.mail.internet.MimeMultipart;

import com.opencsv.CSVWriter;


public class ClaudeEmailParser {

    public static void log(String toPrint) {
        System.out.println(toPrint);
    }

    public static void main(String[] args) {
        // Email account credentials
        String username = "nh104@wellesley.edu";
        String password = "wiyr ndxd esvw tjgc";

        // Gmail IMAP settings
        String host = "imap.gmail.com";
        String port = "993";

        Properties properties = new Properties();
        properties.put("mail.imap.host", host);
        properties.put("mail.imap.port", port);
        properties.put("mail.imap.starttls.enable", "true");
        properties.put("mail.imap.ssl.trust", host);

        try {
            // Create session
            Session session = Session.getInstance(properties);
            Store store = session.getStore("imaps");
            store.connect(host, username, password);

            // Open the inbox folder
            Folder inbox = store.getFolder("[Gmail]/Important");
            inbox.open(Folder.READ_ONLY);

            // Get messages
            Message[] messages = inbox.getMessages();

            // Prepare CSV writer
            CSVWriter writer = new CSVWriter(new FileWriter("emails.csv"));
            String[] header = {"From", "To", "Content"};
            writer.writeNext(header);

            int count = 0;
            log("Total number of messages: " + messages.length);
            // Process each message
            for (Message message : messages) {

                log(""+count++);
                String from = message.getFrom()[0].toString();
                String to = message.getAllRecipients()[0].toString();
                String content = getTextFromMessage(message);

                String[] data = {from, to, content};
                writer.writeNext(data);
            }

            // Close resources
            writer.close();
            inbox.close(false);
            store.close();

            System.out.println("Email data has been exported to emails.csv");

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String getTextFromMessage(Message message) throws Exception {
        if (message.isMimeType("text/plain")) {
            return message.getContent().toString();
        } else if (message.isMimeType("multipart/*")) {
            MimeMultipart mimeMultipart = (MimeMultipart) message.getContent();
            return getTextFromMimeMultipart(mimeMultipart);
        }
        return "";
    }

    private static String getTextFromMimeMultipart(MimeMultipart mimeMultipart) throws Exception {
        StringBuilder result = new StringBuilder();
        int count = mimeMultipart.getCount();
        for (int i = 0; i < count; i++) {
            BodyPart bodyPart = mimeMultipart.getBodyPart(i);
            if (bodyPart.isMimeType("text/plain")) {
                result.append("\n").append(bodyPart.getContent());
                break; // without break, same text appears twice in my tests
            } else if (bodyPart.isMimeType("text/html")) {
                String html = (String) bodyPart.getContent();
                result.append("\n").append(org.jsoup.Jsoup.parse(html).text());
            } else if (bodyPart.getContent() instanceof MimeMultipart) {
                result.append(getTextFromMimeMultipart((MimeMultipart) bodyPart.getContent()));
            }
        }
        return result.toString();
    }
}