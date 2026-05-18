import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

@SuppressWarnings("resource")
public class UsernamePassword {

    public static void main(String[] args) {
        System.out.println("Your ULID username is: " + ulidGenerator());
        System.out.println(passwordChecker() + " is a secure password");
        
        String ulid = "bmwasse";
        if (checkDatabase(ulid)){
            ulid = ulid.substring(0, ulid.length() - 1) + "2";
        }
        System.out.println(ulid);
    }

    public static String ulidGenerator(){
        String first = "";
        Scanner in = new Scanner(System.in);
        while (first == ""){
            System.out.print("Type your first name: ");
            first = in.nextLine();
            
        }
        String shortFirst = first.substring(0,1);

        String shortMiddle = "";
        System.out.print("Type your middle name:");
        String middle = in.nextLine();  
        if (middle != ""){
            shortMiddle = middle.substring(0,1);
        }

        String last = "";
        String shortLast = "";
        while (last == ""){
            System.out.print("Type your last name: ");
            last = in.nextLine();
        }
        if (middle == "" && last.length() < 5){
            shortLast = last;
        } else if (middle == ""){
            shortLast = last.substring(0,6);
        } else if (last.length() > 5){
            shortLast = last.substring(0,5);
        } else {
            shortLast = last;
        }
        
        return (shortFirst + shortMiddle + shortLast).toLowerCase();
    }

    public static String passwordChecker() {
        Scanner in = new Scanner(System.in);
        String password = "";
        String confirmation = "";
        
        while (true) {
            boolean hasUpperCase = false;
            boolean hasLowerCase = false;
            boolean lengthValid = false;
            int amountNum = 0;
            boolean isValid = false;

            while (!isValid) {
                hasUpperCase = false;
                hasLowerCase = false;
                lengthValid = false;
                amountNum = 0;

                System.out.print("Type a secure password: ");
                password = in.nextLine();

                if (password.length() >= 10) {
                    lengthValid = true;
                }

                for (int i = 0; i < password.length(); i++) {
                    char ch = password.charAt(i);
                    if (ch >= 'a' && ch <= 'z') {
                        hasLowerCase = true;
                    } else if (ch >= 'A' && ch <= 'Z') {
                        hasUpperCase = true;
                    } else if (ch >= '0' && ch <= '9') {
                        amountNum++;
                    }
                }

                isValid = hasLowerCase && hasUpperCase && amountNum >= 2 && lengthValid;

                if (!isValid) {
                    // Print specific problems if invalid
                    System.out.println("Password doesn't meet criteria. Problems detected:");
                    if (!lengthValid) {
                        System.out.println("- Must be at least 10 characters long.");
                    }
                    if (!hasLowerCase) {
                        System.out.println("- Must contain a lowercase letter.");
                    }
                    if (!hasUpperCase) {
                        System.out.println("- Must contain an uppercase letter.");
                    }
                    if (amountNum < 2) {
                        System.out.println("- Must contain at least two numbers.");
                    }
                }
            }

            System.out.print("Re-enter password for confirmation: ");
            confirmation = in.nextLine();

            if (confirmation.equals(password)) {
                System.out.println("Password set successfully!");
                return confirmation;
            } else {
                System.out.println("Confirmation does not match. Restarting the entire password creation process.");
            }
        }
    }

    
    
    
    
    
    
    
    
    
    
    public static boolean checkDatabase(String newUlid){
        String filename = "ulids.txt"; // file must be in project folder
        try (Scanner fileIn = new Scanner(new File(filename))) {
            while (fileIn.hasNext()) {
                String line = fileIn.nextLine();
                if (line.equals(newUlid)) return true;
            } 
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        return false;
    }


}
