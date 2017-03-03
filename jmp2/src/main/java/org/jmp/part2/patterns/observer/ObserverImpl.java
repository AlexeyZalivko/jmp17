package org.jmp.part2.patterns.observer;

import org.jmp.part2.patterns.observer.data.Event;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by alex on 02.03.17.
 */
public final class ObserverImpl extends Observer {

    public static final String SPACE_DELIMETER = "\\s+";

    public void readFile(final File file) {
        if (file == null) {
            return;
        }

        try {
            final BufferedReader reader = new BufferedReader(new FileReader(file));
            String line;
            Integer lines = 0;
            String longestWord = null;
            while ((line = reader.readLine()) != null) {
                longestWord = getLongestWord(line, longestWord);
                lines++;
            }

            generateEvents(lines, longestWord);
        } catch (FileNotFoundException e) {
            System.out.println(e);
        } catch (IOException e) {
            System.out.println(e);
        }
    }

    private void generateEvents(final Integer counter, final String longestWord) {
        final List<Event> events = new ArrayList<>();
        events.add(new Event(Event.Type.ID_LINES_COUNT_EVENT, counter.toString()));
        events.add(new Event(Event.Type.ID_LONGEST_WORD_EVENT, longestWord));
        events.add(new Event(Event.Type.ID_REVERSE_WORD_EVENT, new StringBuilder(longestWord).reverse().toString()));

        for (Event event : events) {
            super.notifyEvent(event);
        }
    }

    private String getLongestWord(final String line, final String previousLongestWord) {
        if (line == null) {
            return previousLongestWord;
        }

        final List<String> words = Arrays.asList(line.trim().split(SPACE_DELIMETER));
        if (words == null) {
            return previousLongestWord;
        }

        String longestWord = (previousLongestWord != null) ? previousLongestWord : words.get(0);
        for (String word : words) {
            if (word.length() > longestWord.length()) {
                longestWord = word;
            }
        }

        return longestWord;
    }

}
