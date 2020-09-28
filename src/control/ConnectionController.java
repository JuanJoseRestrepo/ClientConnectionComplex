package control;

import java.awt.peer.TextComponentPeer;

import com.google.gson.Gson;

import comm.TCPConnection;
import javafx.application.Platform;
import view.ChadWindow;
import view.ConnectionWindow;
import model.*;

public class ConnectionController implements TCPConnection.OnConnectionListener {
	
	private ConnectionWindow view;
	private TCPConnection connection;
	
	
	public ConnectionController(ConnectionWindow view) {
		this.view = view;
		init();
	}
	
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setConnectionListener(this);
		connection.setIp("127.0.0.1");
		connection.setPuerto(5000);
		view.getBtnConnect().setOnAction(
				
				(e) ->{
					connection.start();	
				}
				
				);
	}

	@Override
	public void onConnection(String id) {
		//-----------------------------------------------
		// TODO Auto-generated method stub
		// No se puede utilizar metodos con resultados grafico en un hilo que no sea principal
		if(id.isEmpty()) {
			Platform.runLater(
					
					()->{
						ChadWindow window = new ChadWindow();
						window.show();
						view.close();
					}
					
					
					);	
		}
		
		
	}

	@Override
	public void onConnectionListener() {
		
		String nombre = view.getTextNombre().getText();
		UserMessage m = new UserMessage(nombre,"Conectado","");
		Gson gson = new Gson();
		String msj = gson.toJson(m);
		connection.getEmisor().setMessage(msj);
			
	}
	

}
