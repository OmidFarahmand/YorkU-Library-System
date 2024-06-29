package model;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public abstract class SearchStrategy {





    public static int calculateDistance(String a, String b) {
        a = a.toLowerCase();
        b = b.toLowerCase();
        int[] costs = new int[b.length() + 1];
        for (int j = 0; j < costs.length; j++)
            costs[j] = j;
        for (int i = 1; i <= a.length(); i++) {
            costs[0] = i;
            int nw = i - 1;
            for (int j = 1; j <= b.length(); j++) {
                int cj = Math.min(1 + Math.min(costs[j], costs[j - 1]), a.charAt(i - 1) == b.charAt(j - 1) ? nw : nw + 1);
                nw = costs[j];
                costs[j] = cj;
            }
        }
        return costs[b.length()];
    }

    public static List<String> findMostSimilarStrings(Collection<String> strings, String keyString) {
        List<String> similarStrings = new ArrayList<>();
        int minDistance = 10; //Integer.MAX_VALUE;
        for (String str : strings) {
            int distance = calculateDistance(str, keyString);
            int sim = countSimilarWords(str,keyString);
            if (distance <= minDistance || sim > 2) {
                // similarStrings.clear();
                // minDistance = distance;
                similarStrings.add(str);
            } else if (distance == minDistance) {
                similarStrings.add(str);
            }
        }
        return similarStrings;
    }


    public static int countSimilarWords(String str1, String str2) {
        String[] words1 = str1.split("\\s+");
        String[] words2 = str2.split("\\s+");

        Set<String> set1 = new HashSet<>();
        Set<String> set2 = new HashSet<>();

        for (String word : words1) {
            set1.add(word.toLowerCase()); 
        }
        for (String word : words2) {
            set2.add(word.toLowerCase()); 
        }

        set1.retainAll(set2);

        return set1.size();
    }



}
