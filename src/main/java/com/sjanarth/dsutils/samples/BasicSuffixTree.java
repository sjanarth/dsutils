package com.sjanarth.dsutils.samples;

import com.sjanarth.dsutils.BasicTrie;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;
import java.util.Set;

public class BasicSuffixTree
{
    private static void showSimpleUsage()   {
        com.sjanarth.dsutils.BasicSuffixTree bst = new com.sjanarth.dsutils.BasicSuffixTree("banana");
        System.out.println(bst.toString());
    }

    private static void showMultipleWordsUsage()    {
        String[] words = new String[] {
                "ball", "call", "fall", "hall", "mall", "tall", "wall", "ball",
                "barley", "bat", "battle", "batch", "bath", "beast", "beet", "bell",
                "best", "book", "boot", "botch", "box", "broad", "bull", "bust", "but",
                "butt", "butler", "butter", "button"
        };
        com.sjanarth.dsutils.BasicSuffixTree bst = new com.sjanarth.dsutils.BasicSuffixTree();
        Arrays.stream(words).forEach(w -> bst.addWord(w));
        System.out.println(bst.toString());
    }

    private static void showIteratorUsage() {
        com.sjanarth.dsutils.BasicSuffixTree bst = new com.sjanarth.dsutils.BasicSuffixTree("yesterday");
        Iterator<BasicTrie.BasicTrieNode> it = bst.getDfsIterator();
        System.out.println("Showing DFS iterator");
        while (it.hasNext())
            System.out.println(it.next().getString());
        Iterator<BasicTrie.BasicTrieNode> it2 = bst.getBfsIterator();
        System.out.println("Showing BFS iterator");
        while (it2.hasNext())
            System.out.println(it2.next().getString());
    }

    private static void showLongestCommonSubstringUsage()   {
        String[] words = new String[] { "myself", "yourself" };
        com.sjanarth.dsutils.BasicSuffixTree bst = new com.sjanarth.dsutils.BasicSuffixTree();
        bst.addWords(words);
        LCSTrieWalker lcstw = new LCSTrieWalker(words);
        lcstw.walkDfs(bst.getRoot());
        System.out.println("Longest common substring (myself, yourself) = "+lcstw.getLCS());
    }

    private static class LCSTrieWalker extends BasicTrie.BasicTrieWalker
    {
        public LCSTrieWalker (String[] words) { this.words = words; }
        @Override
        public void processNode (BasicTrie.BasicTrieNode node) {
            Optional<Set<Integer>> sources = com.sjanarth.dsutils.BasicSuffixTree.getSources(node);
            if (sources.isPresent() && sources.get().size() == words.length)	{
                String str = node.getString();
                if (lcs == null) lcs = str;
                else if (lcs.length() < str.length()) lcs = str;
            }
        }
        public String getLCS () { return lcs; }
        private String lcs;
        private String[] words;
    }

    public static void main(String[] args)	{
        //showSimpleUsage();
        //showMultipleWordsUsage();
        showIteratorUsage();
        //showLongestCommonSubstringUsage();
    }
}
