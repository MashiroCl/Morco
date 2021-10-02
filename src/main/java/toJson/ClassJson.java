package toJson;

import java.util.List;
import java.util.ArrayList;
import java.util.Collections;
import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaField;
import org.jtool.eclipse.javamodel.JavaLocalVar;
import org.jtool.eclipse.javamodel.JavaPackage;
import org.jtool.eclipse.javamodel.JavaFile;

public class ClassJson implements EncodeClassInfo{
	private JavaClassInfo javaClassInfo;
	private String filePath;
	private String javaPackage;
	private String className;
	private List<String> javaFields;
	private List<String> javaMethods;
	private List<List<String>> superClasses;
	
	public ClassJson(JavaClassInfo javaClassInfo) {
		this.javaClassInfo=javaClassInfo;		
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

	
	public List<List<String>> getSuperClasses() {
		return encodeSuperClasses();
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
				this.javaClassInfo.getClass().getModifiers())
				+"#"
				+this.javaClassInfo.getClass().getName();
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
	public List<List<String>> encodeSuperClasses(){
		this.superClasses=new ArrayList<>();
		
		List<List<List<String>>> superClassInfo = new ArrayList<>();
		
		for(JavaClass javaClass:this.javaClassInfo.getSuperClasses()) {
			/*
			 * If the parent is null or java.lang.Object, we won't consider it
			 * */
			if(javaClass==null||javaClass.getQualifiedName().contains("java.")) {
				continue;
			}
			
			System.out.println("--------------start-----------------");
			System.out.print(javaClass);
			System.out.println(javaClass.getQualifiedName());
			System.out.println("--------------end------------------");
			
			JavaClassInfo javaClassInfo = new JavaClassInfo(javaClass,false);
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
			
			superClassInfo.add(methodList);
		}
		return this.superClasses;
	}
	

	

}
	