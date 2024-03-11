
package backend.classes;

import java.net.InetAddress;
 
public class PrivateIP
{
    public static String getPrivateIP() throws Exception
    {
        // Returns the instance of InetAddress containing
        InetAddress localhost = InetAddress.getLocalHost();
        String privateIP = localhost.getHostAddress().trim();

        return privateIP;
    }
}