package toJson;

import java.util.List;

import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaField;
import org.jtool.eclipse.javamodel.JavaFile;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaPackage;

public class JavaClassInfo {
	private JavaClass javaClass;
	private List<JavaField> javaFields;
	private List<JavaMethod> javaMethods;
	private JavaClass superClass;
//	private List<JavaClass> children;
	private JavaPackage javaPackage;
	private JavaFile javaFile;
	private String filePath;
	
	@Deprecated
	public JavaClassInfo (JavaClass javaClass) {
		this.javaClass=javaClass;
		this.javaPackage=this.javaClass.getPackage();
		this.javaFields=this.javaClass.getFields();
		this.javaMethods=this.javaClass.getMethods();
		this.javaFile=this.javaClass.getFile();
		this.superClass=this.javaClass.getSuperClass();
	}
	
	public JavaClassInfo (JavaClass javaClass,boolean withSuperClasses) {
		this.javaClass=javaClass;
		this.javaPackage=this.javaClass.getPackage();
		this.javaFields=this.javaClass.getFields();
		this.javaMethods=this.javaClass.getMethods();
		this.javaFile=this.javaClass.getFile();
		if(withSuperClasses==true) {
			this.superClass=this.javaClass.getSuperClass();
		}
		else {
			this.superClass=null;
		}
	}
	
	public List<JavaField> getJavaFields() {
		return javaFields;
	}

	public List<JavaMethod> getJavaMethods() {
		return javaMethods;
	}

	public JavaPackage getJavaPackage() {
		return javaPackage;
	}

	public JavaFile getJavaFile() {
		return javaFile;
	}

	public String getFilePath() {
		return filePath;
	}
	
	public JavaClass getJavaClass() {
		return this.javaClass;
	}
	
	public void setSuperClasses() {
		this.superClass=this.javaClass.getSuperClass();
	}
	
	public JavaClass getSuperClasses() {
		return this.superClass;
	}
	
}
