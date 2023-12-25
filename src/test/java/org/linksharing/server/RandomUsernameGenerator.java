package org.linksharing.server;

import java.util.Random;

public class RandomUsernameGenerator {
    private static final String[] ADJECTIVES = {"Fast", "Slow", "Smart", "Silly", "Bright",
            "Dark", "Funny", "Serious"};
    private static final String[] NOUNS = {"Tiger", "Eagle", "Shark", "Lion", "Whale", "Panda",
            "Monkey", "Elephant"};

    private static final int THIRD_COMPONENT_BOUND = 10_000;

    public static String get() {
        var random = new Random();
        String randomAdjective = ADJECTIVES[random.nextInt(ADJECTIVES.length)];
        String randomNoun = NOUNS[random.nextInt(NOUNS.length)];
        int randomNum = random.nextInt(THIRD_COMPONENT_BOUND);
        return randomAdjective + randomNoun + randomNum;
    }
}
