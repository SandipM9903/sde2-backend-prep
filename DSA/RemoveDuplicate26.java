package DSA;

public class RemoveDuplicate26 {
    public int removeDuplicates(int[] nums) {
        int j = 1;
        for (int i = 1; i < nums.length; i++) {
            if (nums[i] != nums[i - 1]) {
                nums[j] = nums[i];
                j++;
            }
        }
        return j;
    }

    public static void main(String[] args) {
        int[] nums = { 1, 1, 2 };
        RemoveDuplicate26 r = new RemoveDuplicate26();
        int k = r.removeDuplicates(nums);
        System.out.println("Unique elements :" + k);
    }
}
