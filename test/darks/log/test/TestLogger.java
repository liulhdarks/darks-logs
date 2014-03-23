package darks.log.test;

import darks.log.Logger;

public class TestLogger
{

    private static Logger log = Logger.getLogger(TestLogger.class);

    public TestLogger()
    {
    }

    public static void test()
    {
        throw new RuntimeException("test exception");
    }

    /**
     * @param args
     */
    public static void main(String[] args)
    {
        try
        {
            System.out.println("start");
            for (int i = 0; i < 1; i++)
            {
                log.info("show test info " + i);
                log.debug("show test debug " + i);
                log.warn("show test warn " + i);
                log.error("show test error " + i);
            }
            test();
        }
        catch (Exception e)
        {
            log.error(e.getMessage(), e);
        }
        System.out.println("end");
    }

}
