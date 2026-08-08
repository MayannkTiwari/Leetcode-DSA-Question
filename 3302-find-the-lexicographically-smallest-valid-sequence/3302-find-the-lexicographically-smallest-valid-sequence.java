class Solution {
    public int[] validSequence(String word1, String word2) {
        int n = word1.length();
        int m = word2.length();

        // next[i] = first position >= i where word1[pos] == word2[j]
        int[] next = new int[n + 1];
        int pos = n;

        int[] suf = new int[n + 1];

        pos = m - 1;
        for (int i = n - 1; i >= 0; i--) {
            if (pos >= 0 && word1.charAt(i) == word2.charAt(pos)) {
                pos--;
            }
            suf[i] = m - 1 - pos;
        }

        // Try to build the lexicographically smallest sequence.
        int[] ans = new int[m];
        int j = 0;
        boolean changed = false;
        int start = 0;

        while (j < m) {
            boolean found = false;

            for (int i = start; i < n; i++) {
                if (word1.charAt(i) == word2.charAt(j)) {
                    ans[j] = i;
                    start = i + 1;
                    j++;
                    found = true;
                    break;
                }

                // We can use at most one mismatch.
                if (!changed && suf[i + 1] >= m - j - 1) {
                    ans[j] = i;
                    changed = true;
                    start = i + 1;
                    j++;
                    found = true;
                    break;
                }
            }

            if (!found) {
                return new int[0];
            }
        }

        return ans;
    }
}