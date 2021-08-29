package com.sjanarth.dsutils;

import java.util.*;

public class StringUtils 
{
	public static Set<String> getAllSubstrings (String input)	{
		Set<String> substrs = new LinkedHashSet<String>();
		for (int i = 0; i < input.length(); i++) {
			for (int j = 1; j <= input.length() - i; j++)	{
				substrs.add(input.substring(i, i+j));
			}
		}
		return substrs;
	}
	
	public static Set<String> getAllSubsequences (String input)	{
		Set<String> substrs = getAllSubstrings(input);
		Set<String> subseqs = new LinkedHashSet<String>();
		subseqs.addAll(substrs);
		for (String sub : substrs)	{
			for (int k = 1; k < sub.length() - 1; k++)	{
				StringBuilder sb = new StringBuilder(sub);
				sb.deleteCharAt(k);
				subseqs.add(sb.toString());
			}
		}
		return subseqs;
	}
	
	public static String lcp (String s1, String s2)	{
		int n = Math.min(s1.length(), s2.length());
		for (int i = 0; i < n; i++) {
			if (s1.charAt(i) != s2.charAt(i))
				return s1.substring(0, i);
		}
		return s1.substring(0, n);
	}
	
	public static void main (String[] args)	{
		for (String s : getAllSubstrings ("abcd")) System.out.println(s);
		System.out.println("------------------------");
		for (String s : getAllSubsequences ("abcd")) System.out.println(s);
	}
}