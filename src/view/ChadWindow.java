package view;

import java.util.ArrayList;

import control.ChadController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.control.ToggleGroup;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import javafx.stage.Window;

public class ChadWindow extends Stage{
	
	private Scene scene;
	private HBox hbox;
	private VBox vbox1;
	private HBox hboxAux;
	private TextArea textChat;
	private TextField chatWithUser;
	private Button send;
	private ToggleButton broadCast;
	private ChadController control;
	
	public ChadWindow() {
		
		hbox = new HBox();
		vbox1 = new VBox();
		hboxAux = new HBox();
		VBox vbox = new VBox();
		textChat = new TextArea();
		chatWithUser = new TextField();
		send = new Button(">");
		broadCast = new ToggleButton("Todos");
		
		vbox1.getChildren().add(broadCast);
		vbox1.setPrefSize(120.0, 120.0);
		hbox.getChildren().add(vbox1);
		hbox.getChildren().add(vbox);
		
		vbox.getChildren().add(textChat);
		vbox.getChildren().add(hboxAux);
		
		hboxAux.getChildren().add(chatWithUser);
		hboxAux.getChildren().add(send);

		scene = new Scene(hbox,600,400);
		this.setScene(scene);

		control = new ChadController(this);
	}

	public void closeWindow() {
		this.close();
	}
	
	public HBox getHbox() {
		return hbox;
	}

	public void setHbox(HBox hbox) {
		this.hbox = hbox;
	}

	public VBox getVbox1() {
		return vbox1;
	}

	public void setVbox1(VBox vbox1) {
		this.vbox1 = vbox1;
	}

	public HBox getHboxAux() {
		return hboxAux;
	}

	public void setHboxAux(HBox hboxAux) {
		this.hboxAux = hboxAux;
	}

	public TextArea getTextChat() {
		return textChat;
	}

	public void setTextChat(TextArea textChat) {
		this.textChat = textChat;
	}

	public TextField getChatWithUser() {
		return chatWithUser;
	}

	public void setChatWithUser(TextField chatWithUser) {
		this.chatWithUser = chatWithUser;
	}

	public Button getSend() {
		return send;
	}

	public void setSend(Button send) {
		this.send = send;
	}

	public ToggleButton getBroadCast() {
		return broadCast;
	}

	public void setBroadCast(ToggleButton broadCast) {
		this.broadCast = broadCast;
	}

	
	
}
