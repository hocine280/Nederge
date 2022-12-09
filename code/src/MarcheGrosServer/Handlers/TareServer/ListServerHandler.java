package MarcheGrosServer.Handlers.TareServer; 

import java.net.DatagramPacket;
import java.net.DatagramSocket;

import org.json.JSONObject;
import MarcheGrosServer.Handlers.Handler;
import Server.LogManage.LogManager;
import MarcheGrosServer.ManageMarcheGrosServer.StockManage;
import MarcheGrosServer.Requests.RequestsTare.ListServerRequest;

import java.net.InetAddress;
import java.net.SocketException;
import java.io.IOException;
import java.net.UnknownHostException;


public class ListServerHandler extends Handler{

    private final int port = 2025; 
    
    public ListServerHandler(LogManager logManager, StockManage stockManage){
        super(logManager, stockManage);
    }

    public ListServerRequest handle(){
        ListServerRequest listServerRequest = new ListServerRequest();
        
        return listServerRequest;
    }

}