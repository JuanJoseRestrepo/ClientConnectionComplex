package control;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.UUID;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import javafx.application.Platform;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.Toggle;
import javafx.scene.control.ToggleButton;
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
				if(!view.getChatWithUser().getText().isEmpty() && !view.getBroadCast().isSelected()) {
					if(view.getBroadCast().isSelected()) {
						if(!view.getChatWithUser().getText().isEmpty()) {
						String id = group.getToggles().get(0).getUserData().toString();
						String date = Calendar.getInstance().getTime().toString();
						String msg = view.getChatWithUser().getText();
						Message msgObj = new Message(id,msg,date);
						Gson gson = new Gson();
						String json = gson.toJson(msgObj);
						connection.getEmisor().setMessage(json);
						
						view.getChatWithUser().setText("");	
						}
					}else {
					
						if(group.getSelectedToggle().isSelected()) {
							if(!view.getChatWithUser().getText().isEmpty()) {
									String id = group.getToggles().get(0).getUserData().toString();
							        String clientId = group.getSelectedToggle().getUserData().toString();
							        System.out.println(clientId);
							       	String date = Calendar.getInstance().getTime().toString();
									String msg = view.getChatWithUser().getText();
									System.out.println(view.getChatWithUser().getText());
									
									DirectMessage dmsg = new DirectMessage(id, msg, date, clientId);
									Gson gson = new Gson();
									String json = gson.toJson(dmsg);
									System.out.println(json);
									connection.getEmisor().setMessage(json);
									
									view.getChatWithUser().setText("");	
							}
						}
						
					}
					
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

					if(msjObj.getType().equalsIgnoreCase("NewConnection")) {
						
						NewConnection m = gson.fromJson(message, NewConnection.class);
						
						if(verifyRepeatUser(m.getId())) {
							
							System.out.println("xD");
							
						}else if(group.getToggles().isEmpty()) {
							
							view.getTextChat().appendText("<<<"+ m.getId() + " : "+ "Se ha conectado" + "\n");
							conectados.add(m);
							ToggleButton buton = new ToggleButton(m.getId()+"(yo)");
							buton.setDisable(true);
							buton.setToggleGroup(group);
							buton.setUserData(m.getId());
							
							view.getVbox1().getChildren().add(buton);
							
						}else {
						view.getTextChat().appendText("<<<"+ m.getId() + " : "+ "Se ha conectado" + "\n");	
						conectados.add(m);
						ToggleButton buton = new ToggleButton(m.getId());
						buton.setToggleGroup(group);
						buton.setUserData(m.getId());
						System.out.println(buton.getUserData().toString());
					    view.getVbox1().getChildren().add(buton);
						
						System.out.println(conectados.size());
						

					}
					}else if(msjObj.getType().equalsIgnoreCase("DirectMessage")){
						
						System.out.println("pa las perras");
						DirectMessage direct = gson.fromJson(message, DirectMessage.class);
						
						view.getTextChat().appendText("<<<"+ direct.getId() + " : "+ direct.getBody() + "\n");
						
					}else {
						view.getTextChat().appendText("<<<"+ msjObj.getId() + " : "+ msjObj.getBody() + "\n");
					}
			
				}
				
				
				);
		
	}
	
	
	public Boolean verifyRepeatUser(String id) {
		
		boolean t = false;
		
		for(int i = 0; i < group.getToggles().size() && !t;i++) {
			
			if(group.getToggles().get(i).getUserData().toString().equalsIgnoreCase(id)) {
				
				t = true;
				
			}
			
			
		}
		
		return t;
		
	}
	
	public void createButtonsForUsers() {
		Platform.runLater(
				()->{
					
				
					
				}
				
				);
	
		
	}



	
}
