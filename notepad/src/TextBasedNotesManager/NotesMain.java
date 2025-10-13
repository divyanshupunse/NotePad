package TextBasedNotesManager;


import java.io.*;
import java.util.Scanner;

public class NotesMain {
    public static void main(String[] args) throws Exception {
        Scanner sc = new Scanner(System.in);

        int choice;
        do {
            System.out.println("1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Search Note");
            System.out.println("4. Delete Note");
            System.out.println("5. Exit");

            choice = sc.nextInt();
            sc.nextLine();

            switch (choice) {
                case 1:
                    System.out.print("Enter title: ");
                    String title = sc.nextLine();
                    System.out.print("Enter category: ");
                    String category = sc.nextLine();
                    System.out.print("Enter content: ");
                    String content = sc.nextLine();
                    try(BufferedWriter br=new BufferedWriter(new FileWriter("KeepNotes.txt",true))){
                        br.write("Title:"+title+" Category:"+category+" Content:"+content);
                        br.newLine();
                    }catch (FileNotFoundException e){
                        System.out.println("File ot found"+e.getMessage());
                    }
                    System.out.println("Note added successfully");
                    break;
                case 2:
                    try (BufferedReader br = new BufferedReader(new FileReader("KeepNotes.txt"))) {
                        String line;
                        while ((line = br.readLine()) != null) {
                            System.out.println(line);
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    break;

                case 3:
                    boolean found=false;
                    System.out.print("Enter title to search: ");
                    String search=sc.nextLine();
                    try (BufferedReader br = new BufferedReader(new FileReader("KeepNotes.txt"))) {
                        String line;
                        while ((line= br.readLine()) !=null) {
                            if (line.toLowerCase().contains(search.toLowerCase())) {
                                System.out.println(line);
                                found = true;
                            }
                        }
                    } catch (IOException e) {
                        System.out.println("Error reading file: " + e.getMessage());
                    }
                    if (!found){
                        System.out.println("This title not found");
                    };

                    break;
                case 4:
                    System.out.print("Enter title to delete note: ");
                    String delete = sc.nextLine().trim().toLowerCase();
                    boolean found1 = false;

                    File inputFile = new File("KeepNotes.txt");
                    File tempFile = new File("TempNotes.txt");

                    try (BufferedReader br = new BufferedReader(new FileReader(inputFile));
                         BufferedWriter bw = new BufferedWriter(new FileWriter(tempFile))) {

                        String line;
                        while ((line = br.readLine()) != null) {
                            String[] parts = line.split("\\s+");
                            if (parts.length > 0) {
                                String titlePart = parts[0].split(":")[1].trim().toLowerCase();
                                if (titlePart.trim().equals(delete)) {
                                    found1 = true;
                                    continue; // skip line → delete
                                }
                            }
                            bw.write(line);
                            bw.newLine();
                        }

                    } catch (IOException e) {
                        System.out.println("Error: " + e.getMessage());
                    }

                    if (inputFile.delete()) {
                        boolean renamed = tempFile.renameTo(inputFile);
                        if (!renamed) {
                            System.out.println("Error: Could not rename temp file.");
                        }
                    }

                    if (found1) {
                        System.out.println("Note deleted successfully!");
                    } else {
                        System.out.println("Title not found.");
                    }
                    break;
                case 5:
                    System.out.println("Exiting...");
                    break;

                default:
                    System.out.println("You enter wrong choice");
            }
        }while (choice!=5);
    }
}
