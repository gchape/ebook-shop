package io.github.gchape.ebookshop.utils;

import java.util.List;

public class Unique100k {
    private static final List<String> ADJECTIVES = List.of("Cool", "Epic", "Fast", "Mysterious", "Brave", "Witty", "Clever", "Loyal", "Swift", "Fierce");
    private static final List<String> NOUNS = List.of("Tiger", "Falcon", "Wizard", "Knight", "Panda", "Shadow", "Fox", "Dragon", "Rider", "Ninja");

    private Unique100k() {
    }

    public static String nextString() {
        return getRandom(ADJECTIVES) + getRandom(NOUNS) + getRandom(1000);
    }

    private static String getRandom(Object o) {
        if (o instanceof Integer)
            return String.valueOf((int) (Math.random() * (int) o));
        else if (o instanceof List<?>)
            return String.valueOf(((List<?>) o).get((int) (Math.random() * ((List<?>) o).size())));
        else throw new IllegalArgumentException();
    }
}
