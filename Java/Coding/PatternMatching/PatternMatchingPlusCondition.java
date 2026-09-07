package PatternMatching;

public class PatternMatchingPlusCondition {
    public String checkCondition(Object num){
		return switch(num){
			case Integer i when i > 0 ->
			"Positive Number";
			
			case Integer i when i < 0 ->
			"Negative Number";
			
			default ->
			"Zero or Non-Integer";
		};
	}
	
	public static void main(String[] args){
		PatternMatchingPlusCondition pmpc = new PatternMatchingPlusCondition();
		System.out.println(pmpc.checkCondition(10));
        System.out.println(pmpc.checkCondition(-10));
        System.out.println(pmpc.checkCondition(0));
        System.out.println(pmpc.checkCondition(10.0));
	}
}