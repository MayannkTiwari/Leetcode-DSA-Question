import java.util.*;

class Solution {
    public int maxProduct(int n) {
        ArrayList<Integer> digits = new ArrayList<>();

        while (n > 0) {
            digits.add(n % 10);
            n /= 10;
        }

        int ans = 0;

        for (int i = 0; i < digits.size(); i++) {
            for (int j = i + 1; j < digits.size(); j++) {
                ans = Math.max(ans, digits.get(i) * digits.get(j));
            }
        }

        return ans;
    }
}