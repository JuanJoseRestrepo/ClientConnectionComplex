package comm;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InterfaceAddress;
import java.net.NetworkInterface;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Calendar;
import java.util.Enumeration;
import java.util.List;
import java.util.Scanner;


import comm.Receptor.OnMessageListener;
import comm.TCPConnection.OnConnectionListener;

public class TCPConnection extends Thread {

	private static TCPConnection instance = null;
	private Socket socket;
	private String ip;
	private int puerto;
	private Receptor receptor;
	private Emisor emisor;
	private OnMessageListener listener;
	private String creador;
	private OnConnectionListener connectionListener;
	
	private  TCPConnection() {
		creador = "";
	}
	
	public static synchronized TCPConnection getInstance() {
		
		if(instance == null) {
			instance = new TCPConnection();
		}
		return instance;
	}
	
	public static void reconnect() {
		
		instance = new TCPConnection();
		
	}
	
	
	@Override
	public void run() {

		try {
			System.out.println("Enviando al servidor");
			socket = new Socket(ip,puerto);
			System.out.println("Conectado =)");
		
			String msj = getCreador();
			connectionListener.onConnection(msj);
			
			receptor = new Receptor(socket.getInputStream());
			receptor.start();
			
			emisor = new Emisor(socket.getOutputStream());
			
			connectionListener.onConnectionListener();
		
			
		}catch(IOException e) {
			
		}
			
	}

	
	
	
	public String getCreador() {
		return creador;
	}

	public void setCreador(String creador) {
		this.creador = creador;
	}

	public void closeSocket() {
		
		try {
			socket.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setConnectionListener(OnConnectionListener connectionListener) {
		this.connectionListener = connectionListener;
	}

	public void setListerOnMessage(OnMessageListener listener) {
		this.receptor.setList(listener);
	}

	public void setPuerto(int puerto) {
	
		this.puerto = puerto;
		
	}

	public Emisor getEmisor() {
		return this.emisor;
		
	}

	public void setIp(String string) {
		this.ip = ip;
	}
	
	
	
	public OnConnectionListener getConnectionListener() {
		return connectionListener;
	}



	public interface OnConnectionListener{
		//------------------------
		public void onConnection(String id);
		public void onConnectionListener();
	
	}
	

}
