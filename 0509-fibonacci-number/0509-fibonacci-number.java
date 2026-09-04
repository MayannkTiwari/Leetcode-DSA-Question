import java.util.*;
class Solution {
    
        HashMap<Integer,Integer> memo = new HashMap<>();
            public int fib(int n) {
                if(n==0){
                    return 0;
                }
                if(n==1){
                    return 1;
                }
                if(memo.containsKey(n)){
                    return memo.get(n);
                }
                int ans=fib(n-1)+fib(n-2);
                memo.put(n,ans);
                return ans;

    
        
    }
}