package com.cursedecho.constants;

import java.util.*;

public class DisplayResolution {

    private static final Map<String, List<String>> resolutionsByAspectRatio = new HashMap<>();

    static {
        resolutionsByAspectRatio.put(DisplayRatio.RATIO_16_9, Arrays.asList("1920x1080", "1280x720", "854x480"));
        resolutionsByAspectRatio.put(DisplayRatio.RATIO_4_3, Arrays.asList("1600x1200", "1024x768", "800x600"));
        resolutionsByAspectRatio.put(DisplayRatio.RATIO_16_10, Arrays.asList("1920x1200", "1440x900", "1280x800"));
    }

    private DisplayResolution() {
        // Private constructor to prevent instantiation
    }

    // Method to get resolutions based on selected aspect ratio
    public static List<String> getResolutions(String aspectRatio, Boolean fullscreen) {
        List<String> resolutions = resolutionsByAspectRatio.getOrDefault(aspectRatio, Arrays.asList("1920x1080"));
        if (fullscreen) {
            return resolutions.subList(0, 1);
        } else {
            return resolutions;
        }
    }
}
