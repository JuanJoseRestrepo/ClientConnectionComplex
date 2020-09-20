package control;

import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import javafx.application.Platform;
import model.DirectMessage;
import model.Message;
import comm.TCPConnection;
import view.ChadWindow;

public class ChadController implements OnMessageListener {

	
	private ChadWindow view;
	private TCPConnection connection;
	
	public ChadController(ChadWindow view) {
		this.view = view;
		init();
	}
	
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListerOnMessage(this);
		
		
		
		/*view.getSendBtn().setOnAction(
				
				(e) ->{
					
					String id = UUID.randomUUID().toString();
					String date = Calendar.getInstance().getTime().toString();
					String msg = view.getMessageTF().getText();
					Message msgObj = new Message(id,msg,date);
					Gson gson = new Gson();
					
					String json = gson.toJson(msgObj);
					
					connection.getEmisor().setMessage(json);
					view.getMessageTF().setText("");
					//view.getMessageArea().appendText(">>>" + date + " : " + msg + "\n");
				}
				
				
				
				);
		
view.getSendDirectionBtn().setOnAction(
				
				(e) ->{
					
					String id = UUID.randomUUID().toString();
					String date = Calendar.getInstance().getTime().toString();
					String msg = view.getMessageTF().getText();
					String clientId = view.getClientID().getText();
					
					if(clientId.isEmpty()) {
						return;
					}
					
					DirectMessage dmsg = new DirectMessage(id, msg, date, clientId);
					Gson gson = new Gson();
					
					String json = gson.toJson(dmsg);
					connection.getEmisor().setMessage(json);
					view.getMessageTF().setText("");
				}
				
				
				
				);
		
	}
**/
	}
	@Override
	public void onMessage(String message) {
		Platform.runLater(
				
				()->{
					
					Gson gson = new Gson();
					Message msjObj = gson.fromJson(message, Message.class);
					
					view.getTextChat().appendText("<<<"+ msjObj.getDate() + " : "+ msjObj.getBody() + "\n");
				}
				
				
				);
		
	}


	
}
