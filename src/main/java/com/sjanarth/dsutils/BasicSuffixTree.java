package com.sjanarth.dsutils;

import java.util.*;

/**
 * A basic implementation of the Suffix Tree data structure.
 *
 * A suffix tree represents all the suffixes of a given string as a single trie data structure
 * and can be used to perform prefix searches (auto complete) in O(log n) or lesser time.
 *
 * More info:
 * https://en.wikipedia.org/wiki/Suffix_tree
 * https://en.wikipedia.org/wiki/Generalized_suffix_tree
 */
public class BasicSuffixTree extends BasicTrie
{
	/**
	 * Constructs an empty suffix tree.
	 */
	public BasicSuffixTree() {
		this (null);
	}

	/**
	 * Constructs a suffix tree with the given input String.
	 * @param input
	 */
	public BasicSuffixTree(String input) {
		words = new ArrayList<>();
		addWord(input);
	}

	/**
	 * Adds the given word to this suffix tree.
	 * @param word String to add to this suffix tree.
	 */
	public void addWord (String word)	{
		if (word != null)	{
			int id = words.size();
			words.add(word);
			for (int i = 0; i < word.length(); i++)	{
				BasicTrieNode node = add(word.substring(i));
				setProps (node, id, i);
			}
		}
	}

	/**
	 * Adds the given array of words into this suffix tree.
	 * @param words array of Strings to add to this suffix tree.
	 */
	public void addWords (String[] words)	{
		Arrays.stream(words).forEach(w -> addWord(w));
	}

	/**
	 * Fetches all the source words in this suffix tree.
	 * Source words are whole words added to this Suffix tree through prior calls to <a href="#addWord">addWord()</a>.
	 * They are recorded in the order in which they were originally added to this suffix tree.
	 * @return a List of Strings in this suffix tree.
	 */
	public List<String> getSourceWords()	{
		return words;
	}

	/**
	 * Fetches the source word at the given index in this suffix tree.
	 * Source words are whole words added to this Suffix tree through prior calls to <a href="#addWord">addWord()</a>.
	 * They are recorded in the order in which they were originally added to this suffix tree.
	 * @param i index of the source word to return.
	 * @return the source word at the given index.
	 */
	public Optional<String> getSourceWord(int i)	{
		return words.size() > i ? Optional.ofNullable(words.get(i)) : Optional.empty();
	}

	/**
	 * Fetches a collection of source words that involve the given node.
	 * @param node the node whose source words are to be returned.
	 * @return a Collection of indices representing the source words. 
	 */
	@SuppressWarnings("unchecked")
	public static Optional<Set<Integer>> getSources (BasicTrieNode node)	{
		Optional<Object> sources = node.getProperty(TRIENODE_PROPERTY_SOURCE);
		return sources.isPresent() ? Optional.ofNullable((Set<Integer>)sources.get()) : Optional.empty();
	}
	
	protected void setProps (BasicTrieNode node, Integer src, Integer sid) {
		node.addProperty(TRIENODE_PROPERTY_SOURCE, words.get(src));
		node.setProperty(TRIENODE_PROPERTY_SUFFIX_ID, sid);
		node.setProperty("str", node.getString());
	}

	protected List<String> words;

	protected static final String TRIENODE_PROPERTY_SOURCE = "src";
	protected static final String TRIENODE_PROPERTY_SUFFIX_ID = "sid";
}