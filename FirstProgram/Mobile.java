//1. Write a program to create a directory that contains the following information.
//(a) Name of a person
//(b) Address
//(c) Telephone Number (if available with STD code)
//(d) Mobile Number (if available)
//(e) Head of the family
//(f) Unique ID No.
//The program will support the following menu based activities:
//(a) Create a database entry (The Unique ID number must be unique for every entry, the telephone numbers of two or more persons can be same if and only if the head of family is same)
//(b) Edit an entry (Must be identified by only the Unique ID number)
//(c) Search by keyword (Any keyword may not be complete; even if the keyword matches partially with any field, the corresponding information must be displayed)


import java.io.*;
   
   public class  Mobile{
   
       static final String DEFAULT_FILENAME = "Mobile.dat";
   
       static PhoneDirectory directory;  
                                        
       public static void main(String[] args) {
       
          String fileName;  
          boolean done; 
             
          if (args.length == 0)
             fileName = DEFAULT_FILENAME;
          else
             fileName = args[0];
         
          readPhoneData(fileName);
          done = false;
          
          while (done == false) {
              TextIO.putln();
              TextIO.putln();
              TextIO.putln("Select the operation you want to perform:");
              TextIO.putln();
              TextIO.putln("     1.  Look up a phone number");
              TextIO.putln("     2.  Add an entry to the directory");
              TextIO.putln("     3.  Delete an entry from the directory");
              TextIO.putln("     4.  Change someone's phone number");
              TextIO.putln("     5.  Exit form this program.");
              TextIO.putln();
              TextIO.put("Enter the number of your choice: ");
              int menuOption = TextIO.getlnInt();
              switch (menuOption) {
                 case 1:
                    doLookup();
                    break;
                 case 2:
                    doAddEntry();
                    break;
                 case 3:
                    doDeleteEntry();
                    break;
                 case 4:
                    doModifyEntry();
                    break;
                 case 5:
                    done = true;
                    break;
                 default:
                    System.out.println("Illegal choice! Please try again.");
              } 
          } 
          
          if (directory.changed == true)
             writePhoneData(fileName);
             
          TextIO.putln("\nExiting program.");
       
       } // end main()
   
   
       static void readPhoneData(String fileName) 
	{
          TextReader in;  
          try {
             in = new TextReader( new FileReader(fileName) );
          }
          catch (Exception e) {
             in = null;
          }
          if (in == null) {
             TextIO.putln("\nThe file \"" + fileName + "\" does not exist.");
             TextIO.put("Do you want to create the file? ");
             boolean create = TextIO.getlnBoolean();
             if (create == false) {
                TextIO.putln("Program aborted.");
                System.exit(0);
             }
             directory = new PhoneDirectory();  //  empty phone directory.
             try {
                   // create the file.
                PrintWriter out = new PrintWriter( new FileWriter(fileName) );
                directory.save(out);
                if (out.checkError())
                   throw new Exception();
                TextIO.putln("Empty directory created.");
             }
             catch (Exception e) {
                TextIO.putln("Can't create file.");
                TextIO.putln("Program aborted.");
                System.exit(0);
             }
          }
          else {
                // Get the data.
             try {
                 directory = new PhoneDirectory();  // A new, empty directory.
                 directory.load(in);  // Try to load it with data from the file.
             }
             catch (Exception e) {
                TextIO.putln("An error occurred while read data from \"" + fileName + "\":");
                TextIO.putln(e.toString());
                TextIO.putln("Program aborted.");
                System.exit(0);
             }
          }
       }  
       
       
       static void writePhoneData(String fileName) {
            // Save the data from the phone directory to the specified file.
          PrintWriter out;
          try {
             out = new PrintWriter( new FileWriter(fileName) );
          }
          catch (Exception e) {
             TextIO.putln("\nCan't open file for output!");
             TextIO.putln("Changes have not been saved.");
             return;
          }
          directory.save(out);
          if (out.checkError()) {
             TextIO.putln("Some error occurred while saving data to a file.");
             TextIO.putln("Sorry, but your phone directory might be ruined");
          }
       }
       
       
       static void doLookup() {
          TextIO.putln("\nLook up the name: ");
          String name = TextIO.getln();
          String number = directory.numberFor(name);
          if (number == null)
             TextIO.putln("\nNo such name in the directory.");
          else
             TextIO.putln("\nThe number for " + name + " is " + number);
       }
       
       
       static void doAddEntry() {
          TextIO.putln("\nAdd entry for this name: ");
          String name = TextIO.getln();
          if (directory.numberFor(name) != null) {
             TextIO.putln("That name is already in the directory.");
             TextIO.putln("Use command number 4 to change the entry for " + name);
             return;
          }
          TextIO.putln("What is the number for " + name + "? ");
          String number = TextIO.getln();
          directory.addNewEntry(name,number);
       }
       
       
       static void doDeleteEntry() {
          TextIO.putln("\nDelete the entry for this name: ");
          String name = TextIO.getln();
          if (directory.numberFor(name) == null)
             TextIO.putln("There is not entry for " + name);
          else {
             directory.deleteEntry(name);
             TextIO.putln("Entry deleted.");
          }
       }
       
       
       static void doModifyEntry() {
          TextIO.putln("\nChange the number for this name: ");
          String name = TextIO.getln();
          if (directory.numberFor(name) == null) {
             TextIO.putln("That name is not in the directory.");
             TextIO.putln("Use command number 2 to add an entry for " + name);
             return;
          }
          TextIO.putln("What is the new number for " + name + "? ");
          String number = TextIO.getln();
          directory.updateEntry(name,number);
       }
   
   
   } 