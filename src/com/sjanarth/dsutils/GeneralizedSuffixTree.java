package com.sjanarth.dsutils;

import java.util.Collection;
import java.util.Set;

public class GeneralizedSuffixTree extends BasicSuffixTree 
{
	public GeneralizedSuffixTree(String[] inputs) {
		super();
		for (int i = 0; i < inputs.length; i++)	{
			addWord(inputs[i]);
		}
	}
	
	public static String getLCS (String[] inputs)	{
		GeneralizedSuffixTree gst = new GeneralizedSuffixTree(inputs);
		LCSTrieWalker lcstw = new LCSTrieWalker(gst.getWords());
		lcstw.walkDfs(gst.getRoot());
		return lcstw.getLCS();
	}
	
	private static class LCSTrieWalker extends BasicTrieWalker 	
	{
		public LCSTrieWalker (Collection<String> ws) { words = ws; } 
		@Override
		public void processNode (BasicTrieNode node) {
			Set<Integer> sources = getSources(node);
			if (sources != null && sources.size() == words.size())	{
				String str = node.getString();
				if (lcs == null) lcs = str;
				else if (lcs.length() < str.length()) lcs = str;
			}
		}
		public String getLCS () { return lcs; }
		private String lcs = null;
		private Collection<String> words = null;
	}
	
	public static void main (String[] args)	{
		String[] words = new String[] { 
				"ball", "call", "fall", "hall", "mall", "tall", "wall", "ball",
				"barley", "bat", "battle", "batch", "bath", "beast", "beet", "bell", 
				"best", "book", "boot", "botch", "box", "broad", "bull", "bust", "but",
				"butt", "butler", "butter", "button"
			};
		GeneralizedSuffixTree gst = new GeneralizedSuffixTree(words);
		System.out.println(gst.toString());
	}
}