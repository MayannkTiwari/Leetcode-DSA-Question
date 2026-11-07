class Solution {
    void helper(char[] s,int i, int j){
        if(i>=j){
            return;
        }
        char temp=s[i];
        s[i]=s[j];
        s[j]=temp;
        helper(s,i+1,j-1);
    }

    public void reverseString(char[] s) {
        // Main obj=new Main();
        helper(s ,0,s.length-1);
    }
}