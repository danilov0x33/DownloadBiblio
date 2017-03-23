package ru.danilov.dwbiblio.runnable;

import javafx.application.Application;
import javafx.stage.Stage;
import ru.danilov.dwbiblio.controller.CMainFrame;

public class Launcher extends Application {

	public static void main(String[] args) {
		launch(args);

	}

	@Override
	public void start(Stage primaryStage) throws Exception {
		new CMainFrame().start(primaryStage);
		
	}

}
