class Solution {
    public String maxSumOfSquares(int num, int sum) {
        if (sum > 9 * num) {
            return "";
        }

        StringBuilder result = new StringBuilder(num);

        int nines = sum / 9;
        int remainder = sum % 9;

        for (int i = 0; i < nines; i++) {
            result.append('9');
        }

        if (remainder > 0) {
            result.append(remainder);
        }

        while (result.length() < num) {
            result.append('0');
        }

        return result.toString();
    }
}