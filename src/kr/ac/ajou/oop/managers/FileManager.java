package kr.ac.ajou.oop.managers;

import java.io.BufferedReader;
import java.io.File;
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

	public static void saveUser(User u) throws IOException {
		File dir = new File("data/user/");
		if(!dir.exists()) dir.mkdir();
		FileOutputStream fos = new FileOutputStream("data/user/user.dat");
		ObjectOutputStream oos = new ObjectOutputStream(fos);
		oos.writeObject(u);
		oos.close();
	}

	public static String loadGuidance(int level) throws IOException, ClassNotFoundException {

		File file = new File("data/guidances/guidance_" + level + ".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}
		
		br.close();
		return sb.toString();
	}

	public static String loadAnswerCode(int level) throws FileNotFoundException, IOException {
		File file = new File("data/answers/answer_" + level + ".txt");
		BufferedReader br = new BufferedReader(new FileReader(file));
		StringBuilder sb = new StringBuilder();
		String line;

		while ((line = br.readLine()) != null) {
			sb.append(line);
			sb.append(System.lineSeparator());
		}
		
		br.close();
		return sb.toString();
	}

}
