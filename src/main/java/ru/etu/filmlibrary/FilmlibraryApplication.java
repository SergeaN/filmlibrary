package ru.etu.filmlibrary;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

@SpringBootApplication
public class FilmlibraryApplication {

	public static void main(String[] args) {
		SpringApplication.run(FilmlibraryApplication.class, args);
		String url = "jdbc:mysql://localhost:3306/test";
		String username = "SergeaN";
		String password = "loveetu";

		System.out.println("Connecting...");
		try (Connection connection = DriverManager.getConnection(url, username, password)) {
			System.out.println("Connection successful!");
		} catch (SQLException e) {
			System.out.println("Connection failed!");
			e.printStackTrace();
		}
	}

}
