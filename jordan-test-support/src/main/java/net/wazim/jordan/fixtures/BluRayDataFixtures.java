package net.wazim.jordan.fixtures;

import net.wazim.jordan.domain.BluRay;

import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Random;

public class BluRayDataFixtures {

    public static BluRay someUnownedBluRay() {
        return new BluRay(someString(), somePrice(), somePrice(), false);
    }

    public static BluRay someOwnedBluRay() {
        return new BluRay(someString(), somePrice(), somePrice(), true);
    }

    private static String someString() {
        return new BigInteger(130, new SecureRandom()).toString(32);
    }

    public static String someHtmlClass() {
        return pickOneOf("fstRowGrid", "rsltGrid");
    }

    private static String somePrice() {
        Random random = new Random();
        int pound = random.nextInt(10);
        int pennies = random.nextInt(99-10 + 1) + 10;
        return "£" + pound + "." + pennies;
    }

    public static <T> T pickOneOf(T... choices) {
        Random random = new Random();
        return choices[random.nextInt(choices.length)];
    }

}