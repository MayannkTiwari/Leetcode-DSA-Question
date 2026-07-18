class Solution {
    public int findGCD(int[] nums) {
        int n = nums.length;
        int min = nums[0];
        int max = nums[0];
        for(int i=1;i<nums.length;i++){
            if(min>nums[i]){
                min=nums[i];
            }
            if(max<nums[i]){
                max=nums[i];
            
            }
        }
        for(int i=min;i>0;i--){
            if(max%i==0&&min%i==0){
                return i;
            }
        }
        return 1;
    }
}