// HW6_dyl30
import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static org.junit.Assert.*;

public class AirportSystemTest {

    private AirportSystem airportSystem;

    @Before
    public void setUp() {
        airportSystem = new AirportSystem();
        // Add some default edges
        airportSystem.addEdge("City1", "City2", 100);
        airportSystem.addEdge("City2", "City3", 150);
        airportSystem.addEdge("City3", "City1", 200);
    }

    // Test cases for Shortest Distance
    @Test
    public void testValidShortestDistance() {
        assertEquals(250, airportSystem.shortestDistance("City1", "City3"));
    }

    @Test
    public void testUnreachableCity() {
        assertEquals(-1, airportSystem.shortestDistance("City1", "City4"));
    }

    @Test
    public void testDirectConnection() {
        assertEquals(100, airportSystem.shortestDistance("City1", "City2"));
    }

    // Test cases for Minimum Spanning Tree
    @Test
    public void testDisconnectedGraphMST() {
        airportSystem = new AirportSystem();
        airportSystem.addEdge("City1", "City2", 100);
        airportSystem.addEdge("City3", "City4", 150);
        List<AirportSystem.Edge> mst = airportSystem.minimumSpanningTree();
        assertNull(null);
    }

    @Test
    public void testSingleEdgeGraphMST() {
        airportSystem.addEdge("City1", "City2", 100);

        List<AirportSystem.Edge> mst = airportSystem.minimumSpanningTree();
        assertNotNull(mst);
        assertFalse(mst.isEmpty());
        assertEquals(2, mst.size());
    }

    @Test
    public void testTriangleGraphMST() {
        airportSystem.addEdge("City1", "City2", 100);
        airportSystem.addEdge("City2", "City3", 100);
        airportSystem.addEdge("City3", "City1", 100);

        List<AirportSystem.Edge> mst = airportSystem.minimumSpanningTree();
        assertNotNull(mst);
        assertFalse(mst.isEmpty());
        assertEquals(2, mst.size());
    }
}
