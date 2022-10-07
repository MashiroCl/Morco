package structureInfo;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Repository {
	private Path path;
	private Path name;
	private Path sourceCodeFile;
	
	public Path getSourceCodeFile() {
		return this.sourceCodeFile;
	}
	
	public Path getPath() {
		return this.path;
	}
	
	public String getName() {
		return this.name.toString();
	}
	
	public Repository(String path) {
		this.path=Paths.get(path);
		this.name=this.path.getFileName();
		this.sourceCodeFile=Paths.get(this.path.toString());
	}
}
