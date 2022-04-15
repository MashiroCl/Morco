package toJson;
import toJson.*;
import structureInfo.*;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaProject;

public class JavaClassInfoTest {
	
	@Test
	void testSetSuperClasses_DisabledListener_has_one_parent_class() {
		String repoPath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador";
		Jxplatform2 jxplatform2= new Jxplatform2(new Repository(repoPath));
		JavaProject javaProject = jxplatform2.extractJavaProject();
		List<JavaClass> javaClasses = javaProject.getClasses();
		List<JavaClass> fakeParentClasses = new ArrayList<>();
		fakeParentClasses.add(javaClasses.get(1));
		fakeParentClasses.add(javaClasses.get(2));
		JavaClassInfo testChildClass = new JavaClassInfo(javaClasses.get(1));
		testChildClass.setSuperClasses(javaClasses);
		assertEquals(1,testChildClass.getSuperClasses().size());
		
	}
}
