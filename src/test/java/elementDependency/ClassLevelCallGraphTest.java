package elementDependency;

import org.junit.jupiter.api.Test;

import structureInfo.Repository;

import java.util.List;

public class ClassLevelCallGraphTest {
	private Repository repo = new Repository("/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador");
	
	@Test
	public void testGetMethodCallOut() {
		MethodCallExtractor mce = new MethodCallExtractor(repo);
		List<ClassLevelCallGraph> clcgs = mce.extract();
		for(ClassLevelCallGraph clcg:clcgs) {
			if(clcg.getCallOut().size()!=0) {
			System.out.print(clcg.getPath()+" ");
			System.out.println(clcg.getCallOut());	
			}	
			}
	}
	
	@Test
	public void testGetFieldCallOut() {
		FieldAccessExtractor mce = new FieldAccessExtractor(repo);
		List<ClassLevelCallGraph> clcgs = mce.extract();
		for(ClassLevelCallGraph clcg:clcgs) {
			if(clcg.getCallOut().size()!=0) {
			System.out.print(clcg.getPath()+" ");
			System.out.println(clcg.getCallOut());	
			}	
			}
	}
}
