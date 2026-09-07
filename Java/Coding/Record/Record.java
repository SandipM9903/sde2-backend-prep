public record Record(Long id,String name, String emailId) {

    //We can add method inside a Record
    public void displayName(){
        System.out.println("<" + name + ">" + "[" + emailId + "]");
    }

    //We can validate data in compact constructor
    public Record{
        if(name == null || emailId == null){
            throw new IllegalArgumentException("Name and emailId cannot be null");
        }
    }
}