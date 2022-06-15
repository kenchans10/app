package com.java_backend;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.nio.file.DirectoryNotEmptyException;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardWatchEventKinds;
import java.nio.file.WatchEvent;
import java.nio.file.WatchKey;
import java.nio.file.WatchService;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.DriverManager;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.Map;

import static java.lang.Integer.parseInt;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JavaBackendApplication {

	public static void main(String[] args) {
		SpringApplication.run(JavaBackendApplication.class, args);

		
		try {
			WatchService watchService = FileSystems.getDefault().newWatchService();

			Path path = Paths.get("csv");
			File folder = new File("csv");
			path.register(watchService, StandardWatchEventKinds.ENTRY_MODIFY);

			WatchKey key;
			while ((key = watchService.take()) != null) {
				for (WatchEvent<?> event : key.pollEvents()) {

					CallApi(event.context().toString().substring(0, event.context().toString().lastIndexOf(".")));
					try {
			            Files.delete(Paths.get(folder.getAbsolutePath() +"\\"+ event.context()));
			        } catch (NoSuchFileException x) {
//			            System.err.format("%s: no such" + " file or directory%n", path);
			        } catch (DirectoryNotEmptyException x) {
//			            System.err.format("%s not empty%n", path);
			        } catch (IOException x) {
			            // File permission problems are caught here.
//			            System.err.println(x);
			        }
				}
				
				key.reset();
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
	
	public static void CallApi(String name) {
		try {
			URL url = null;
			
			if(name.equals("ref_state")) {
				url = new URL("http://localhost:8080/api/ref_state/generate");
			}
			
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");

			if (conn.getResponseCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
			}

			conn.disconnect();

		} catch (MalformedURLException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();

		}
	}
}