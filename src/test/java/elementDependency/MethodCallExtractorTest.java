package elementDependency;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;
import structureInfo.Repository;
import elementDependency.MethodCallExtractor;

public class MethodCallExtractorTest {
//	private Repository repo = new Repository("/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador");
	private Repository repo = new Repository("/Users/leichen/Desktop/Student");

	@Test
	public void testExtract() {
		MethodCallExtractor mce = new MethodCallExtractor(repo);
		List<ClassLevelCallGraph> clcgs = mce.extract();
		for(ClassLevelCallGraph clcg:clcgs) {
			System.out.println(clcg.toString());
		}
		
	}
	
	@Test
	public void testBuildCallIn() {
		MethodCallExtractor mce = new MethodCallExtractor(repo);
		List<ClassLevelCallGraph> clcgs = mce.extract();
		List<ClassLevelCallGraph> callins = mce.buildCallIn(clcgs);
		for(ClassLevelCallGraph callin:callins) {
			System.out.println(callin.toString());
		}
	}

}
