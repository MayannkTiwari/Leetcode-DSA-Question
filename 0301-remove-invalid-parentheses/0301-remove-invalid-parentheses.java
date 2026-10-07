import java.util.*;

class Solution {

    private Set<String> result = new HashSet<>();

    public List<String> removeInvalidParentheses(String s) {

        int leftRemove = 0;
        int rightRemove = 0;

        // Find minimum number of '(' and ')' to remove
        for (char ch : s.toCharArray()) {

            if (ch == '(') {
                leftRemove++;
            } 
            else if (ch == ')') {

                if (leftRemove > 0) {
                    leftRemove--;
                } 
                else {
                    rightRemove++;
                }
            }
        }

        backtrack(s, 0, leftRemove, rightRemove, 0, new StringBuilder());

        return new ArrayList<>(result);
    }

    private void backtrack(
            String s,
            int index,
            int leftRemove,
            int rightRemove,
            int balance,
            StringBuilder current) {

        // Invalid state
        if (balance < 0) {
            return;
        }

        // End of string
        if (index == s.length()) {

            if (leftRemove == 0 &&
                rightRemove == 0 &&
                balance == 0) {

                result.add(current.toString());
            }

            return;
        }

        char ch = s.charAt(index);

        // Parentheses
        if (ch == '(') {

            // Option 1: Remove '('
            if (leftRemove > 0) {
                backtrack(
                    s,
                    index + 1,
                    leftRemove - 1,
                    rightRemove,
                    balance,
                    current
                );
            }

            // Option 2: Keep '('
            current.append(ch);

            backtrack(
                s,
                index + 1,
                leftRemove,
                rightRemove,
                balance + 1,
                current
            );

            current.deleteCharAt(current.length() - 1);
        }

        else if (ch == ')') {

            // Option 1: Remove ')'
            if (rightRemove > 0) {
                backtrack(
                    s,
                    index + 1,
                    leftRemove,
                    rightRemove - 1,
                    balance,
                    current
                );
            }

            // Option 2: Keep ')'
            if (balance > 0) {

                current.append(ch);

                backtrack(
                    s,
                    index + 1,
                    leftRemove,
                    rightRemove,
                    balance - 1,
                    current
                );

                current.deleteCharAt(current.length() - 1);
            }
        }

        else {
            // Normal character
            current.append(ch);

            backtrack(
                s,
                index + 1,
                leftRemove,
                rightRemove,
                balance,
                current
            );

            current.deleteCharAt(current.length() - 1);
        }
    }
}