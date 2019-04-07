package com.weixm.wordLadder.models;

import java.io.IOException;
import java.util.ArrayList;
import java.io.FileReader;
import java.io.FileNotFoundException;

import com.sun.org.apache.xpath.internal.operations.Bool;
import org.springframework.core.io.ClassPathResource;


public class wordLadder {
    // static file
    private ArrayList<String> dictionary;

    // constructor (initialize this.dictionary)
    public wordLadder () {
        this.dictionary = new ArrayList<>();
        try{
            ClassPathResource dict_path = new ClassPathResource("static/dictionary.txt");
            String path = dict_path.getFile().getAbsolutePath();
            FileReader dic_file = new FileReader(path);
            char[] word_buffer = new char[1]; // assume that no word is longer than 64B
            String word = "";
            while (dic_file.read(word_buffer) != -1) {
                if (word_buffer[0] != '\n') {
                    word += String.valueOf(word_buffer);
                } else {
                    this.dictionary.add(word);
                    word = "";
                }
            }
            this.dictionary.add(word);
        } catch (FileNotFoundException e) {
            System.out.println(e.getMessage());
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    // judge whether a word is in the given dictionary
    public Boolean validWord(String word) {
        return this.dictionary.contains(word);
    }

    // neighbors means the word that only differs one letter from the given word
    private ArrayList<String> neighbers(String word) {
        String alp = "abcdefghijklmnopqrstuvwxyz";
        int len = word.length();
        ArrayList<String> neighbors = new ArrayList<>();
        for (int i = 0; i < len; i++) {
            for (int j = 0; j < 26; j++) {
                String new_word = word.substring(0, i);
                new_word += alp.charAt(j);
                new_word += word.substring(i+1);
                if (validWord(new_word) && !word.equals(new_word) && !neighbors.contains(new_word)) {
                    neighbors.add(new_word);
                }
            }
        }
        return neighbors;
    }

    public ArrayList<String> generateChain (String word1, String word2) {
        // to mark visited words
        ArrayList<String> mark = new ArrayList<>();
        ArrayList<String> path = new ArrayList<>();
        path.add(word1);
        ArrayList<ArrayList<String> > path_queue = new ArrayList<>();
        path_queue.add(path);

        while (path_queue.size() != 0) {
            ArrayList<String> top_path = path_queue.get(0);
            path_queue.remove(0);
            ArrayList<String> neighbors = this.neighbers(top_path.get(top_path.size()-1));
            int nei_len = neighbors.size();
            for (int i = 0; i < nei_len; i++) {
                String word = neighbors.get(i);
                // skip visited words
                if (!mark.contains(word)) {
                    if (word.equals(word2)) {
                        top_path.add(word);
                        return top_path;
                    } else {
                        ArrayList<String> deep_copy = new ArrayList<>();
                        int top_len = top_path.size();
                        for (int j = 0; j < top_len; j++) {
                            deep_copy.add(top_path.get(j));
                        }
                        deep_copy.add(word);
                        path_queue.add(deep_copy);
                        mark.add(word);
                    }
                }
            }
        }
        return new ArrayList<>();
    }

    public ArrayList<String> wholeDictionary () {
        return this.dictionary;
    }
}
