package me.dennisp;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

import java.net.URL;

public class Main extends Application {
	public static URL getViewPath(String viewLoc) {
		return Main.class.getResource("/me/dennisp/UI/view/" + viewLoc + ".fxml");
	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		BorderPane pane = FXMLLoader.load(Main.getViewPath("main_view")); //new Scene(new Pane(), 200, 200);
		Scene scene = new Scene(pane, 800, 600);

		primaryStage.setScene(scene);
		primaryStage.setTitle("JavaFX - Monopoly");
		//primaryStage.setResizable(false);

		primaryStage.show();
	}

	public static void main(String[] args)  {
		Application.launch(args);
	}
}