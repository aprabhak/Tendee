import java.io.*;
import java.net.Socket;
import java.text.ParseException;
import java.awt.image.BufferedImage;
import java.net.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Date;
import java.lang.Object;
import java.io.*;
import java.lang.*;

import com.sun.org.apache.regexp.internal.RE;
import org.codehaus.jackson.JsonFactory;
import org.codehaus.jackson.JsonParser;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.JsonToken;
import org.codehaus.jackson.map.ObjectMapper;

import javax.imageio.ImageIO;

import static com.sun.tools.doclint.Entity.image;

public class Tendee_Client {

    public static Socket s;


    public static void main(String[] args) throws IOException, ParseException, ClassNotFoundException {

        //Localhost
        String host = "127.0.0.1";

        //AWS EC2 Server
        //String host = "52.15.154.196";

        int port = 11408;
        s = new Socket(host, port);

        print();
        // }


    }

    public static void print() throws IOException {

        OutputStream os = s.getOutputStream();
        PrintWriter pw = new PrintWriter(os);

        //Send Commend to server
        pw.write("LOGIN1,####,####");
        pw.flush();
        s.shutdownOutput();

        InputStream is = s.getInputStream();
        BufferedReader br = new BufferedReader(new InputStreamReader(is));


        String Response = null;
        Response = br.readLine();
        System.out.println("Server Response is ----> " + Response);


        br.close();
        is.close();
        pw.close();
        s.close();
        os.close();

    }
}

