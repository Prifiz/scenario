package org.myhomeapps.walkers.validators;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

class AdjacencyMatrix {

    private int[][] adjacencyMatrix;

    AdjacencyMatrix(int[][] adjacencyMatrix) {
        this.adjacencyMatrix = adjacencyMatrix;
    }

    private void print() {
        for (int[] matrix : adjacencyMatrix) {
            for (int j = 0; j < adjacencyMatrix.length; j++) {
                System.out.print(matrix[j] + "\t");
            }
            System.out.println();
        }
    }

    List<Integer> getZerosLineIndices() {
        List<Integer> result = new ArrayList<>();
        for (int i = 0; i < adjacencyMatrix.length; i++) {
            if (isZeroLine(adjacencyMatrix[i])) {
                result.add(i);
            }
        }
        return result;
    }

    boolean isZeroLine(int[] line) {
        return Arrays.stream(line)
                .allMatch(current -> current == 0);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AdjacencyMatrix that = (AdjacencyMatrix) o;
        return Arrays.deepEquals(adjacencyMatrix, that.adjacencyMatrix);
    }

    @Override
    public int hashCode() {
        return Arrays.hashCode(adjacencyMatrix);
    }
}
