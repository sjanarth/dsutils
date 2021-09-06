package com.sjanarth.dsutils.samples;

import com.sjanarth.dsutils.BasicTrie;

import java.util.*;

public class AutoCompleteTrie extends BasicTrie
{
    public AutoCompleteTrie() {
        super (AutoCompleteTrieNode.class);
    }

    public List<String> searchTopK (String prefix)	{
        System.out.println("searchTopK, prefix="+prefix);
        Queue<Character> q = toCharQueue(prefix);
        Optional<? extends BasicTrieNode> node = root.findNode(q);
        if (node.isPresent()) {
            return ((AutoCompleteTrieNode) node.get()).getTopChildStrings();
        }
        return new ArrayList<>();
    }

    public Integer update (String s, int freq)	{
        Queue<Character> q = toCharQueue(s);
        Optional<? extends BasicTrieNode> node = root.findNode(q);
        if (!node.isPresent())
            node = Optional.ofNullable (add(s));
        return ((AutoCompleteTrieNode) node.get().getChildMap().get(null)).setFrequency(freq).get();
    }

    public void crawl ()	{
        ((AutoCompleteTrieNode)root).crawl();
    }

    protected static class AutoCompleteTrieNode extends BasicTrie.BasicTrieNode
    {
        protected static final int DEFAULT_TOP_K = 3;
        protected static final String AUTOCOMPLETE_TRIENODE_PROPERTY_FREQUENCY = "freq";
        protected static final String AUTOCOMPLETE_TRIENODE_PROPERTY_TOPK = "topK";

        public AutoCompleteTrieNode () {
            this (null, null);
        }

        public AutoCompleteTrieNode (Character ch, AutoCompleteTrieNode p) {
            super (ch, p);
            setDefaultProps();
        }

        public Optional<Integer> getFrequency ()	{
            Optional<Object> freq = getProperty (AUTOCOMPLETE_TRIENODE_PROPERTY_FREQUENCY);
            return freq.isPresent() ? Optional.ofNullable((Integer)freq.get()) : Optional.empty();
        }

        public Optional<Integer> setFrequency (Integer f)	{
            Optional<Object> freq = setProperty (AUTOCOMPLETE_TRIENODE_PROPERTY_FREQUENCY, f);
            return freq.isPresent() ? Optional.ofNullable((Integer)freq.get()) : Optional.empty();
        }

        public List<AutoCompleteTrieNode> crawl ()	{
            List<AutoCompleteTrieNode> topNodes = (List<AutoCompleteTrieNode>) props.get(AUTOCOMPLETE_TRIENODE_PROPERTY_TOPK);
            if (isWord()) {
                topNodes.set(0, this);
            } else {
                for (BasicTrie.BasicTrieNode child : children.values())	{
                    AutoCompleteTrieNode achild = (AutoCompleteTrieNode) child;
                    for (AutoCompleteTrieNode topK : achild.crawl())	{
                        if (topK == null) break;
                        for (int i = 0; i < DEFAULT_TOP_K; i++)	{
                            if (topNodes.get(i) == null) {
                                topNodes.set(i, topK);
                                break;
                            } else if (topNodes.get(i).getFrequency().get() < topK.getFrequency().get()) {
                                for (int j = DEFAULT_TOP_K - 1; j > i; j--)
                                    topNodes.set(j, topNodes.get(j - 1));
                                topNodes.set(i, topK);
                                break;
                            }
                        }
                    }
                }
            }
            props.put(AUTOCOMPLETE_TRIENODE_PROPERTY_TOPK, topNodes);
            return topNodes;
        }

        public List<String> getTopChildStrings ()	{
            List<String> topStrings = new ArrayList<String>();
            List<AutoCompleteTrieNode> topNodes = (List<AutoCompleteTrieNode>) getProperty (AUTOCOMPLETE_TRIENODE_PROPERTY_TOPK).get();
            for (AutoCompleteTrieNode topNode : topNodes)	{
                if (topNode != null)	{
                    StringBuilder sb = new StringBuilder();
                    sb.append(topNode.getString());
                    sb.append("(");
                    sb.append(AUTOCOMPLETE_TRIENODE_PROPERTY_FREQUENCY); sb.append("=");
                    sb.append(topNode.getFrequency().get());
                    sb.append(")");
                    topStrings.add(sb.toString());
                }
            }
            return topStrings;
        }

        protected void setDefaultProps ()	{
            // frequency
            props.put(AUTOCOMPLETE_TRIENODE_PROPERTY_FREQUENCY, Integer.valueOf(0));
            // top K nodes
            AutoCompleteTrieNode[] topNodes = new AutoCompleteTrieNode[DEFAULT_TOP_K];
            for (int i = 0; i < DEFAULT_TOP_K; i++)
                topNodes[i] = null;
            props.put(AUTOCOMPLETE_TRIENODE_PROPERTY_TOPK, Arrays.asList(topNodes));
        }
    }
}
