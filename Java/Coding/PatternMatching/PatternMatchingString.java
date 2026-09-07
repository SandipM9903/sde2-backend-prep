package PatternMatching;

public class PatternMatchingString {
    public static void main(String[] args) {
        Object name = "Sandip";
        
        if(name instanceof String){
            String str = (String) name;
            System.out.println(str.length());
        }

        //Modern Java introduced pattern matching for instanceof first. 
        //We can directly use the variable without explicit casting [String str = (String) name;]
        if(name instanceof String str){
            System.out.println(str.length());
        }
    }
}
