package com.baseball.players_by_position.service.search;

import java.util.HashMap;
import java.util.Map;

public class FirstNameEditDistance {

    Map<String, Map<String, Integer>> calculatedDistancesMap;

    public FirstNameEditDistance() {
        this.calculatedDistancesMap = new HashMap<>();
    }

    private void addEntryToDistanceMap(String name1, String name2, int distance) {

        if (calculatedDistancesMap.containsKey(name1)) {
            //should not reach this case as we should have already memoized the result
            if (calculatedDistancesMap.get(name1).containsKey(name2)) {
                return;
            } else {
                calculatedDistancesMap.get(name1).put(name2, distance);
            }
        } else {
            HashMap<String, Integer> insideMap = new HashMap<>();
            insideMap.put(name2, distance);
            calculatedDistancesMap.put(name1, new HashMap<>(insideMap));
        }
    }

    public Boolean isFuzzyMatch(String name1, String name2) {

        int name1Length = name1.length();
        int name2Length = name2.length();
        int maxLength = Math.max(name1Length, name2Length);
        int distanceThresholdExclusive = maxLength / 2 + (maxLength % 2);

        if (this.calculatedDistancesMap.containsKey(name1)) {
            Map<String, Integer> insideMap = this.calculatedDistancesMap.get(name1);
            if (insideMap.containsKey(name2)) {
                return insideMap.get(name2) == distanceThresholdExclusive;
            }
        } else if (this.calculatedDistancesMap.containsKey(name2)) {
            Map<String, Integer> insideMap = this.calculatedDistancesMap.get(name2);
            if (insideMap.containsKey(name1)) {
                return insideMap.get(name1) == distanceThresholdExclusive;
            }
        }

        int[][] matrix = getMatrix(name1Length + 1, name1Length + 1);
        int distance = calculateDistance(matrix, name1, name2, distanceThresholdExclusive);

        addEntryToDistanceMap(name1, name2, distance);
        return distance == distanceThresholdExclusive;

    }

    private int[][] getMatrix(int yLength, int xLength) {

        int[][] matrix = new int[yLength][xLength];

        for (int i = 0; i < yLength; i++) {
            matrix[i][0] = i;
        }

        for (int i = 1; i < xLength; i++) {
            matrix[0][i] = i;
        }

        return matrix;

    }

    private int calculateDistance(int[][] matrix, String name1, String name2, int distanceThresholdExclusive) {

        for (int i = 1; i <= matrix.length; i++) {

            for (int j = 1; j <= matrix[i].length; j++) {

                int cost = 0;
                if (name1.charAt(i) != name2.charAt(j)) {
                    cost = 1;
                }

                int cellAboveCost = matrix[i - 1][j] + 1;
                int cellToTheLeftCost = matrix[i][j - 1] + 1;//
                int cellToTheDiagonal = matrix[i - 1][j - 1] + cost;//equal each other no change

                int minDistance = Math.min(cellAboveCost, Math.min(cellToTheLeftCost, cellToTheDiagonal));

                if (minDistance == distanceThresholdExclusive) {
                    return minDistance;
                }

                matrix[i][j] = minDistance;

            }

        }

        return matrix[name1.length()][name2.length()];

    }

    public int getEditDistance(String name1, String name2) {
        return this.calculatedDistancesMap.get(name1).get(name2);
    }

}
