package com.cursedecho.constants;

import java.util.Arrays;
import java.util.List;

public class DisplayRatio {
    public static final String RATIO_16_9 = "16:9";
    public static final String RATIO_4_3 = "4:3";
    public static final String RATIO_16_10 = "16:10";
    // Add more ratios as needed

    private DisplayRatio() {
        // Private constructor to prevent instantiation
    }

    // Returns a list of all aspect ratios
    public static List<String> getAll() {
        return Arrays.asList(RATIO_16_9, RATIO_4_3, RATIO_16_10);
    }
}
