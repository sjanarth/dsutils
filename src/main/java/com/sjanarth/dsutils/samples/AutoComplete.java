package com.sjanarth.dsutils.samples;

import java.util.List;

public class AutoComplete
{
    public static void main (String[] args)	{
        AutoCompleteTrie trie = buildSampleTrie();

        List<String> results = trie.searchAlpha("bu");
        for (String s : results) System.out.println("  "+s);

        /*
         * There seem to be at least two approaches to updating the frequency of occurrences.
         *
         * Approach-1: Bubble up
         *
         * 		Every time, updateFreq is called, we pass a list of topNodes to the immediate parent.
         * 		The parents recursively pass up their top used words, eventually leading to the most
         * 		used words "bubbling up" to the top. While this approach is efficient for small volumes,
         * 		it quickly gets out of control when there are a large number of updateFreq calls.
         *
         * Approach-2: Trickle down
         *
         * 		After doing all updateFreq calls, we call crawl on the root which cascades that recursively
         * 		through the entire tree, effectively decoupling the updates to the frequency counters
         * 		from the code that determines the top most used words.
         *
         * Typically, updates to the trie occur at a set point in time. When they do occur, we expect a
         * large volume of updates and hence this implementation takes the second (trickle down) approach.
         *
         * There are further optimizations possible here. For instance, updateFreq could mark the node as "dirty"
         * and the crawl calls could limit themselves to only the dirty nodes. When applied recursively, this
         * effectively limits the crawl to only those subtrees of the root that have at least one descendant
         * whose frequency has just been updated.
         *
         */

        trie.update("ball", 8);
        trie.update("bath", 4);
        trie.update("beet", 11);
        System.out.println("After all frequency updates");
        System.out.println(trie.toString());
        trie.crawl();
        System.out.println("After crawl");
        System.out.println(trie.toString());

        List<String> results2 = trie.searchTopK("b");
        for (String s : results2) System.out.println("  "+s);
    }

    private static AutoCompleteTrie buildSampleTrie() 	{
        AutoCompleteTrie root = new AutoCompleteTrie();
        root.add("ball");
        root.add("barley");
        root.add("bat");
        root.add("battle");
        root.add("batch");
        root.add("bath");
        root.add("beast");
        root.add("beet");
        root.add("bell");
        root.add("best");
        root.add("book");
        root.add("boot");
        root.add("botch");
        root.add("box");
        root.add("broad");
        root.add("bull");
        root.add("bust");
        root.add("but");
        root.add("butt");
        root.add("butler");
        root.add("butter");
        root.add("button");
        System.out.println(root.toString());
        return root;
    }
}