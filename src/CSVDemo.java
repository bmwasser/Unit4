import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class CSVDemo {
    
    public static void main(String[] args) {
        System.out.println("Seating Chart by first name: ");
        makeSeatingChart("roster.csv","first");
        System.out.println("Seating Chart by last name: ");
        makeSeatingChart("roster.csv","last");
        System.out.println("Seating Chart by grade: ");
        makeSeatingChart("roster.csv","grade");
    }

    public static void makeSeatingChart(String filename, String order){
        String[][] roster = new String[25][3];
        String[][] seatingChart = new String[5][5];
        try (Scanner fileIn = new Scanner(new File(filename))) {
            int counter = 0;
            while (fileIn.hasNext() && counter < roster.length) {
                String line = fileIn.nextLine();
                roster[counter] = line.split(",");
                counter++;
            }
        } catch (FileNotFoundException e) {
            System.out.println(e);
        }
        if (order.equals("first")){
            for (int c = 0; c < seatingChart[0].length; c++){
                for (int r = 0; r < seatingChart.length; r++){
                    seatingChart[r][c] = findMin(roster, 0);
                }
            }
            for (int r = 0; r < seatingChart.length; r++) {
                for (int c = 0; c < seatingChart[0].length; c++) {
                    System.out.printf(" %10s ", seatingChart[r][c]);
                }
            System.out.println();
            }
        }
        if (order.equals("last")){
            for (int c = 0; c < seatingChart[0].length; c++){
                for (int r = 0; r < seatingChart.length; r++){
                    seatingChart[r][c] = findMin(roster, 1);
                }
            }
            for (int r = 0; r < seatingChart.length; r++) {
                for (int c = 0; c < seatingChart[0].length; c++) {
                    System.out.printf(" %10s ", seatingChart[r][c]);
                }
            System.out.println();
            }

        }
        if (order.equals("grade")){
            for (int c = 0; c < seatingChart[0].length; c++){
                for (int r = 0; r < seatingChart.length; r++){
                    seatingChart[r][c] = findMin(roster, 2);
                }
            }
            for (int r = 0; r < seatingChart.length; r++) {
                for (int c = 0; c < seatingChart[0].length; c++) {
                    System.out.printf(" %10s ", seatingChart[r][c]);
                }
            System.out.println();
            }
        }
        
    }

    public static String findMin(String[][] roster, int column){
        int minIndex = -1;
        String nameToReturn = null;
        if (column == 0 || column == 1){
            String minString = null;
            for (int i = 0; i < roster.length; i++){
                if (roster[i] != null && roster[i][column] != null){
                    String currentString = roster[i][column];
                    if (minString == null || currentString.compareTo(minString) < 0){
                        if (column == 0){
                            minString = currentString + " " + roster[i][1];
                        } else {
                            minString = roster[i][0] + " " + currentString;
                        }
                        
                        minIndex = i;
                    } 
                }    
            }
            if (minIndex != -1) {
                roster[minIndex][column] = null; 
            }
            
            return minString;

        } else if (column == 2){
            int minGrade = 0;
            boolean firstGradeFound = false;
            for (int i = 0; i < roster.length; i++){
                if (roster[i] != null && roster[i][column] != null){
                    int currentGrade = Integer.parseInt(roster[i][column]);

                    if (!firstGradeFound || currentGrade < minGrade){
                        minGrade = currentGrade;
                        minIndex = i;
                        nameToReturn = roster[i][0] + " " + roster[i][1];
                        firstGradeFound = true;
                    }
                }
            }
            if (minIndex != -1) {
                roster[minIndex] = null; 
            }
            return nameToReturn;
        }

        return "";
    }

}
