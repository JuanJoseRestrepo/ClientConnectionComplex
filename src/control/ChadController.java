package control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import javafx.application.Platform;
import javafx.scene.control.ToggleGroup;
import model.DirectMessage;
import model.Message;
import model.NewConnection;
import comm.TCPConnection;
import view.ChadWindow;

public class ChadController implements OnMessageListener {

	
	private ChadWindow view;
	private TCPConnection connection;
	final ToggleGroup group = new ToggleGroup();
	private ArrayList<NewConnection> conectados;
	
	public ChadController(ChadWindow view) {
		this.view = view;
		conectados = new ArrayList<NewConnection>();
		init();
	}
	
	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListerOnMessage(this);
		
		
		view.getSend().setOnAction(
				(e) ->{
					if(view.getBroadCast().isSelected()) {
						if(!view.getChatWithUser().getText().isEmpty()) {
						String id = UUID.randomUUID().toString();
						String date = Calendar.getInstance().getTime().toString();
						String msg = view.getChatWithUser().getText();
						Message msgObj = new Message(id,msg,date);
						Gson gson = new Gson();
						String json = gson.toJson(msgObj);
						connection.getEmisor().setMessage(json);
						
						view.getChatWithUser().setText("");	
						}
					}else {
					
					}
					
				}
				
				);
		
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
					System.out.println("Aqui toy");
					if(msjObj.getType().equalsIgnoreCase("NewConnection")) {
						
						NewConnection m = gson.fromJson(message, NewConnection.class);
						view.getTextChat().appendText("<<<"+ m.getId() + " : "+ m.getBody() + "\n");
						conectados.add(m);
						System.out.println(conectados.size());

						
					}else {
						view.getTextChat().appendText("<<<"+ msjObj.getDate() + " : "+ msjObj.getBody() + "\n");
					}
			
				}
				
				
				);
		
	}
	
	
	public void createButtonsForUsers() {
		Platform.runLater(
				()->{
					
				
					
				}
				
				);
	
		
	}



	
}
