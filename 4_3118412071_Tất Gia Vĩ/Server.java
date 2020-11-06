
import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.StringTokenizer;
import org.json.JSONArray;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.Connection;
import org.jsoup.nodes.Document;
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Tat Gia Vi
 */
public class Server {
    public static int buffsize=4096;
    public static int port=200;
    public static void main(String[] args){
        
        DatagramSocket socket;
        DatagramPacket dreceive;
        DatagramPacket dsend;
        try{
            socket= new DatagramSocket(1234);
            dreceive= new DatagramPacket(new byte[buffsize],buffsize); 
            while(true){
                socket.receive(dreceive);
                String tmp= new String(dreceive.getData(),0,dreceive.getLength());
                if(tmp.equalsIgnoreCase("bye")){
                    System.out.println("Server closed");
                    socket.close();
                    break;
                }
                else{
                StringTokenizer a=new StringTokenizer(tmp,";");
                String comm=a.nextToken();
                if(comm.equalsIgnoreCase("country")){
                    String url="http://api.airvisual.com/v2/countries?&key=9c2dd8c2-1053-43fa-9357-6d3aa876aabc";
                    String userAgent="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
                    Connection.Response respond=  Jsoup.connect(url)
                        .userAgent(userAgent)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();
                        String doc=respond.body();
                    JSONObject obj=new JSONObject(doc);
                    String stat=obj.getString("status");
                    if(stat.equalsIgnoreCase("success"))
                    {
                    JSONArray arr=obj.getJSONArray("data");
                    List<String> country= new ArrayList<String>();
                    for(int i=0;i<arr.length();i++){
                        country.add(arr.getJSONObject(i).getString("country"));
                    }
                    String data=country.toString();
                    byte[] pack=data.getBytes();
                    dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                    socket.send(dsend);
                    }
                    else{
                        byte[] pack=stat.getBytes();
                        dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                        socket.send(dsend);
                    }
                }
                else if(comm.equalsIgnoreCase("state")){
                    String ctry=a.nextToken();
                    String url="http://api.airvisual.com/v2/states?country="+ctry+"&key=9c2dd8c2-1053-43fa-9357-6d3aa876aabc";
                    String userAgent="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
                    Connection.Response respond=  Jsoup.connect(url)
                        .userAgent(userAgent)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();
                        String doc=respond.body();
                    JSONObject obj=new JSONObject(doc);
                    String stat=obj.getString("status");
                    if(stat.equalsIgnoreCase("success"))
                    {
                    JSONArray arr=obj.getJSONArray("data");
                    List<String> country= new ArrayList<String>();
                    for(int i=0;i<arr.length();i++){
                        country.add(arr.getJSONObject(i).getString("state"));
                    }
                    String data=country.toString();
                    byte[] pack=data.getBytes();
                    dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                    socket.send(dsend);
                    }
                     else{
                        byte[] pack=stat.getBytes();
                        dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                        socket.send(dsend);
                     }
                }
                else if(comm.equalsIgnoreCase("city")){
                    String ctry=a.nextToken();
                    String sta=a.nextToken();
                    String url="http://api.airvisual.com/v2/cities?state="+sta+"&country="+ctry+"&key=9c2dd8c2-1053-43fa-9357-6d3aa876aabc";
                    String userAgent="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
                    Connection.Response respond=  Jsoup.connect(url)
                        .userAgent(userAgent)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();
                        String doc=respond.body();
                    JSONObject obj=new JSONObject(doc);
                    String stat=obj.getString("status");
                     if(stat.equalsIgnoreCase("success"))
                     {
                    JSONArray arr=obj.getJSONArray("data");
                    List<String> country= new ArrayList<String>();
                    for(int i=0;i<arr.length();i++){
                        country.add(arr.getJSONObject(i).getString("city"));
                    }
                    String data=country.toString();
                    byte[] pack=data.getBytes();
                    dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                    socket.send(dsend);
                     }
                     else{
                        byte[] pack=stat.getBytes();
                        dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                        socket.send(dsend);
                     }
                }
                else{
                    String ab=a.nextToken();
                    String ac=a.nextToken();
                    String url="http://api.airvisual.com/v2/city?country="+comm+"&state="+ab+"&city="+ac+"&key=9c2dd8c2-1053-43fa-9357-6d3aa876aabc";
                    String userAgent="Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/41.0.2228.0 Safari/537.36";
                    Connection.Response respond=  Jsoup.connect(url)
                        .userAgent(userAgent)
                        .method(Connection.Method.GET)
                        .ignoreContentType(true)
                        .ignoreHttpErrors(true)
                        .execute();
                    String doc=respond.body();
                    JSONObject obj=new JSONObject(doc);
                    String stat=obj.getString("status");
                    if(stat.equalsIgnoreCase("success")){
                        String city=obj.getJSONObject("data").getString("city");
                        String state=obj.getJSONObject("data").getString("state");
                        String country=obj.getJSONObject("data").getString("country");
                        String ts=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getString("ts");
                        int tp=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getInt("tp");
                        int pr=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getInt("pr");
                        int hu=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getInt("hu");
                        int ws=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getInt("ws");
                        int wd=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getInt("wd");
                        String ic=obj.getJSONObject("data").getJSONObject("current").getJSONObject("weather").getString("ic");
                        String ts1=obj.getJSONObject("data").getJSONObject("current").getJSONObject("pollution").getString("ts");
                        int aqius=obj.getJSONObject("data").getJSONObject("current").getJSONObject("pollution").getInt("aqius");
                        String mainus=obj.getJSONObject("data").getJSONObject("current").getJSONObject("pollution").getString("mainus");
                        int aqicn=obj.getJSONObject("data").getJSONObject("current").getJSONObject("pollution").getInt("aqicn");
                        String maincn=obj.getJSONObject("data").getJSONObject("current").getJSONObject("pollution").getString("maincn");
                        String data="Country:"+country+"\n"+"State:"+state+"\n"+"City:"+city+"\n"+"Weather:\n"+"Timestamp:"+ts+"\n"+"Temperature:"+tp+"\n"+"Atmospheric Pressure:"+pr+"\n"+"Humidity:"+hu+"\n"+"Wind speed:"+ws+"\n"+"Wind direction:"+wd+"\n"+"Icon:"+ic+"\n"
                        +"Pollution:\n"+"Timestamp:"+ts1+"\n"+"AQI value in US EPA Standard:"+aqius+"\n"+"Main pollutant for US AQI:"+mainus+"\n"+"AQI value in China MEP Standard:"+aqicn+"\n"+"Main polutant for China AQI:"+maincn;
                        byte[] pack=data.getBytes();
                        dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                        socket.send(dsend);
                    }
                    else{
                        byte[] pack=stat.getBytes();
                        dsend=new DatagramPacket(pack,pack.length,dreceive.getAddress(),dreceive.getPort());
                        socket.send(dsend);
                    }
                }
                }
            }
        }
        catch(IOException e){
         System.err.println(e);               
        }
    }
}
