import java.io.*;
import java.net.InetSocketAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;

public class ProtocolServer {
    public static void main(String[] args) throws IOException {
        try{
            ServerSocket serverSocket = new ServerSocket(80); //80번 포트로 ServerSocket 생성
            while(true)
            {
                System.out.println("Connecting...");
                Socket socket = serverSocket.accept(); //소켓 연결 수락.
                //연결한 클라이언트 주소 출력
                InetSocketAddress isa = (InetSocketAddress) socket.getRemoteSocketAddress();
                System.out.println(isa + " Connect");

                // 소켓 -> 서버
                //소켓에서 받은 메시지를 출력하기 위한 BufferedReader 객체 생성
                BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
                String line = reader.readLine(); //request header 읽어오기

                String[] header = line.split(" "); //http method tokenize
                System.out.print("Request : ");
                for (int i = 0; i < header.length; i++) //request 출력
                    System.out.print(header[i] + " ");
                System.out.println("\n");

                String method = header[0];
                String version = header[2];

                //서버 -> 소켓
                if(version.equals("HTTP/1.0")) { //HTTP 1.0 version에 대한 method 처리
                    if (method.equals("GET"))
                        get(socket);
                    else if(method.equals("POST"))
                        post(socket);
                    else if(method.equals("PUT"))
                        put(socket);
                    else if(method.equals("HEAD"))
                        head(socket);
                    else //위 4가지 method를 제외한 다른 method에 대한 처리
                        notAllowed(socket);
                }
                else //HTTP 1.0을 제외한 다른 version에 대한 처리
                    version_error(socket);
            }
        } catch (IOException e) {}
    }
    //GET method 처리
    private static void get(Socket socket) throws IOException
    {
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //HTTP Response message 만들기
        writer.print("HTTP/1.0 200 OK\r\n");
        writer.print("Date: " + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", new Locale("en", "US"))) + "\r\n");
        writer.print("Server: 192.168.31.121\r\n");
        writer.print("Content-Type: Multipart/related\r\n");
        writer.print("\r\n");
        writer.flush();
        writer.close();
    }
    //POST method 처리
    private static void post(Socket socket) throws IOException
    {
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //HTTP Response message 만들기
        writer.print("HTTP/1.0 201 Created\r\n");
        writer.print("Date: " + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", new Locale("en", "US"))) + "\r\n");
        writer.print("Server: 192.168.31.121\r\n");
        writer.print("Content-Type: Multipart/related\r\n");
        writer.print("\r\n");
        writer.flush();
        writer.close();
    }
    //PUT method 처리
    private static void put(Socket socket) throws IOException
    {
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //HTTP Response message 만들기
        writer.print("HTTP/1.0 204 No Content\r\n");
        writer.print("Date: " + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", new Locale("en", "US"))) + "\r\n");
        writer.print("Server: 192.168.31.121\r\n");
        writer.print("Content-Type: Multipart/related\r\n");
        writer.print("\r\n");
        writer.flush();
        writer.close();
    }
    //HEAD method 처리
    private static void head(Socket socket) throws IOException
    {
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //HTTP Response message 만들기
        writer.print("HTTP/1.0 200 OK\r\n");
        writer.print("Date: " + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", new Locale("en", "US"))) + "\r\n");
        writer.print("Server: 192.168.31.121\r\n");
        writer.print("Content-Type: Multipart/related\r\n");
        writer.print("\r\n");
        writer.flush();
        writer.close();
    }
    //GET, POST, PUT, HEAD를 제외한 method 처리
    private static void notAllowed(Socket socket) throws IOException
    {
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //HTTP Response message 만들기
        writer.print("HTTP/1.0 405 Method Not Allowed\r\n");
        writer.print("Date: " + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", new Locale("en", "US"))) + "\r\n");
        writer.print("Server: 192.168.31.121\r\n");
        writer.print("Content-Type: Multipart/related\r\n");
        writer.print("\r\n");
        writer.flush();
        writer.close();
    }
    //GET method 처리
    private static void version_error(Socket socket) throws IOException
    {
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        //HTTP Response message 만들기
        writer.print("HTTP/1.0 505 HTTP Version Not Supported\r\n");
        writer.print("Date: " + ZonedDateTime.now(ZoneId.of("Asia/Seoul")).format(DateTimeFormatter.ofPattern("EEE, d MMM yyyy HH:mm:ss z", new Locale("en", "US"))) + "\r\n");
        writer.print("Server: 192.168.31.121\r\n");
        writer.print("Content-Type: Multipart/related\r\n");
        writer.print("\r\n");
        writer.flush();
        writer.close();
    }

}