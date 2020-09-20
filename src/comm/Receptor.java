package comm;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;


public class Receptor extends Thread {

	private InputStream is;
	
	public OnMessageListener listener;
	
	public Receptor(InputStream is){
		this.is = is;
	}
	
	@Override
	public void run() {
		
		BufferedReader breader = new BufferedReader(new InputStreamReader(this.is));
		
		while(true) {
			
			try {
				String msj = breader.readLine();
				if(msj != null) {
				
				System.out.println("Recibido" +msj);
				listener.onMessage(msj);
				}else {
					
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}
	}
	
	public void setList(OnMessageListener listener) {
		this.listener = listener;
	}
	
	
	public interface OnMessageListener{

		public void onMessage(String message);
	}

	
	
}
