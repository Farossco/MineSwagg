package fr.ftnt.mineswagg.common;

import java.util.Properties;

public class Version {
    private static String major;
    private static String minor;
    private static String rev;
    private static String build;
    private static String mcversion;

    static void init(Properties properties)
    {
        if (properties != null)
        {
            major = properties.getProperty("MineSwagg.build.major.number");
            minor = properties.getProperty("MineSwagg.build.minor.number");
            rev = properties.getProperty("MineSwagg.build.revision.number");
            build = properties.getProperty("MineSwagg.build.number");
            mcversion = properties.getProperty("MineSwagg.build.mcversion");
        }
    }

    public static String fullVersionString()
    {
        return String.format("%s.%s.%s build %s", major, minor, rev, build);
    }
}
