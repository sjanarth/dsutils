package com.sjanarth.dsutils;

import java.lang.reflect.Constructor;
import java.util.*;

/**
 * A basic implementation of a trie data structure.
 *
 * A trie data structure is a container for one or more Strings represented as a tree whose
 * nodes represent characters in the input string. The root node represents a special '/' character.
 * The node representing the first character of the input string is a direct descendant of this special root node.
 * All other nodes (representing characters at indices 1 through n in the input text) inherit from a parent node
 * that represents the character immediately to the left of the node.
 *
 * More info:
 * https://en.wikipedia.org/wiki/Trie
 */
public class BasicTrie extends BasicObjectWithProperties
{
	/**
	 * Constructs an instance of BasicTrie with the root node of type BasicTrieNode.
	 */
	public BasicTrie() {
		this(BasicTrieNode.class);
	}

	/**
	 * Construcs an instance of BasicTrie with the root node of the given type.
	 * @param subClass a child class of <a href="#BasicTrieNode">BasicTrieNode</a> to
	 *                 	create the root node of this BasicTrie with.
	 */
	public BasicTrie (Class<? extends BasicTrieNode> subClass) {
		try {
			Constructor<? extends BasicTrieNode> c = subClass.getConstructor(new Class[] {});
			root = c.newInstance(new Object[] {});
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Fetches the root node of this trie.
	 * @return the root node of this trie.
	 */
	public BasicTrieNode getRoot()	{
		return root;
	}

	/**
	 * Adds the given String to the trie.
	 * @param word string to add to the trie.
	 * @return the node representing the first character of the given word in this trie.
	 */
	public BasicTrieNode add (String word) {
		//System.out.println("Trie.add, s="+s);
		word = word.toLowerCase();	// done typically to minimize the overall size of the trie
		Queue<Character> q = toCharQueue(word);
		return root.add(q);
	}

	/**
	 * Searches the given string in the trie alphabetically.
	 * @param prefix the string to look for in the trie.
	 * @return a list of Strings from this trie that represent words starting with the given prefix.
	 */
	public List<String> searchAlpha (String prefix)	{
		System.out.println("searchAlpha, prefix="+prefix);
		Queue<Character> q = toCharQueue(prefix);
		Optional<? extends BasicTrieNode> node = root.findNode(q);
		if (node.isPresent())	{
			//System.out.println(node.toString(0));
			/*
			prefix = prefix.substring(0, prefix.length()-1);
			List<String> results = new ArrayList<String>();
			for (String s : node.getAllChildStrings())
				results.add(prefix+s);
			*/
			return node.get().getAllChildStrings();
		}
		return new ArrayList<>();
	}

	@Override
	public String toString () {
		return root.toString(0);
	}
	
	protected Queue<Character> toCharQueue(String s) {
		Queue<Character> q = new LinkedList<>();
		for (Character c : s.toCharArray())
			q.add(c);
		return q;
	}
	
	protected BasicTrieNode root = null;

	/**
	 * A class representing individual nodes in the trie.
	 */
	public static class BasicTrieNode extends BasicObjectWithProperties
	{
		/**
		 * Constructs an empty instance of a BasicTrieNode.
		 */
		public BasicTrieNode () {
			this (null, null);
		}

		/**
		 * Constructs a BasicTrieNode instance representing the given character as a child of the given parent node.
		 * @param ch character representing the new BasicTrieNode instance.
		 * @param p parent to the link the node BasicTrieNode instance as a child of.
		 */
		public BasicTrieNode (Character ch, BasicTrieNode p) {
			c = ch;
			children = new TreeMap<>(new NullCharComparator());
			parentNode = p;	// a more clever implementation could do away with this
		}

		/**
		 * Fetches a map of child nodes rooted at the current node.
		 * @return a map of child nodes rooted at the current node.
		 */
		public Map<Character, BasicTrieNode> getChildMap()	{
			return children;
		}

		/**
		 * Fetches the parent node.
		 * @return the parent node of the current node.
		 */
		public BasicTrieNode getParent()	{
			return parentNode;
		}

		/**
		 * Signals if this node is a terminal node.
		 * @return true if this node is a terminal node (represents the end of a word), false otherwise.
		 */
		public boolean isWord () {
			return c == null && children.isEmpty();
		}

		/**
		 * Signals if this is a root node.
		 * @return true if this is a root node, false otherwise.
		 */
		public boolean isRoot () {
			return c == null && !children.isEmpty();
		}

		protected BasicTrieNode add(Queue<Character> q)	{
			Character nextChar = null;
			if (!q.isEmpty())
				nextChar = q.poll();
			BasicTrieNode child = children.get(nextChar);
			if (child == null)	{
				child = createNode (nextChar, this);
				children.put(nextChar, child);
			}
			/*
			if (nextChar != null)
				child.add(q);
			return child;
			*/
			if (nextChar != null)
				return child.add(q);
			else
				return child;
		}

		/**
		 * Searches for a node matching the given prefix rooted in the current node.
		 * @param qPrefix prefix to search for
		 * @return the node matching the given prefix.
		 */
		public Optional<? extends BasicTrieNode> findNode(Queue<Character> qPrefix)	{
			if (qPrefix.isEmpty())
				return Optional.of(this);// children.get(null);
			Character nextChar = qPrefix.poll();
			if(!children.containsKey(nextChar))
				return Optional.empty();
			BasicTrieNode nextNode = children.get(nextChar);
			return nextNode.findNode(qPrefix);
		}

		/**
		 * Fetches the String represented by this node.
		 * @return the String represented by this node starting from the root node.
		 */
		public String getString ()  {
			StringBuilder sb = new StringBuilder();
			BasicTrieNode curr = this;
			while (curr != null) {
				if (!curr.isRoot())
					sb.append(curr.c == null? "." : curr.c);
				curr = curr.parentNode;
			}
			return sb.reverse().toString();
		}

		/**
		 * Fetches all Strings with the current node as the root node.
		 * @return a list of Strings rooted at the current node.
		 */
		public List<String> getAllChildStrings ()	{
			List<String> allStrings = new ArrayList<>();
			if (isWord()) {
				allStrings.add("");
				return allStrings;
			}
			for(BasicTrieNode child : children.values())
				for (String s : child.getAllChildStrings())
					allStrings.add(c+s);
			return allStrings;
		}

		 protected String toString (int level) {
			StringBuilder sb = new StringBuilder();
			if (isRoot()) {
				sb.append("/");
				for (Character ch : children.keySet()) {
					sb.append("\n");
					BasicTrieNode child = children.get(ch);
					sb.append(child.toString(level+1));
				}
			} else {
				for (int i = 1; i < level; i++) sb.append(" ");
				sb.append("\\_");
				//sb.append("|-");
				if (isWord()) {
					sb.append(".");
					appendProps(sb);
				} else {
					sb.append(c);
					appendProps(sb);
					for (Character ch : children.keySet()) {
						sb.append("\n");
						BasicTrieNode child = children.get(ch);
						sb.append(child.toString(level+2));
					}
				}
			}
			return sb.toString();
		}

		@SuppressWarnings("rawtypes")
		protected BasicTrieNode createNode (Character ch, BasicTrieNode parent)	{
			Class<? extends BasicTrieNode> cls = parent.getClass();
			Class[] paramTypes = { Character.class, cls };
			Object[] params = { ch, parent };
			try {
				Constructor<? extends BasicTrieNode> c = cls.getConstructor(paramTypes);
				return c.newInstance(params);
			} catch (Exception e) {
				e.printStackTrace();
			}
			return null;
		}

		@SuppressWarnings("unchecked")
		protected void appendProps (StringBuilder sb)	{
			if (props.isEmpty()) {
				return;
			}
			sb.append("{");
			boolean first = true;
			for (Object k : props.keySet())	{
				if (!first)
					sb.append(",");
				sb.append(k); sb.append("=");
				Object v = props.get(k);
				if (v instanceof Collection)	{
					Collection<Object> lov = (Collection<Object>) v;
					sb.append("[");
					boolean first2 = true;
					for (Object v2 : lov) {
						if (!first2)
							sb.append(",");
						if (v2 instanceof BasicTrieNode)
							sb.append(((BasicTrieNode) v2).getString());
						else
							sb.append(v2);
						first2 = false;
					}
					sb.append("]");
				} else if (v instanceof BasicTrieNode)	{
					sb.append(((BasicTrieNode) v).getString());
				} else {
					sb.append(props.get(k));
				}
				first = false;
			}
			sb.append("}");
		}

		protected Character c;
		protected Map<Character, BasicTrieNode> children;
		protected BasicTrieNode parentNode;

		private static class NullCharComparator implements Comparator<Character>	{
			@Override
			public int compare(Character o1, Character o2) {
				if (o1 == null && o2 == null) return 0;
				if (o1 == null) return -1;
				if (o2 == null) return 1;
				return o1.compareTo(o2);
			}
		}
	}

	public BasicTrieBfsIterator getBfsIterator()	{
		return new BasicTrieBfsIterator(root);
	}

	public static class BasicTrieBfsIterator implements Iterator<BasicTrieNode>
	{
		protected BasicTrieBfsIterator (BasicTrieNode node)	{
			if (node != null)
				queue.add(node);
		}

		@Override
		public boolean hasNext() {
			return !queue.isEmpty();
		}

		@Override
		public BasicTrieNode next() {
			BasicTrieNode head = queue.poll();
			if (!head.getAllChildStrings().isEmpty())	{
				for (BasicTrieNode child : head.getChildMap().values())
					queue.add(child);
			}
			return head;
		}

		private Queue<BasicTrieNode> queue = new LinkedList<>();
	}

	public BasicTrieDfsIterator getDfsIterator()	{
		return new BasicTrieDfsIterator(root);
	}

	public static class BasicTrieDfsIterator implements Iterator<BasicTrieNode>
	{
		protected BasicTrieDfsIterator (BasicTrieNode node)	{
			if (node != null)
				stack.push(node);
		}

		@Override
		public boolean hasNext() {
			return !stack.isEmpty();
		}

		@Override
		public BasicTrieNode next() {
			BasicTrieNode top = stack.pop();
			if (!top.getAllChildStrings().isEmpty())	{
				for (BasicTrieNode child : top.getChildMap().values())
					stack.push(child);
			}
			return top;
		}

		private Stack<BasicTrieNode> stack = new Stack<BasicTrieNode>();
	}

	// TODO: Convert this to lambda style processNode
	public static class BasicTrieWalker
	{
		public void walkDfs (BasicTrieNode node)	{
			if (node == null) return;
			for (BasicTrieNode child : node.getChildMap().values())
				walkDfs(child);
			processNode (node);
		}

		public void walkBfs (BasicTrieNode node)	{
			if (node == null) return;
			Queue<BasicTrieNode> qNodes = new ArrayDeque<>();
			qNodes.add(node);
			while (!qNodes.isEmpty())	{
				BasicTrieNode curr = qNodes.poll();
				processNode (curr);
				qNodes.addAll(curr.getChildMap().values());
			}
		}

		public void processNode (BasicTrieNode node)	{
			System.out.println(node.getString());
		}
	}
}