package elementDependency;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaElement;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.javamodel.JavaField;

import structureInfo.Repository;

/**
 * 
 * @author leichen
 *build simplified call graph by counting number of method calls/field accesses between 2 classes
 */

public class SimplifyCallGraphBuilder {
	
	protected JavaClass getClass(JavaElement javaelement) {
		JavaClass res = null;
		if(javaelement.getClass()==JavaMethod.class) {
			res = ((JavaMethod)javaelement).getDeclaringClass();
		}
		else if(javaelement.getClass()==JavaField.class) {
			res = ((JavaField)javaelement).getDeclaringClass();
		}
		return res;
	}
	
	public Map<String, Integer> build(ClassLevelCallGraph clcg) {
		Map<String, Integer> callNumber = new HashMap<>();
		//all callers in a clcg belongs to a same class
		if(clcg==null) {
			return null;
		}
		for(JavaElement caller:clcg.getMethodCall().keySet()) {
			for(JavaElement callee:clcg.getMethodCall().get(caller)) {
				String curClassPath = getClass(callee).getFile().getRelativePath();
				int num = callNumber.getOrDefault(curClassPath, 0)+1;
				callNumber.put(curClassPath, num);
			}
		}
		return callNumber;
	}
	
}
