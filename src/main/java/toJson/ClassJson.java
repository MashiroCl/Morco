package toJson;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaField;
import org.jtool.eclipse.javamodel.JavaLocalVar;


public class ClassJson implements EncodeClassInfo{
	private JavaClassInfo javaClassInfo;
	private List<JavaClass> allClassesContainedInProject;
	private String filePath;
	private String javaPackage;
	private String className;
	private List<String> javaFields;
	private List<String> javaMethods;
	private List<List<List<String>>> superClasses;
	private List<String> childClasses;
	
	public ClassJson(JavaClassInfo javaClassInfo,List<JavaClass> allClassesContainedInProject) {
		this.javaClassInfo=javaClassInfo;		
		this.allClassesContainedInProject=allClassesContainedInProject;
	}
	
	
	public String getFilePath() {
		return encodeFilePath();
	}

	public String getJavaPackage() {
		return encodeJavaPackage();
	}


	public String getClassName() {
		return encodeClassName();
	}

	public List<String> getJavaFields() {
		return encodeJavaFields();
	}

	
	public List<String> getJavaMethods() {
		return encodeJavaMethods();
	}

	
	public List<List<List<String>>> getSuperClasses() {
		return encodeSuperClasses();
	}
	
	public List<String> getChildClasses(){
		return encodeChildClasses();
	}
	
	@Override
	public String encodeFilePath() {
		this.filePath=this.javaClassInfo.
				getJavaFile().getPath();
		return this.filePath;
	}
	
	@Override
	public String encodeJavaPackage() {
		this.javaPackage=this.javaClassInfo.
				getJavaPackage().getName();
		return this.javaPackage;
	}
	
	@Override
	public String encodeClassName() {
		this.className=String.valueOf(
				this.javaClassInfo.getJavaClass().getModifiers())
				+"#"
				+this.javaClassInfo.getJavaClass().getName();
		return this.className;
	}
	
	@Override
	public List<String> encodeJavaFields(){
		this.javaFields=new ArrayList<>();
		
		for(JavaField javaField:this.javaClassInfo.getJavaFields()){
			this.javaFields.add(String.valueOf(
					javaField.getModifiers())
					+"#"
					+javaField.toString().split("\nFIELD: ")[1]);	
		}
		
		return this.javaFields;
	}
	
	@Override
	public List<String> encodeJavaMethods(){
		this.javaMethods = new ArrayList<>();
		
		for(JavaMethod javaMethod:this.javaClassInfo.getJavaMethods()) {
			String modifier = String.valueOf(javaMethod.getModifiers())
					+"@"+javaMethod.getName()
					+"()@"+javaMethod.getReturnType();
			String sum = modifier;
			for (JavaLocalVar javaLocalVar:javaMethod.getParameters()) {
				sum+="#"+javaLocalVar.toString().split("\n")[1];
			}
			this.javaMethods.add(sum);
		}

		return this.javaMethods;
		
	}
	
	@Override
	public List<List<List<String>>> encodeSuperClasses(){
		this.superClasses=new ArrayList<>();
		this.javaClassInfo.setSuperClasses(this.allClassesContainedInProject);
		
		for(JavaClass javaClass:this.javaClassInfo.getSuperClasses()) {
			
			
			JavaClassInfo javaClassInfo = new JavaClassInfo(javaClass);
			/*
			 * Extract class modifer
			 * 			class name 
			 * 			of super class
			 * */
			String className=String.valueOf(
					javaClassInfo.getJavaClass().getModifiers())
					+"#"+javaClassInfo.getJavaClass().getName();
			
			/*
			 * Extract methods modifier
			 * 			 methods return type
			 * 			methods variables
			 * 			of super class
			 * */
			List<String> methods = new ArrayList<>();
			int modifier=0;
			for(JavaMethod javaMethod:javaClassInfo.getJavaMethods()) {
				modifier = javaMethod.getModifiers();
				String method=String.valueOf(modifier)
						+"@"+javaMethod.getName()
						+"()@"+javaMethod.getReturnType();
				for(JavaLocalVar javaLocalVar:javaMethod.getParameters()) {
					method+="#"+javaLocalVar.toString().split("\n")[1];
				}
				methods.add(method);
			}
			List<List<String>> methodList = new ArrayList<>();
			methodList.add(Collections.singletonList(className));
			methodList.add(methods);
			
			this.superClasses.add(methodList);
		}
		return this.superClasses;
	}
	@Override
	public List<String> encodeChildClasses(){
		return this.javaClassInfo.getChildClasses();
		
	}


}
	