
package backend.utility;

import java.net.InetSocketAddress;
import java.net.Socket;

 
public class PrivateIP
{
    public static String getPrivateIP() throws Exception
    {
        String privateIP;

        Socket socket = new Socket();
        socket.connect(new InetSocketAddress("google.com", 80));
        privateIP = socket.getLocalAddress().toString();
        socket.close();
        String trimmedIP = privateIP.substring(1);

        return trimmedIP;
    }
}