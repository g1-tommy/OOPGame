package kr.ac.ajou.oop.managers;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public class FileManager implements Serializable {

	private FileManager() {}
	// To prevent creating an object
	// 1. Make constructor private
	// 2. Make class abstract

	public static void saveUser(User u) throws IOException {
		FileOutputStream fos = new FileOutputStream("user.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);

		oos.writeObject(u);
		
		oos.close();
	}

	public static String loadGuidance(int level) throws IOException, ClassNotFoundException {

		BufferedReader br = new BufferedReader(new FileReader("guidance" + level + ".txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		
		br.close();
		return sb.toString();
	}

	public static String loadAnswerCode(int level) throws FileNotFoundException, IOException {
		BufferedReader br = new BufferedReader(new FileReader("answer" + level + ".txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		
		br.close();
		return sb.toString();
	}

}
