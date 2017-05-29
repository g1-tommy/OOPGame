package kr.ac.ajou.oop.managers;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;

import kr.ac.ajou.oop.user.User;

@SuppressWarnings("serial")
public final class FileManager implements Serializable {

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
		String hint = "";
		int c;
		FileInputStream fis = new FileInputStream("guide" + level + ".txt");

		while ((c = fis.read()) != -1)
			hint += (char) fis.read();

		fis.close();
		return hint;
	}

	public static String loadAnswerCode(int level) throws FileNotFoundException, IOException {
		String code;

		BufferedReader br = new BufferedReader(new FileReader("answer" + level + ".txt"));
		StringBuilder sb = new StringBuilder();
		String line = br.readLine();

		while (line != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
			line = br.readLine();
		}
		
		code = sb.toString();
		br.close();
		return code;
	}

}
