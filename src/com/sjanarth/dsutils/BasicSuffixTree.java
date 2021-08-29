package com.sjanarth.dsutils;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

public class BasicSuffixTree extends BasicTrie
{
	private static final String TRIENODE_PROPERTY_SOURCE = "src";
	private static final String TRIENODE_PROPERTY_SUFFID = "sid";
	
	public BasicSuffixTree() {
		this (null);
	}
	
	public BasicSuffixTree(String input) {
		words = new HashMap<Integer,String>();
		addWord(input);
	}
	
	public void addWord (String word)	{
		if (word != null)	{
			Integer id = words.size() + 1;
			words.put(id, word);
			for (int i = 0; i < word.length(); i++)	{
				BasicTrieNode node = add(word.substring(i));
				setProps (node, id, i);
			}
		}
	}
	
	public Collection<String> getWords ()	{
		return words.values();
	}
	
	@SuppressWarnings("unchecked")
	public static Set<Integer> getSources (BasicTrieNode node)	{
		return (Set<Integer>) node.getProperty(TRIENODE_PROPERTY_SOURCE);
	}
	
	private void setProps (BasicTrieNode node, Integer src, Integer sid) {
		while (node != null)	{
			node.addProperty(TRIENODE_PROPERTY_SOURCE, src);
			node = node.getParent();
		}
		/*
		List<Integer> sids = (List<Integer>) node.getProperty(TRIENODE_PROPERTY_SUFFID) ;
		if (sids == null) sids = new ArrayList<Integer>();
		sids.add(sid);
		node.setProperty(TRIENODE_PROPERTY_SUFFID, sids);
		*/
	}

	private Map<Integer,String> words = null;
	
	public static void main(String[] args)	{
		BasicSuffixTree st = new BasicSuffixTree("banana");
		System.out.println(st.toString());
	}
}