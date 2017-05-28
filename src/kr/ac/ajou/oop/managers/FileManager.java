package kr.ac.ajou.oop.managers;

import kr.ac.ajou.oop.user.User;

import java.io.*;

public final class FileManager implements Serializable {

    private FileManager() {}
//  To prevent creating an object
//   1. Make constructor private
//   2. Make class abstract

    public static void saveUser(User u) throws IOException {
        FileOutputStream fos = new FileOutputStream("user.dat");
        ObjectOutputStream oos = new ObjectOutputStream(fos);

        oos.writeObject(u);
        oos.close();
    }

    public static String loadGuidance(int level) throws IOException, ClassNotFoundException {
        String hint;

        FileInputStream fis = new FileInputStream("guide"+level+".dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        hint = (String)ois.readObject();
        ois.close();

        return hint;

    }

    public static String loadAnswerCode(int level) throws IOException, ClassNotFoundException {
        String code;

        FileInputStream fis = new FileInputStream("answer"+level+".dat");
        ObjectInputStream ois = new ObjectInputStream(fis);

        code = (String)ois.readObject();
        ois.close();

        return code;
    }

}
