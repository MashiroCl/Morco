package structureInfo;

import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaProject;

import toJson.*;
import com.alibaba.fastjson.JSON;


public class Main {

	public static void main(String[] args) {

		String repoPath="/Users/leichen/JAVA/test_code/refactoring-toy-example";
		repoPath="/Users/leichen/ResearchAssistant/InteractiveRebase/data/jedis";
		String outputJsonPath="/Users/leichen/Desktop/res.json";
		
		List<ClassJson> classJsonList = extractClasses(repoPath);
		int classNum = classJsonList.size();
		
		WriteJson writeJson = new WriteJson(outputJsonPath);
		try {
			writeJson.write(JSON.toJSONString(classJsonList));
		}
		catch(IOException e) {
			System.out.println("Output Json file path error");
		}
		
		System.out.println("** Building finished\n");
		
	}
	
	
	
	public static List<ClassJson> extractClasses(String repoPath) {
		
		Jxplatform2 jxplatform2= new Jxplatform2(new Repository(repoPath));
		JavaProject javaProject = jxplatform2.extractJavaProject();
		List<JavaClass> javaClasses = javaProject.getClasses();
		
		List<ClassJson> classJsonList = new ArrayList<ClassJson>();
		for(JavaClass eachClass:javaClasses) {
			JavaClassInfo javaClassInfo = new JavaClassInfo(eachClass,true);
			ClassJson classJson = new ClassJson(javaClassInfo);
			classJsonList.add(classJson);
		}
		return classJsonList;
	}

}
