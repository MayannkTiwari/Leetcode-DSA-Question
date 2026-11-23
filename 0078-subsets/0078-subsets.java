class Solution {
    void sub(int[] arr,int i,List<Integer>list,List<List<Integer>>ans){
        if(i>=arr.length){
            ans.add(new ArrayList<>(list));
            return;
        }
        list.add(arr[i]);
        sub(arr,i+1,list,ans);
        list.remove(list.size()-1); 
        sub(arr,i+1,list,ans);

    }
    public List<List<Integer>> subsets(int[] nums) {
        List<List<Integer>> ans = new ArrayList<>();
        List<Integer> list = new ArrayList<>();
        sub(nums,0,list,ans);
        return ans;
        
    }
}