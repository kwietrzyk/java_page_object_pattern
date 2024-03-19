package pl.sii.framework.helpers;

import pl.sii.framework.pages.signing.CreateAccountPage;

import java.util.EnumSet;
import java.util.Random;

public enum Title {
    MR, MRS;

    private static final EnumSet<Title> VALUES = EnumSet.allOf(Title.class);
    private static final Random RANDOM = new Random();

    public static Title randomValue() {
        int index = RANDOM.nextInt(VALUES.size());
        return VALUES.toArray(new Title[0])[index];
    }
}
