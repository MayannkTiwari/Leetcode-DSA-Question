class Solution {
    private TreeSet<String> set = new TreeSet<>();

    public List<String> braceExpansionII(String expression) {
        dfs(expression);
        return new ArrayList<>(set);
    }

    private void dfs(String exp) {
        int close = exp.indexOf('}');

        if (close == -1) {
            set.add(exp);
            return;
        }

        int open = exp.lastIndexOf('{', close);

        String left = exp.substring(0, open);
        String middle = exp.substring(open + 1, close);
        String right = exp.substring(close + 1);

        for (String part : middle.split(",")) {
            dfs(left + part + right);
        }
    }
}