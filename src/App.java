public class App {
    
    public static void main(String[] args) throws Exception {
        String s = "eggs,bacon,  oranges, apples, pancakes, Clif bars";
        String[] tokens = s.split(",");
        System.out.println(tokens);
        for (int i = 0; i < tokens.length; i++) {
            tokens[i] = tokens[i].trim();
        }
        
        
        
        
        
        
        System.out.println("Done");
    }
}
