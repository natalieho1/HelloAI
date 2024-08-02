
/**
 * A program that retrieves emails from my Gmail inbox and collects key content using JakartaMail API. The content is written into a csv file.
 *
 * @author: Natalie Ho
 * @date: Tuesday, July 30 2024
 */
import java.io.*;
import java.util.*;

import jakarta.activation.DataHandler;
import jakarta.mail.*;

public class EasyEmailParser implements Part {
    private static final String email    = "nh104@wellesley.edu";
    private static final String password = "wiyr ndxd esvw tjgc";

    @Override
    public int getSize() throws MessagingException {
        return 0;
    }

    @Override
    public int getLineCount() throws MessagingException {
        return 0;
    }

    @Override
    public String getContentType() throws MessagingException {
        return "";
    }

    @Override
    public boolean isMimeType(String s) throws MessagingException {
        return false;
    }

    @Override
    public String getDisposition() throws MessagingException {
        return "";
    }

    @Override
    public void setDisposition(String s) throws MessagingException {

    }

    @Override
    public String getDescription() throws MessagingException {
        return "";
    }

    @Override
    public void setDescription(String s) throws MessagingException {

    }

    @Override
    public String getFileName() throws MessagingException {
        return "";
    }

    @Override
    public void setFileName(String s) throws MessagingException {

    }

    @Override
    public InputStream getInputStream() throws IOException, MessagingException {
        return null;
    }

    @Override
    public DataHandler getDataHandler() throws MessagingException {
        return null;
    }


    @Override
    public Object getContent() throws IOException, MessagingException {
        return null;
    }

    @Override
    public void setDataHandler(DataHandler dataHandler) throws MessagingException {

    }

    @Override
    public void setContent(Object o, String s) throws MessagingException {

    }

    @Override
    public void setText(String s) throws MessagingException {

    }

    @Override
    public void setContent(Multipart multipart) throws MessagingException {

    }


    @Override
    public void writeTo(OutputStream outputStream) throws IOException, MessagingException {

    }

    @Override
    public String[] getHeader(String s) throws MessagingException {
        return new String[0];
    }

    @Override
    public void setHeader(String s, String s1) throws MessagingException {

    }

    @Override
    public void addHeader(String s, String s1) throws MessagingException {

    }

    @Override
    public void removeHeader(String s) throws MessagingException {

    }

    @Override
    public Enumeration<Header> getAllHeaders() throws MessagingException {
        return null;
    }

    @Override
    public Enumeration<Header> getMatchingHeaders(String[] strings) throws MessagingException {
        return null;
    }

    @Override
    public Enumeration<Header> getNonMatchingHeaders(String[] strings) throws MessagingException {
        return null;
    }

    private static void readAndWriteMessages(String outputFileName) {
        Store store = null;
        Folder folder = null;

        try {
            store = getImapStore();
            folder = getFolderFromStore(store, "[Gmail]/Starred");

            Message[] messages = folder.getMessages();

            StringBuilder contentBuilder = new StringBuilder();
            makeHeader(contentBuilder);

            System.out.println("Entering loop");
            for (Message message: messages) {
                System.out.println("Check if message is right Content type");
                getContentFromMessage(contentBuilder, message);
            }
            writeCSV(contentBuilder, outputFileName);


        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            closeFolder(folder);
            closeStore(store);
        }
    }

    private static Store getImapStore() throws Exception {
        Session session = Session.getInstance(getImapProperties());
        Store store     = session.getStore("imaps");

        store.connect("imap.gmail.com", email, password);

        return store;
    }

    private static Properties getImapProperties() {
        Properties props = new Properties();
        props.put("mail.imaps.host", "imap.gmail.com");
        props.put("mail.imaps.ssl.trust", "imap.gmail.com");
        props.put("mail.imaps.port", "993");
        props.put("mail.imaps.starttls.enable", "true");
        props.put("mail.imaps.connectiontimeout", "10000");
        props.put("mail.imaps.timeout", "10000");
        return props;
    }

    private static Folder getFolderFromStore(Store store, String folderName) throws MessagingException {
        Folder folder = store.getFolder(folderName);
        folder.open(Folder.READ_ONLY);
        return folder;
    }

    private static void makeHeader(StringBuilder sb) {
        sb.append("From").append(",");
        sb.append("To").append(",");
        sb.append("Message").append("\n");
    }

    private static void getContentFromMessage(StringBuilder sb, Message part) throws MessagingException, IOException {
        if (part.isMimeType("text/plain") || part.isMimeType("text/html")) {
            System.out.println("Yes message is right type");
            Address[] from      = part.getFrom();
            Address[] recipient = part.getAllRecipients();
            sb.append(Arrays.toString(from)).append(",").append(Arrays.toString(recipient)).append(",").append(part.getContent()).append("\n");
            //System.out.println("Getting content from message: " + sb);
        }
    }

    private static void writeCSV(StringBuilder sb, String outputFileName) throws IOException {
        try {
            BufferedWriter writer = new BufferedWriter( new FileWriter(outputFileName) );
            System.out.println("StringBuilder says " + sb);
            writer.write(sb.toString());

            System.out.println("Data has been written to " + outputFileName);
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    private static void closeFolder (Folder folder) {
        if (folder != null && folder.isOpen()) {
            try {
                folder.close(true);
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }

    private static void closeStore(Store store) {
        if (store != null && store.isConnected()) {
            try {
                store.close();
            } catch (MessagingException e) {
                e.printStackTrace();
            }
        }
    }
    public static void main(String[] args) {
        System.out.println("Begin reading inbox");

        readAndWriteMessages("/Users/natalieho/Desktop/HelloAI/emailsFromGmail.csv");

        System.out.println("LFG!");
    }
}