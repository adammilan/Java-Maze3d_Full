package properties;

import java.beans.XMLDecoder;
import java.io.BufferedInputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class PropertiesLoader {

	private static PropertiesLoader instance;

	private Properties properties;

	public Properties getProperties() {
		return properties;
	}

	public PropertiesLoader() {

	    // XMLDecoder decoder = new XMLDecoder(new FileInputStream("properties.xml"));
		XMLDecoder decoder = new XMLDecoder(getClass().getClassLoader().getResourceAsStream("properties.xml"));
		properties = (Properties) decoder.readObject();
		decoder.close();

		// System.out.println("properties has been initial");
	}

	public static PropertiesLoader getInstance() {
		if (instance == null)
			instance = new PropertiesLoader();
		return instance;
	}

}
