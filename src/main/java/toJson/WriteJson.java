package toJson;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;

public class WriteJson {
	
	private String filePath;
	public WriteJson(String filePath) {
		this.filePath=filePath;
	}
	public void write(String content) throws IOException {
		FileWriter fileWriter = new FileWriter(this.filePath);
		PrintWriter out= new PrintWriter(fileWriter);
		out.write(content);
		out.println();
		fileWriter.close();
		out.close();
	}
}
