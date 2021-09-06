package com.sjanarth.dsutils;

import java.util.Arrays;
import java.util.Comparator;

/**
 * A basic implementation of a suffix array.
 *
 * The construction of the suffix array uses a O(n^2) algorithm but can be
 * easily overridden by supplying custom implementations of the construct method.
 * This can be particularly useful for large datasets or if the input text is &gt; 500 chars.
 *
 * More info:
 * https://en.wikipedia.org/wiki/Suffix_array
 * http://www.stanford.edu/class/cs97si/suffix-array.pdf
 */
public class BasicSuffixArray
{
	/**
	 * Construct a basic suffix array.
	 * @param text input text to build the suffix array out of
	 */
	public BasicSuffixArray (String text) {
		construct(text);
	}

	/**
	 * Returns the internal suffix array.
	 * @return an array of int representing the sorted indices of the suffixes.
	 */
	public int[] getSuffixArray()	{
		return sa;
	}

	/**
	 * Returns the longest common prefix array.
	 * @return an array of int representing the lengths of the longest common
	 * 			prefixes of adjacent elements in the sorted suffixes array.
	 * 		    The first element is always initialized with 0.
	 */
	public int[] getLCPArray()	{
		return lcp;
	}

	/**
	 * Returns the suffix at index i.
	 * @param i index into the sorted suffixes array.
	 * @return String representing the suffix at index i in the sorted suffixes array.
	 */
	public String getSuffix (int i)	{
		return text.substring(sa[i]);
	}

	@Override
	public String toString ()	{
		StringBuilder sb = new StringBuilder();
		sb.append("text=["); sb.append(text); sb.append("]\n");
		sb.append("sa="); sb.append(toString(sa)); sb.append("\n");
		sb.append("lcp="); sb.append(toString(lcp)); sb.append("\n");
		sb.append("suffixes={\n");
		for (int i = 0; i < sa.length; i++) {
			sb.append("    ");
			sb.append(getSuffix(i));
			sb.append(",\n");
		}
		return sb.toString();
	}
	
	protected String toString(int[] arr) {
		return Arrays.toString(arr).
					replaceAll(" ", "").
					replaceAll("\\[", "\\{").
					replaceAll("\\]", "\\}");
	}
	
	/*
	 * This is a basic o(n^2) algorithm to construct a Suffix Array.
	 * There are faster o(nlog^2n), o(nlogn) and o(n) variants available.
	 */
	protected void construct (String text)	{
		this.text = text;
		sa = new int[text.length()];
		lcp = new int[text.length()];
		// temporary space, will get freed up
		Suffix[] suffixes = new Suffix[text.length()];
		// gather all suffixes
		for (int i = 0; i < this.text.length(); i++)
			suffixes[i] = new Suffix(this.text.substring(i), i);
		//System.out.println("Listing all suffixes");
		//for (Suffix s : suffixes) System.out.println(s);
		// sort them
		Arrays.sort(suffixes, Suffix.getComparator());
		//System.out.println("After sorting");
		//for (Suffix s : suffixes) System.out.println(s);
		//System.out.println("Showing sorted suffixes");
		//for (int i = 0; i < suffixes.length; i++)
			//System.out.println(i+": "+suffixes[i]);
		// build the suffix and lcp arrays
		lcp[0] = 0;
		for (int i = 1; i < text.length(); i++)	{
			sa[i-1] = suffixes[i-1].position;
			lcp[i] = StringUtils.lcp(suffixes[i-1].suffix, suffixes[i].suffix).length();
			//System.out.println("LCP ("+suffixes[i-1]+", "+suffixes[i]+") = "+StringUtils.lcp(suffixes[i-1].suffix, suffixes[i].suffix));
			suffixes[i-1] = null;	// free up
		}
		sa[text.length()-1] = suffixes[text.length()-1].position;
		suffixes[text.length()-1] = null;	// free up
	}
	
	private String text = null;
	private int[] sa = null;
	private int[] lcp = null;

	/**
	 * Nested class representing a single suffix of a given String.
	 */
	protected static class Suffix  
	{
		public Suffix (String s, int pos) { suffix = s; position = pos; }
		public String toString() { return "{"+suffix+","+position+"}"; }
		public static Comparator<Suffix> getComparator () { return comp; }
		
		protected String suffix;
		protected Integer position = -1;
		protected static Comparator<Suffix> comp = Comparator.comparing(o -> o.suffix);
	}
}