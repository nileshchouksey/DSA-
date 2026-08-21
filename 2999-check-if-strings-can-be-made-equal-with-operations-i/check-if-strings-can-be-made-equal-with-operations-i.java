class Solution {
    public boolean canBeEqual(String s1, String s2) {
        
        
        if (!same(s1.charAt(0), s1.charAt(2),
                  s2.charAt(0), s2.charAt(2))) {
            return false;
        }

        
        if (!same(s1.charAt(1), s1.charAt(3),
                  s2.charAt(1), s2.charAt(3))) {
            return false;
        }

        return true;
    }

    private boolean same(char a, char b, char c, char d) {
        return (a == c && b == d) || (a == d && b == c);
    }
}