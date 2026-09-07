public class PatterMatching{
	public void checkNumber(int num){
		switch(num){
			case 1 :
			System.out.println("The number is 1");
			break;
			
			case 2 :
			System.out.println("The number is 2");
			break;
			
			case 3 :
			System.out.println("The number is 3");
			break;
			
			case 4 :
			System.out.println("The number is 4");
			
			default :
			System.out.println("Invalid number");
		}
	}
	
	public static void main(String[] args){
		PatterMatching pm = new PatterMatching();
		pm.checkNumber(1);
	}
}