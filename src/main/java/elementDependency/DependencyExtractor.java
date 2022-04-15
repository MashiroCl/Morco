package elementDependency;

import java.util.List;
import org.jtool.eclipse.batch.ModelBuilderBatch;
import org.jtool.eclipse.javamodel.JavaElement;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaProject;

import structureInfo.Repository;


public abstract class DependencyExtractor {
	private ModelBuilderBatch builder;
	private Repository repository;
	private JavaProject javaproject;
	
	public DependencyExtractor(Repository repository) {
		this.repository = repository;
		builder = new ModelBuilderBatch();
		builder.setLogVisible(true);
		javaproject = builder.build(repository.getSourceCodeFile().toString(),
				repository.getSourceCodeFile().toString(), 
				repository.getName());
		
	}
	
	public ModelBuilderBatch getBuilder() {
		return builder;
	}
	
	public JavaProject getJavaProject() {
		return javaproject;
	}
	
	
	public abstract List<ClassLevelCallGraph> extract();
	
}
