package com.intinctools.service.words;

import org.springframework.stereotype.Component;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Component
public class SplitWords implements WordsSpliterator {
    @Override
    public List<String> wordsSpliterator(final String words) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(words);
        List<String> wordsList = new LinkedList<>();
        while (matcher.find()){
            wordsList.add(matcher.group());
        }
        return wordsList;
    }
}
