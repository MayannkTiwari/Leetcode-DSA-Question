class Solution {
    public boolean isPalindrome(String s) {
        String str = "";

        for (int n = 0; n < s.length(); n++) {
            if (Character.isLetterOrDigit(s.charAt(n))) {
                str = str + Character.toLowerCase(s.charAt(n));
            }
        }

        for (int i = str.length() - 1, j = 0; i >= j; i--, j++) {
            if (str.charAt(i) != str.charAt(j)) {
                return false;
            }
        }

        return true;
    }
}