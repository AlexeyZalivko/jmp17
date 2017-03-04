package org.jmp.part2.patterns.decorator;

import org.jmp.part2.patterns.data.Person;

/**
 * Created by alex on 04.03.17.
 */
public interface PersonDecorator {

    String SPACE_DELIMITER = "\\s+";
    String SPACE_SYMBOL = " ";
    String EXTENSION = ".prs";

    default String capitalizeFirstLetter(final String part) {
        if (Character.isUpperCase(part.charAt(0))) {
            return part + SPACE_SYMBOL;
        } else {
            return Character.toUpperCase(part.charAt(0)) + part.substring(1) + SPACE_SYMBOL;
        }
    }

    default Person capitalizeName(final Person person) {
        if (person == null || person.getName() == null) {
            return null;
        }

        final StringBuilder sb = new StringBuilder();
        for (String part : person.getName().split(SPACE_DELIMITER)) {
            sb.append(capitalizeFirstLetter(part));
        }

        person.setName(sb.toString());

        return person;
    }
}
