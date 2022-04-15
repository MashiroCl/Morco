package elementDependency;

import org.jtool.eclipse.javamodel.JavaElement;
import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaMethod;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;

public class ClassLevelCallGraph {
	private JavaClass javaclass;
	private HashMap<JavaElement, List<JavaElement>> methodCall;
	
	public ClassLevelCallGraph() {}
	
	public ClassLevelCallGraph(JavaClass javaclass) {
		this.javaclass = javaclass;
	}
	
	public String getName() {
		return this.javaclass.getQualifiedName();
	}
	
	public String getPath() {
		return this.javaclass.getFile().getRelativePath();
	}
	
	public JavaClass getJavaClass() {
		return this.javaclass;
	}
	
	public void setMethodCall(HashMap<JavaElement, List<JavaElement>> methodCall) {
		this.methodCall = methodCall;
	}
	
	public HashMap<JavaElement, List<JavaElement>> getMethodCall(){
		return this.methodCall;
	}
	
	@Override
	public String toString() {
		StringBuilder sb = new StringBuilder();
		for(JavaElement javaelement: methodCall.keySet()) {
			sb.append("CALLER: ");
			sb.append(javaelement.getQualifiedName());
			sb.append(" CALLEE: ");
			for(JavaElement callee: methodCall.get(javaelement)) {
				sb.append(callee.getQualifiedName());
				sb.append(", ");
			}
		}
		return "CLASS: "+javaclass.getQualifiedName()+" METHOD CALL:{ " + sb.toString()+"}";
	}
	
	public List<String> getCallOut() {
		List<String> res = new LinkedList<>();
		for(JavaElement javaelement: methodCall.keySet()) {
			res.add(javaelement.getFile().getRelativePath());
		}
		return res;
	}
	
	
}
