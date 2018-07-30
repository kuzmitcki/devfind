package com.instinctools.service.words;

import org.springframework.stereotype.Service;

import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


@Service
public class SplitWords implements WordsSpliterator {
    @Override
    public List<String> wordsSpliterator(final String words) {
        Pattern pattern = Pattern.compile("\\w+");
        Matcher matcher = pattern.matcher(words);
        List<String> wordsList = new LinkedList<>();
        while (matcher.find()) {
            wordsList.add(matcher.group());
        }
        return wordsList;
    }
}
