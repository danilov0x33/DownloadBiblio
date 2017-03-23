package ru.danilov.dwbiblio.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

public class CMainFrame extends Application {

	@FXML
	private Button downloadButton;
	@FXML
	private TextField url;
	@FXML
	private TextField nameBook;
	@FXML
	private TextField countPage;
	
	@FXML
	public void initialize() {
		this.downloadButton.setOnAction(e -> {
			ExecutorService thread = Executors.newSingleThreadExecutor();
			thread.submit(() -> { 
				URL url;
				URLConnection conn;
		
				try {
					int start = 1;
					String replacePage = "/"+start;
					String urlString  = this.url.getText().split("/book/")[1];
					urlString  = "http://www.biblio-online.ru/viewer/getPage/" + urlString + "/";
					File dir = new File(this.nameBook.getText());
					dir.mkdirs();
					
					for (int i = start; i <= Integer.valueOf(countPage.getText()); i++) {
						url = new URL(urlString + i); 
						
																																																						// �����
						conn = url.openConnection();
						conn.setDoOutput(true);
						conn.setDoInput(true);
		
						BufferedInputStream bis = new BufferedInputStream(conn.getInputStream());
						FileOutputStream fos = new FileOutputStream(
								new File(this.nameBook.getText() + "/" + i + ".png"));
		
						int ch;
						while ((ch = bis.read()) != -1) {
							fos.write(ch);
						}
						bis.close();
						fos.flush();
						fos.close();
						
						final Integer count = new Integer(i);
						Platform.runLater(() -> {
							this.downloadButton.setText("[" + count + "] из" + "[" + countPage.getText() +"]");
						});
						TimeUnit.SECONDS.sleep(2);
					}
				} catch (Exception e2) {
					e2.printStackTrace();
				}
			});
			thread.shutdown();
		});
	}

	@Override
	public void start(Stage primaryStage) throws Exception {

		try {
			Parent root = FXMLLoader.load(CMainFrame.class.getResource("/ru/danilov/dwbiblio/view/VMainFrame.fxml"));
			if (root != null) {
				primaryStage.setScene(new Scene(root));
			}
		} catch (IOException e) {
		}

		primaryStage.setTitle("Я пират!");
		primaryStage.setOnCloseRequest(e -> {
			System.exit(0);
		});

		primaryStage.centerOnScreen();
		primaryStage.show();
	}
}
