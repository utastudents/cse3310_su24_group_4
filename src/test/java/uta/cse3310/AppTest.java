package uta.cse3310;

import java.net.InetSocketAddress;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */

    // Test the creation of App
    public void testApp()
    {
        int testPort = 9104;
        App testApp = new App(testPort);
        assertNotNull("App instance should be created", testApp);
        InetSocketAddress address = testApp.getAddress();
        assertEquals("Port should match the one provided", testPort, address.getPort());
    }


}
