/**
 * Created by ChaoLun Shao on 1/19/18.
 */
import java.net.*;
import java.util.Date;
import java.io.*;
import java.lang.*;
import java.sql.*;
import java.util.Arrays;
import org.codehaus.jackson.JsonProcessingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;


public class Tendee_Server extends Thread {
    private Socket clientRequest;
    private BufferedReader input;
    private PrintWriter output;
    int port = 11408;

    public Tendee_Server() throws IOException {
        ServerSocket rServer = null;
        Socket request = null;
        Thread receiveThread = null;
        try {
            rServer = new ServerSocket(port);
            System.out.println("Welcome to the server!");
            System.out.println(new Date());
            System.out.println("The server is ready!");
            System.out.println("Port: " + port);
            while (true) {
                request = rServer.accept();
                System.out.println("Connected: " + request.getInetAddress().getHostName());
                receiveThread = new Tendee_Server(request);
                receiveThread.start();
            }
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public Tendee_Server(Socket s) {
        this.clientRequest = s;
        InputStreamReader reader;
        OutputStreamWriter writer;
        try {
            reader = new InputStreamReader(clientRequest.getInputStream());
            writer = new OutputStreamWriter(clientRequest.getOutputStream());
            input = new BufferedReader(reader);
            output = new PrintWriter(writer, true);
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
    }

    public static void main(String[] args) throws IOException {
        //initialize
        try {
            Class.forName("com.mysql.jdbc.Driver");
        } catch (Exception e) {
            e.printStackTrace();
            return;
        }
        new Tendee_Server();
    }

    public void run() {
        System.out.println("Thread Created");
        String response = null;
        String str = null;
        boolean done = false;

        String tempId = null;
        try {
            str = input.readLine();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }
        String[] request = str != null ? str.split(",") : new String[0];
        try {
            response = Tendee_Server.ParseRequest(request);
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (response != null) {
            System.out.println("Response ready");
            System.out.println("Response is ----> " + response);
            output.println(response);
        }

        try {
            clientRequest.close();
        } catch (IOException e) {
            System.out.println(e.getMessage());
        }

    }


    public static String ParseRequest(String[] request) throws Exception {
        return "SUCCESS";
    }

    //<<----------End of main----------Parse Request Below-------------------->>


}