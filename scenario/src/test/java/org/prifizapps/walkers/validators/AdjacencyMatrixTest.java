package org.prifizapps.walkers.validators;

import org.junit.Assert;
import org.junit.Test;

public class AdjacencyMatrixTest {

    @Test
    public void getNotEmptyZerosLineIndicesTest() {
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(new int[][]{{0, 1}, {0, 0}});
        Assert.assertFalse(adjacencyMatrix.getZerosLineIndices().isEmpty());
    }

    @Test
    public void getEmptyZerosLineIndicesTest() {
        AdjacencyMatrix adjacencyMatrix = new AdjacencyMatrix(new int[][]{{0, 1}, {0, 1}});
        Assert.assertTrue(adjacencyMatrix.getZerosLineIndices().isEmpty());
    }

}
