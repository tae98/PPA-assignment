

import static org.junit.Assert.*;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

/**
 * The test class TestAssignment4.
 *
 */
public class TestAssignment4
{
    /**
     * Default constructor for test class TestAssignment4
     */
    public TestAssignment4()
    {
    }

    /**
     * Sets up the test fixture.
     *
     * Called before every test case method.
     */
    @Before
    public void setUp()
    {
    }

    /**
     * Tears down the test fixture.
     *
     * Called after every test case method.
     */
    @After
    public void tearDown()
    {
    }
    
    @Test
    public void testGetXlocation()
    {
        Assignment4 a4 = new Assignment4();
        Assignment4.MapPanel mp = a4.new MapPanel();
        
        // lower limit
        double actual = mp.getXlocation(-.5, 800);
        double expected = 0;
        assertEquals(actual,expected,.01);
        
        // upper limit
        actual = mp.getXlocation(.255, 800);
        expected = 800;
        assertEquals(actual,expected,.01);
        
    }
    
    @Test
    public void testGetYlocation()
    {
        Assignment4 a4 = new Assignment4();
        Assignment4.MapPanel mp = a4.new MapPanel();
        
        // lower limit
        double actual = mp.getYlocation(51.66, 600);
        double expected = 0;
        assertEquals(actual,expected,.01);
        
        // upper limit
        actual = mp.getYlocation(51.33, 600);
        expected = 600;
        assertEquals(actual,expected,.01);
        
    }
    
}
