package control;

import java.util.ArrayList;
import java.util.Calendar;

import com.google.gson.Gson;

import comm.Receptor.OnMessageListener;
import javafx.application.Platform;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import main.Launcher;
import model.DirectMessage;
import model.Message;
import model.NewConnection;
import model.UserDisconnect;
import model.UserMessage;
import comm.TCPConnection;
import view.ChadWindow;
import view.ConnectionWindow;

public class ChadController implements OnMessageListener {

	private ChadWindow view;
	private TCPConnection connection;

	private final ToggleGroup group;
	private ArrayList<NewConnection> conectados;

	public ChadController(ChadWindow view) {
		this.view = view;
		view.close();
		group = new ToggleGroup();
		conectados = new ArrayList<NewConnection>();
		init();
	}

	public void init() {
		connection = TCPConnection.getInstance();
		connection.setListerOnMessage(this);

		try {
			view.getSend().setOnAction((e) -> {
				if (!view.getChatWithUser().getText().isEmpty()) {
					if (view.getBroadCast().isSelected()) {
						if (!view.getChatWithUser().getText().isEmpty()) {
							String id = group.getToggles().get(0).getUserData().toString();
							String date = Calendar.getInstance().getTime().toString();
							String msg = view.getChatWithUser().getText();
							Message msgObj = new Message(id, msg, date);
							Gson gson = new Gson();
							String json = gson.toJson(msgObj);
							connection.getEmisor().setMessage(json);

							view.getChatWithUser().setText("");
						}
					} else {
						try {
							if (group.getSelectedToggle().isSelected()) {
								if (!view.getChatWithUser().getText().isEmpty()) {
									String id = group.getToggles().get(0).getUserData().toString();
									String clientId = group.getSelectedToggle().getUserData().toString();
									System.out.println(clientId);
									String date = Calendar.getInstance().getTime().toString();
									String msg = view.getChatWithUser().getText();
									System.out.println(view.getChatWithUser().getText());

									DirectMessage dmsg = new DirectMessage(id, msg, date, clientId);
									Gson gson = new Gson();
									String json = gson.toJson(dmsg);
									connection.getEmisor().setMessage(json);

									view.getChatWithUser().setText("");
								}
							}

						} catch (Exception e1) {
							Alert alert = new Alert(AlertType.INFORMATION);
							alert.setTitle("No hay información master");
							alert.setHeaderText("Por favor, no deje vacio el dialogo");
							alert.setContentText("No seleccionaste a nadie!");

							alert.showAndWait();
						}
					}

				}
			});
		} catch (Exception e) {

			Alert alert = new Alert(AlertType.INFORMATION);
			alert.setTitle("No hay información master");
			alert.setHeaderText("Por favor, no deje vacio el dialogo");
			alert.setContentText("I have a great message for you!");

			alert.showAndWait();

		}

	}

	@Override
	public void onMessage(String message) {
		// ----------------------
		Platform.runLater(

				() -> {
					Gson gson = new Gson();
					Message msjObj = gson.fromJson(message, Message.class);

					if (msjObj.getType().equalsIgnoreCase("NewConnection")) {

						NewConnection m = gson.fromJson(message, NewConnection.class);

						if (m.getBody().isEmpty()) {

							conectados.add(m);

							ToggleButton buton = new ToggleButton(m.getId()+"(yo)");
							buton.setDisable(true);
							buton.setToggleGroup(group);
							buton.setUserData(m.getId());
							
							view.getVbox1().getChildren().add(buton);
							
							view.getTextChat().appendText("<<<" + m.getId() + " : " + "conectado"+ "\n");
							
							System.out.println(conectados.size() + "   : conectados");
						} else {
							reloadStage();
							TCPConnection.reconnect();
							System.out.println(conectados.size() + "  :hey");
						}
					}else if(msjObj.getType().equalsIgnoreCase("UserMessage")) {
						
						UserMessage userM = gson.fromJson(message, UserMessage.class);
	
						
						for(int i = 0; i < userM.getUsuariosEnviados().size();i++) {
							
							if(!verifyRepeatUser(userM.getUsuariosEnviados().get(i).getId())) {
								ToggleButton buton = new ToggleButton(userM.getUsuariosEnviados().get(i).getId());
								buton.setToggleGroup(group);
								buton.setUserData(userM.getUsuariosEnviados().get(i).getId());
								view.getVbox1().getChildren().add(buton);
							}

						}
						System.out.println(group.getToggles().size());
						
				}else if(msjObj.getType().equalsIgnoreCase("UserDisconnect")) {
					
					UserDisconnect userD = gson.fromJson(message, UserDisconnect.class);
					
					ToggleButton butonn = new ToggleButton(userD.getId());
					ToggleButton disconectUser = new ToggleButton(userD.getId() + " " + "Desconectado");
					disconectUser.setDisable(true);
					butonn.setToggleGroup(group);
					
					for(int i = 0; i < group.getToggles().size();i++) {
						if(group.getToggles().get(i).getUserData().equals(userD.getId())) {
							conectados.remove(group.getToggles().get(i).getUserData());
							ToggleButton aux = (ToggleButton) group.getToggles().get(i);
							butonn.getToggleGroup().getToggles().remove(group.getToggles().get(i));
							group.getToggles().remove(group.getToggles().get(i));	
							System.out.println(group.getToggles().size() + "Miraaaa");
							view.getVbox1().getChildren().remove(aux);
							break;
						}
						
					}
			
					System.out.println("   :soy el index xD" +  "   " + userD.getId() + "  index" + group.getToggles().size());
					
					view.getVbox1().getChildren().add(disconectUser);


					view.getTextChat().appendText("<<<" + userD.getId() + " : " + "desconectado" + "\n");
					
				}else {
						
						view.getTextChat().appendText("<<<" + msjObj.getId() + " : " + msjObj.getBody() + "\n");
						
					}

				}

		);

	}

	
	public Boolean verifyRepeatUser(String id) {

		boolean t = false;
		System.out.println(id + "go crazy");
		for (int i = 0; i < group.getToggles().size() && !t; i++) {
			
			if (group.getToggles().get(i).getUserData().toString().equalsIgnoreCase(id)) {

				t = true;

			}
			
		}

		return t;

	}
	
	
	public void reloadStage() {
		
		Platform.runLater(
				
				()->{
					ConnectionWindow window = new ConnectionWindow();
					window.show();
					view.closeWindow();
				}
				
				
				);	
		
	}
	

}
