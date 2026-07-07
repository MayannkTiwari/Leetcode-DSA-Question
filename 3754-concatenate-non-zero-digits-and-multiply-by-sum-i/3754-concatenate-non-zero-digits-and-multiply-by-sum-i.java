class Solution {
    public long sumAndMultiply(int n) {

        String num = Integer.toString(n);
        StringBuilder sb = new StringBuilder();

        
        for (int i = 0; i < num.length(); i++) {
            char ch = num.charAt(i);

            if (ch != '0') {
                sb.append(ch);
            }
        }

        
        if (sb.length() == 0) {
            return 0;
        }

        long x = Long.parseLong(sb.toString());

        
        long sum = 0;
        long temp = x;

        while (temp > 0) {
            sum += temp % 10;
            temp /= 10;
        }

        return x * sum;
    }
}