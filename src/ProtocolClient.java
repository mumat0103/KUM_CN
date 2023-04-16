import java.io.*;
import java.net.Socket;
import java.time.chrono.IsoEra;
import java.util.Scanner;

public class ProtocolClient {
    public static void main(String[] args) throws IOException {
        while (true){
            Scanner input = new Scanner(System.in);
            Socket socket = new Socket("192.168.31.121", 80); //socket 연결. 해당 IP는 Desktop IP 주소
            System.out.print("Input HTTP version : ");
            String version = input.nextLine(); //HTTP version 입력
            if (version.toUpperCase().equals("END")) //end 입력 시 종료
            {
                socket.close();
                break;
            }
            System.out.print("Input HTTP method : ");
            String method = input.nextLine(); //HTTP method 입력

            if (version.equals("1.0"))
            {
                request(socket, method); //입력받은 method 처리
                response(socket);
            }
            else
            {
                version_error(socket, method, version); //버전 오류 시 처리
                response(socket);
            }
        }
    }
    //입력받은 method 처리 함수
    private static void request(Socket socket, String method) throws  IOException {
        String msg = method.toUpperCase() + " / HTTP/1.0"; //입력값을 대문자로 바꿔서 메시지 생성
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.println(msg); //메시지 전송
        writer.flush();
        System.out.println("Data Transfer Finish.");
    }
    private static void response(Socket socket) throws IOException {
        //소켓에서 받은 메시지를 출력하기 위한 BufferedReader 객체 생성
        BufferedReader reader = new BufferedReader(new InputStreamReader(socket.getInputStream()));
        String line = null;
        System.out.println(("Response : "));
        while((line = reader.readLine()) != null) //버퍼가 빌 때까지 반복문 수행
            System.out.println(line); //response 값 출력
        //연결 종료
        reader.close();
    }
    private static void version_error(Socket socket, String method, String version) throws  IOException {
        String msg = method.toUpperCase() + " / HTTP/"+version; //입력값을 대문자로 바꿔서 메시지 생성
        //소켓에 메시지를 전달해주는 PrintWriter 객체 생성
        PrintWriter writer = new PrintWriter(new OutputStreamWriter(socket.getOutputStream()));
        writer.println(msg); //메시지 전송
        writer.flush();
        System.out.println("Data Transfer Finish.");
    }
}