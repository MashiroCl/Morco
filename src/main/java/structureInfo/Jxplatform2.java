package structureInfo;

import org.jtool.eclipse.batch.ModelBuilderBatch;
import org.jtool.eclipse.javamodel.JavaProject;

public class Jxplatform2{
	
	private Repository repository;
	private String name;
	private String target;
	private String classpath;
	
	public Jxplatform2(Repository repository) {
		this.repository=repository;
		this.extractConfigure();
	}
	
	public void extractConfigure() {
		classpath=repository.getSourceCodeFile().toString();
		target=repository.getSourceCodeFile().toString();
		name=repository.getName();
	}
	
	public JavaProject extractJavaProject() {
		ModelBuilderBatch builderBatch = new ModelBuilderBatch();
		builderBatch.setLogVisible(true);
		
		return builderBatch.build(name, target, classpath);
		
	}
	
	public String getName() {
		return this.name;
	}
	
	public String getTarget() {
		return this.target;
	}
	
	public String getClassPath() {
	return this.classpath;
	}
	
}
