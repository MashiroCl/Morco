package toJson;

import java.util.List;

import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaProject;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import structureInfo.Jxplatform2;
import structureInfo.Repository;

public class ClassJsonTest {
	
	@Test
	void testEncodeSuperClasses_DisabledListener_has_one_parent_class() {
		String repoPath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador";
		Jxplatform2 jxplatform2= new Jxplatform2(new Repository(repoPath));
		JavaProject javaProject = jxplatform2.extractJavaProject();
		List<JavaClass> javaClasses = javaProject.getClasses();
		JavaClassInfo testChildClass = new JavaClassInfo(javaClasses.get(1));
		ClassJson classJson = new ClassJson(testChildClass,javaClasses,repoPath);
		List<List<List<String>>> superClasses = classJson.encodeSuperClasses();
		assertEquals(1,superClasses.size());
	}
}
