public class RecordMain{
    public static void main(String[] args) {
        Record recordData = new Record(1L,"Sandip", "sandip@gmail.com");
        //Here we are using toString() method.
        //In class file of Record class, toString() method is automatically overridden.
        System.out.println("Using toString() : " + recordData);

        //Here we are using equals() method.
        //In class file of Record class, equals() method is automatically overridden.
        Record recordData2 = new Record(2L, "Sandip", "sandip@gmail.com");
        System.out.println("Using equals() : " + recordData.equals(recordData2));

        //Here we are using hashCode() method.
        //In class file of Record class, hashCode() method is automatically overridden.
        System.out.println("Using hashCode() : " + recordData.hashCode());

        //Now we can call the defined method in Record class.
        recordData.displayName();

        //Now we can use compact constructor
        try{
            Record recordData3 = new Record(3L, null, "sandip@gmail.com");
            System.out.println(recordData3);
        }catch(IllegalArgumentException e){
            System.out.println(e.getMessage());
        }
    }
}