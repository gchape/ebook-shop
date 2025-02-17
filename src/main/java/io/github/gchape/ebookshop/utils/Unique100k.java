package io.github.gchape.utils;

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
        return switch (o) {
            case Integer i -> String.valueOf((int) (Math.random() * i));
            case List<?> l -> String.valueOf(l.get((int) (Math.random() * l.size())));
            default -> throw new IllegalArgumentException();
        };
    }
}
