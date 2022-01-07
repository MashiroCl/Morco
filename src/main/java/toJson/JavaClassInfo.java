package toJson;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaField;
import org.jtool.eclipse.javamodel.JavaFile;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaPackage;

public class JavaClassInfo {
	private JavaClass javaClass;
	private List<JavaField> javaFields;
	private List<JavaMethod> javaMethods;
	private List<JavaClass> superClasses;
 	private List<String> childClasses;
	private JavaPackage javaPackage;
	private JavaFile javaFile;
	private String filePath;
		
	public JavaClassInfo (JavaClass javaClass) {
		this.javaClass=javaClass;
		this.javaPackage=this.javaClass.getPackage();
		this.javaFields=this.javaClass.getFields();
		this.javaMethods=this.javaClass.getMethods();
		this.javaFile=this.javaClass.getFile();
		this.superClasses= null;
		this.childClasses = this.javaClass.getChildren().stream().map(JavaClass::getQualifiedName).collect(Collectors.toList());
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
	
	/*
	 * javaClasses: the list of all java classes contains in current surveyed project
	 * */
	public void setSuperClasses(List<JavaClass> javaClasses) {
		this.superClasses = new ArrayList<JavaClass>();
		List<JavaClass> curSuperClasses=this.javaClass.getAllSuperClasses();
		List<String> totalClassQualifiedNames= new ArrayList<>();
		for(JavaClass javaClass:javaClasses) {
			totalClassQualifiedNames.add(javaClass.getQualifiedName());
		}
		/*Filter superclasses if superclasses is not in developer self-defined classes, because Jxplatform2 cannot 
		obtain infomation from library classes
		*/
		for(JavaClass javaClass:curSuperClasses) {
			if(totalClassQualifiedNames.contains(javaClass.getQualifiedName())) {
				this.superClasses.add(javaClass);
			}
		}
	}
	
	public List<JavaClass> getSuperClasses() {
		return this.superClasses;
	}
	public List<String> getChildClasses(){
		return this.childClasses;
	}
	
}
