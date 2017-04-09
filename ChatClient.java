
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.PrintStream;
import java.net.Socket;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Jorge
 */
public final class ChatClient {

    public static void main(String[] args) throws Exception {
        try (Socket socket = new Socket("codebank.xyz", 38001)) {
            String recieved;

            System.out.print("Enter username: ");
            InputStream is = socket.getInputStream();
            InputStreamReader isr = new InputStreamReader(is, "UTF-8");
            BufferedReader br = new BufferedReader(isr);

            Runnable send = () -> {
                String sent;
                try {
                    Scanner in = new Scanner(System.in);
                    OutputStream os = socket.getOutputStream();
                    PrintStream out = new PrintStream(os, true);
                    while (true) {
                        sent = in.nextLine();
                        out.println(sent);
                    }
                } catch (IOException e) {
                    System.out.println(e);
                }
            };
            
            Thread sending = new Thread(send);
            sending.start();

            while (true) {
                try {
                    recieved = br.readLine();
                    System.out.println(recieved);
                } catch (IOException e) {
                    System.out.println(e);
                }
            }

        }
    }

}
