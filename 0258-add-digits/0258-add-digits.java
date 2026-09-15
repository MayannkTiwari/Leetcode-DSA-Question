class Solution {
    public int addDigits(int num) {
    // int sum =0;
    while(num>9){
        int sum=0;
    
    while(num>0){
        int digit=num%10;
        sum=digit+sum;
        num=num/10;
    }
    num=sum;
    }
      return num;
    }
}