package elementDependency;

import java.util.List;

import org.junit.jupiter.api.Test;

import elementDependency.FieldAccessExtractor;
import structureInfo.Repository;

public class FieldAccessExtractorTest {
	private Repository repo = new Repository("/Users/leichen/Desktop/Student");
//	private Repository repo = new Repository("/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador");
	
	@Test
	public void testExtract() {
		FieldAccessExtractor fae = new FieldAccessExtractor(repo);
		List<ClassLevelCallGraph> clcgs = fae.extract();
		for(ClassLevelCallGraph clcg:clcgs) {
			System.out.println(clcg.toString());
		}
		
	}
	
	@Test
	public void testBuildCallIn() {
		FieldAccessExtractor fae = new FieldAccessExtractor(repo);
		List<ClassLevelCallGraph> clcgs = fae.extract();
		List<ClassLevelCallGraph> callins = fae.buildCallIn(clcgs);
		for(ClassLevelCallGraph callin:callins) {
			System.out.println(callin.toString());
		}
	}
}
