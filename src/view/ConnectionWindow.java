package view;

import control.ConnectionController;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

public class ConnectionWindow extends Stage{
	
	private Scene scene;
	private Label labelPrChat;
	private Label labelInstrucciones;
	private TextField textNombre;
	private BorderPane paneInicial;
	private Button btnConnect;

	
	private ConnectionController control;
	
	public ConnectionWindow() {
		
		btnConnect = new Button("Conectar");
		
		labelInstrucciones = new Label("Escoge tu nombre de usuario");
		labelPrChat = new Label("PRChat");
		
		paneInicial = new BorderPane();
		textNombre = new TextField();
		
		VBox vbox = new VBox();
		vbox.getChildren().add(labelPrChat);
		vbox.getChildren().add(labelInstrucciones);
		vbox.getChildren().add(textNombre);
		vbox.getChildren().add(btnConnect);
		paneInicial.setCenter(vbox);
		scene = new Scene(paneInicial,400,400);
		this.setScene(scene);
		control = new ConnectionController(this);
	}

	//Permitiendo acceso al control
	public Label getLabelInstrucciones() {
		return labelInstrucciones;
	}

	public void setLabelInstrucciones(Label labelInstrucciones) {
		this.labelInstrucciones = labelInstrucciones;
	}

	public Button getBtnConnect() {
		return btnConnect;
	}

	public void setBtnConnect(Button btnConnect) {
		this.btnConnect = btnConnect;
	}

	public Label getLabelPrChat() {
		return labelPrChat;
	}

	public void setLabelPrChat(Label labelPrChat) {
		this.labelPrChat = labelPrChat;
	}

	public TextField getTextNombre() {
		return textNombre;
	}

	public void setTextNombre(TextField textNombre) {
		this.textNombre = textNombre;
	}

	public BorderPane getPaneInicial() {
		return paneInicial;
	}

	public void setPaneInicial(BorderPane paneInicial) {
		this.paneInicial = paneInicial;
	}
	
	
	

}
