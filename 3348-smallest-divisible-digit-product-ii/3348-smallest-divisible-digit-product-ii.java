import java.util.Arrays;

class Solution {

    // Prime factor counts for digits 0-9
    // Index i stores [cnt2, cnt3, cnt5, cnt7] for digit i
    private static final int[][] DIGIT_FACTORS = {
        {0, 0, 0, 0}, // 0
        {0, 0, 0, 0}, // 1
        {1, 0, 0, 0}, // 2
        {0, 1, 0, 0}, // 3
        {2, 0, 0, 0}, // 4
        {0, 0, 1, 0}, // 5
        {1, 1, 0, 0}, // 6
        {0, 0, 0, 1}, // 7
        {3, 0, 0, 0}, // 8
        {0, 2, 0, 0}  // 9
    };

    public String smallestNumber(String num, long t) {
        // Step 1: Factorize t into prime factors (2, 3, 5, 7)
        int[] req = new int[4]; // [c2, c3, c5, c7]
        long tempT = t;
        int[] primes = {2, 3, 5, 7};
        
        for (int i = 0; i < 4; i++) {
            while (tempT % primes[i] == 0) {
                req[i]++;
                tempT /= primes[i];
            }
        }
        
        // If t has prime factors other than 2, 3, 5, 7, impossible
        if (tempT > 1) {
            return "-1";
        }

        int n = num.length();
        int firstZero = num.indexOf('0');
        if (firstZero == -1) {
            firstZero = n;
        }

        // Count prime factors present in num's prefix
        int[] prefixPrimes = new int[4];
        for (int i = 0; i < n; i++) {
            int d = num.charAt(i) - '0';
            for (int p = 0; p < 4; p++) {
                prefixPrimes[p] += DIGIT_FACTORS[d][p];
            }
        }

        // Check if num itself is valid (no zeros and product divisible by t)
        if (firstZero == n && isSatisfied(req, prefixPrimes)) {
            return num;
        }

        // Step 2: Try preserving prefix up to index i and increasing digit at index i
        for (int i = n - 1; i >= 0; i--) {
            int d = num.charAt(i) - '0';

            // Subtract factors of current digit num[i]
            for (int p = 0; p < 4; p++) {
                prefixPrimes[p] -= DIGIT_FACTORS[d][p];
            }

            // Cannot diverge after the first zero digit
            if (i > firstZero) {
                continue;
            }

            int spaceAfter = n - 1 - i;

            for (int biggerDigit = d + 1; biggerDigit <= 9; biggerDigit++) {
                int[] remainingReq = new int[4];
                for (int p = 0; p < 4; p++) {
                    remainingReq[p] = Math.max(0, req[p] - prefixPrimes[p] - DIGIT_FACTORS[biggerDigit][p]);
                }

                int[] requiredDigits = getMinimalDigitCounts(remainingReq);
                int neededLen = totalDigits(requiredDigits);

                if (neededLen <= spaceAfter) {
                    int onesToFill = spaceAfter - neededLen;
                    StringBuilder sb = new StringBuilder();
                    sb.append(num, 0, i);
                    sb.append(biggerDigit);
                    for (int k = 0; k < onesToFill; k++) {
                        sb.append('1');
                    }
                    appendConstructedDigits(sb, requiredDigits);
                    return sb.toString();
                }
            }
        }

        // Step 3: Expand length if no same-length solution exists
        int[] requiredDigits = getMinimalDigitCounts(req);
        int minLen = totalDigits(requiredDigits);
        int targetLen = Math.max(n + 1, minLen);

        StringBuilder sb = new StringBuilder();
        for (int k = 0; k < targetLen - minLen; k++) {
            sb.append('1');
        }
        appendConstructedDigits(sb, requiredDigits);
        return sb.toString();
    }

    private boolean isSatisfied(int[] req, int[] avail) {
        for (int i = 0; i < 4; i++) {
            if (avail[i] < req[i]) return false;
        }
        return true;
    }

    // Convert required prime powers [c2, c3, c5, c7] into minimal counts of digits [1..9]
    private int[] getMinimalDigitCounts(int[] primeReq) {
        int c2 = primeReq[0];
        int c3 = primeReq[1];
        int c5 = primeReq[2];
        int c7 = primeReq[3];

        int[] digitCounts = new int[10];

        // 5s and 7s cannot be combined with other factors
        digitCounts[5] = c5;
        digitCounts[7] = c7;

        // Combine powers of 3 into 9s
        digitCounts[9] = c3 / 2;
        c3 %= 2;

        // Combine powers of 2 into 8s
        digitCounts[8] = c2 / 3;
        c2 %= 3;

        // Optimal combination for remaining c2 (< 3) and c3 (< 2)
        if (c2 == 2 && c3 == 1) {
            digitCounts[2]++;
            digitCounts[6]++;
        } else if (c2 == 2 && c3 == 0) {
            digitCounts[4]++;
        } else if (c2 == 1 && c3 == 1) {
            digitCounts[6]++;
        } else if (c2 == 1 && c3 == 0) {
            digitCounts[2]++;
        } else if (c2 == 0 && c3 == 1) {
            digitCounts[3]++;
        }

        return digitCounts;
    }

    private int totalDigits(int[] counts) {
        int total = 0;
        for (int cnt : counts) {
            total += cnt;
        }
        return total;
    }

    private void appendConstructedDigits(StringBuilder sb, int[] counts) {
        for (int d = 1; d <= 9; d++) {
            for (int k = 0; k < counts[d]; k++) {
                sb.append(d);
            }
        }
    }
}