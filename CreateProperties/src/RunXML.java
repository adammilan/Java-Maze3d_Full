import java.beans.XMLEncoder;
import java.io.BufferedOutputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;

public class RunXML {

	public static void main(String[] args) {

		Properties prop = new Properties();
		prop.setNumOfThreads(10);
		prop.setGenerateMazeAlgorithm("Simple");
		prop.setSolveMazeAlgorithm("DFS");

		XMLEncoder xmlEncoder = null;
		try {
			xmlEncoder = new XMLEncoder(new FileOutputStream("properties.xml"));
			xmlEncoder.writeObject(prop);

		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} finally {
			xmlEncoder.close();
		}
		System.out.println("Done writing");
	}
}
