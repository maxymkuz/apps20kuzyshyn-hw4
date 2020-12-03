package ua.edu.ucu.tries;

import ua.edu.ucu.immutable.ImmutableLinkedList;
import ua.edu.ucu.queue.Queue;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class RWayTrie implements Trie {

    private static int R = 26;
    // radix
    private int size = 0;
    private Node root = new Node();

    // root of trie
    private static class Node {
        private int word_length = -1;
        private Node[] next = new Node[R];
    }

    private static class NodeTuple {
        public final Node node;
        public final String word;

        public NodeTuple(Node node, String word) {
            this.node = node;
            this.word = word;
        }
    }

    @Override
    public void add(Tuple t) {
        add(root, t, 0);
        size += 1;
    }

    private Node add(Node node, Tuple t, int i) {
        if (node == null) {
            node = new Node();
        }
        if (t.weight == i) {
            node.word_length = t.weight;
            return node;
        }
        int c = (int) t.term.charAt(i) - (int) 'a';
        node.next[c] = add(node.next[c], t, i + 1);
        return node;
    }

    @Override
    public boolean contains(String word) {
        Node node = contains(root, word, 0);
        if (node == null) {
            return false;
        }
        return true;
    }

    private Node contains(Node node, String word, int i) {
        if (node == null) {
            return null;
        }
//        System.out.println(node.word_length);

        if (i == word.length()) {
            if (node.word_length == i) {
                return node;
            }
            return null;
        }
        int c = (int) word.charAt(i) - (int) 'a';
//        System.out.println(word.charAt(i));
        return contains(node.next[c], word, i + 1);
    }


    @Override
    public boolean delete(String word) {
        if (delete(root, word, 0)) {
            size--;
            return true;
        }
        return false;
    }

    private boolean delete(Node node, String word, int i) {
        if (node == null) {
            return false;
        }
        if (i == word.length()) {
            if (node.word_length == i) {
                node.word_length = -1;  // deleting a string
                return true;
            }
            return false;
        }
        int c = (int) word.charAt(i) - (int) 'a';

        return delete(node.next[c], word, i + 1);
    }

    @Override
    public Iterable<String> words() {
        Queue q = new Queue(new ImmutableLinkedList());
        q.enqueue(new NodeTuple(root, ""));
        List<String> iterableList = collect(q);

        Iterable<String> iterable = iterableList;

        return iterable;
    }

    private List<String> collect(Queue q) {
        List<String> iterableList = new ArrayList<>();

        while (!(q.getQueue().isEmpty())) {
            Object t = q.dequeue();
            if (t instanceof NodeTuple) {
                Node node = ((NodeTuple) t).node;
                String prev_str = ((NodeTuple) t).word;
                if (node == null) {
                    continue;
                }
                if (node.word_length != -1) {
                    iterableList.add(prev_str);
                }
                // adding all next words to the list
                for (int j = 0; j < node.next.length; j++) {
                    if (node.next[j] != null) {
                        char c = (char) (j + (int) 'a');
                        String combined = prev_str + c;
                        q.enqueue(new NodeTuple(node.next[j], combined));
                    }
                }
            }
        }
        return iterableList;

    }

    @Override
    public Iterable<String> wordsWithPrefix(String s) {
        Node startingNode = advancedContains(root, s, 0);
//        if (startingNode == null){
//            return null;
//        }
        Queue q = new Queue(new ImmutableLinkedList());
        q.enqueue(new NodeTuple(startingNode, s));
        List<String> iterableList = collect(q);

        Iterable<String> iterable = iterableList;

        return iterable;
    }

    private Node advancedContains(Node node, String word, int i) {
        if (node == null) {
            return null;
        }

        if (i == word.length()) {
            return node;
        }
        int c = (int) word.charAt(i) - (int) 'a';
        return advancedContains(node.next[c], word, i + 1);
    }


    @Override
    public int size() {
        return size;
    }

}
