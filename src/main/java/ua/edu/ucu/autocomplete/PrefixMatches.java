package ua.edu.ucu.autocomplete;

import ua.edu.ucu.tries.Trie;
import ua.edu.ucu.tries.Tuple;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

/**
 * @author andrii
 */
public class PrefixMatches {

    private Trie trie;

    public PrefixMatches(Trie trie) {
        this.trie = trie;
    }

    public int load(String... strings) {
        for (int i = 0; i < strings.length; i++) {
            String[] splitted = strings[i].split("\\s+");
            for (int j = 0; j < splitted.length; j++) {
                if (splitted[j].length() > 2) {
                    trie.add(new Tuple(splitted[j], splitted[j].length()));
                }
            }
        }
        return 0;
    }

    public boolean contains(String word) {
        return trie.contains(word);
    }

    public boolean delete(String word) {
        return trie.delete(word);
    }

    public Iterable<String> wordsWithPrefix(String pref) throws Exception {
        if (pref.length() >= 2) {
            return trie.wordsWithPrefix(pref);
        }
        throw new Exception("Words is not enough long");
    }

    public Iterable<String> wordsWithPrefix(String pref, int k) throws Exception {
        if (pref.length() >= 2) {
            List<String> result = new ArrayList<>();
            Iterator<String> iterator = trie.wordsWithPrefix(pref).iterator();

            while (iterator.hasNext()) {
                String x = iterator.next();
                if (x.length() < pref.length() + k) {
                    result.add(x);
                }
            }
            return result;
        }
        throw new Exception("Words is not enough long");

    }

    public int size() {
        return trie.size();
    }
}
