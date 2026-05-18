import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

public class PasswordAnalysis{

    public static void main(String[] args) {
        analyzePasswords();
    }

    
    
    public static void analyzePasswords(){
        int[] lengthHist = new int[61];
        int [] upperHist = new int[26];
        int [] lowerHist = new int[26];
        int [] numHist = new int[10];
        String filename = "LeakedPasswordsTop100k.txt"; // file must be in project folder
        try (Scanner fileIn = new Scanner(new File(filename))) {
            while (fileIn.hasNext()) {
                String line = fileIn.nextLine();
                int lengthBin = line.length();
                lengthHist[lengthBin]++;
                for (int i = 0; i < line.length(); i++){
                    char character = line.charAt(i);
                    if (character >= '0' && character <= '9'){
                        int numBin = character - '0';
                        numHist[numBin]++;
                    } else if (character >= 'A' && character <= 'Z'){
                        int upperBin = character - 'A';
                        upperHist[upperBin]++;
                    } else if (character >= 'a' && character <= 'z'){
                        int lowerBin = character - 'a';
                        lowerHist[lowerBin]++;
                    }
                }
 
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        System.out.print("Password Lengths Histogram: ");
        printNormalized(lengthHist);
        System.out.print("Number Histogram: ");
        printNormalized(numHist);
        System.out.print("Lowercase Histogram: ");
        printNormalized(lowerHist);
        System.out.print("Uppercase Histogram: ");
        printNormalized(upperHist);
    }

    public static void printNormalized(int[] arr){
        int total = 0;
        for (int value: arr){
            total += value;
        }
        double[] normalizedArr = new double[arr.length];
        for (int i = 0; i < arr.length; i++){
            normalizedArr[i] = (double) arr[i] / total;
        }
        System.out.println(Arrays.toString(normalizedArr));
    }

}