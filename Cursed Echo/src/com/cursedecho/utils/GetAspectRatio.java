package com.cursedecho.utils;

public class GetAspectRatio {

    /**
     * Calculates the aspect ratio of a given width and height.
     *
     * @param width  The width of the screen.
     * @param height The height of the screen.
     * @return A string representing the aspect ratio (e.g., "16:9").
     */

    public static String calculate(double width, double height) {
        double ratio = width / height;

        if (Math.abs(ratio - 16.0 / 9) < 0.01) {
            return "16:9";
        } else if (Math.abs(ratio - 4.0 / 3) < 0.01) {
            return "4:3";
        } else if (Math.abs(ratio - 16.0 / 10) < 0.01) {
            return "16:10";
        } else {
            return String.format("%.2f:1", ratio);
        }
    }
}
