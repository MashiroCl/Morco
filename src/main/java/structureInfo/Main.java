package structureInfo;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaProject;

import toJson.*;
import com.alibaba.fastjson.JSON;
import java.nio.file.Paths;
import java.nio.file.Path;

public class Main {

	public static void main(String[] args) {
//		String repoPath=args[0];		
//		String outputJsonPath=args[1];
		String repoPath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/sshj";
		String outputJsonPath = "/Users/leichen/Desktop/sshj.json";
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
		String localPath = Paths.get(repoPath).getParent().toString();
		List<ClassJson> classJsonList = new ArrayList<ClassJson>();
		
//		filter out class like com.zaxxer.hikari.util.ConcurrentBag#ConcurrentBag( com.zaxxer.hikari.util.ConcurrentBag.IBagStateListener )$1
		Iterator<JavaClass> iterator = javaClasses.iterator();
		while (iterator.hasNext()) {
			JavaClass curClass = iterator.next();
			if(curClass.getQualifiedName().contains("$")) {
				iterator.remove();
			}
		}
		
//		for(JavaClass eachClass: javaClasses) {
//			System.out.println(eachClass.getName());
//			System.out.println(javaClasses.size());
//		}
		
		for(JavaClass eachClass:javaClasses) {
			JavaClassInfo javaClassInfo = new JavaClassInfo(eachClass);
			ClassJson classJson = new ClassJson(javaClassInfo,javaClasses,localPath);
			classJsonList.add(classJson);
		}
		return classJsonList;
	}

}
