package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

import com.google.gson.Gson;

import control.ConnectionController;


public class Receptor extends Thread {

	private InputStream is;
	
	public OnMessageListener listener;
	
	public Receptor(InputStream is){
		this.is = is;
	}
	
	@Override
	public void run() {
		
		BufferedReader breader = new BufferedReader(new InputStreamReader(this.is));
		
		try {
		while(true) {
			
			
				String msj = breader.readLine();
				
				if(listener != null ) {				
					listener.onMessage(msj);
				}
			
		}
		
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public void setList(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public interface OnMessageListener{

		public void onMessage(String message);

	}

	
	
}
