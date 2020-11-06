
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.util.Scanner;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tat Gia Vi
 */
public class Client {
    public static int buffsize=4096;
    public static int lz=1234;
    public static String host="localhost";
    public static void main(String[] args){
        DatagramSocket socket;
        DatagramPacket dsend;
        DatagramPacket dreceive;
        InetAddress add; Scanner stdin;
        try{
            dreceive= new DatagramPacket(new byte[buffsize],buffsize);
            add=InetAddress.getByName(host);
            socket=new DatagramSocket();
            stdin= new Scanner(System.in);
            while(true){
                System.out.print("Client input:");
                String tmp=stdin.nextLine();
                byte[] pack=tmp.getBytes();
                dsend= new DatagramPacket(pack,pack.length,add,lz);
                System.out.println("Client sent to"+add.getHostAddress()+"at port:"+socket.getLocalPort());
                socket.send(dsend);
                if(tmp.equalsIgnoreCase("bye")){
                    System.out.println("Good bye");
                    stdin.close();
                    socket.close();
                    break;
                }
                socket.receive(dreceive);
                String tmp1= new String(dreceive.getData(),0,dreceive.getLength());
                System.out.println("Receive:"+tmp1);
            }
        }
        catch(IOException e){
            System.err.println(e);
        }
    }
}
