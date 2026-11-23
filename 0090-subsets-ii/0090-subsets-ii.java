class Solution {
    void sub(int start ,int[] arr,List<Integer>list,List<List<Integer>>ans){
        
            ans.add(new ArrayList<>(list));
        
    for(int i= start;i<arr.length;i++){
        if(i>start && arr[i]==arr[i-1]){
            continue;
        }
        Arrays.sort(arr);
        list.add(arr[i]);
        sub(i+1,arr,list,ans);
        list.remove(list.size()-1);
        // sub(i+1,arr,list,ans);
        

    }
    }
    public List<List<Integer>> subsetsWithDup(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        sub(0, nums, list, ans);
        return ans;
        
    }
}