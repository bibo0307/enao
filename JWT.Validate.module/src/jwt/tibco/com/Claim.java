package jwt.tibco.com;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.Serializable;

public class Claim implements Serializable {
	private static final long serialVersionUID = 7960567494553263091L;
	
	private String key;
	private String value;
	
	public Claim() { setKey(""); setValue(""); }
	public Claim(String key, String value) { setKey(key); setValue(value); }

	public String getKey() { return this.key; }
	public void setKey(String key) { this.key = key; }
	
	public String getValue() { return this.value; }
	public void setValue(String value) { this.value = value; }

	private void readObject(ObjectInputStream aInputStream) throws ClassNotFoundException, IOException {
		//always perform the default de-serialization first
		aInputStream.defaultReadObject();
	}

	private void writeObject(ObjectOutputStream aOutputStream) throws IOException {
		//perform the default serialization for all non-transient, non-static fields
		aOutputStream.defaultWriteObject();
	}
}
