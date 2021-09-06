package com.sjanarth.dsutils.samples;

public class BasicSuffixArray
{
    public static String getLongestCommonSubstring (String s1, String s2)	{
        char[] delims = new char[] { '$', '.', '!', '@', '#', '%', '^', '&', '*', '-', '+', '=', ':', ';', ',', '/', '?'};;
        for (char delim : delims)
            if (!s1.contains(String.valueOf(delim)) && !s2.contains(String.valueOf(delim)))
                return getLongestRepeatedSubstring(s1 + delim + s2);
        return null;
    }

    public static String getLongestRepeatedSubstring (String s)	{
        String lrs = null;
        com.sjanarth.dsutils.BasicSuffixArray sa = new com.sjanarth.dsutils.BasicSuffixArray(s);
        System.out.println(sa.toString());
        int[] lcp = sa.getLCPArray();
        int maxVal = Integer.MIN_VALUE;
        int maxPos = -1;
        for (int i = 0; i < lcp.length; i++)    {
            if (maxVal < lcp[i])    {
                maxVal = lcp[i];
                maxPos = i;
            }
        }
        if (maxPos != -1)       {
            lrs = sa.getSuffix(maxPos).substring(0, maxVal);
        }
        return lrs;
    }

    public static void main (String[] args)	{
        System.out.println("Longest common substring (myself, yourself) = "+getLongestCommonSubstring("my$self", "yourself"));
        System.out.println("Longest repeated substring (mississippi) = "+getLongestRepeatedSubstring("mississippi"));
    }
}
