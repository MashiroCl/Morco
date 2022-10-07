package elementDependency;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaElement;
import org.jtool.eclipse.javamodel.JavaFile;

public class CallGraphBuilder extends SimplifyCallGraphBuilder{
	public List<String> detailbuild(ClassLevelCallGraph clcg) {
		List<String> callList = new LinkedList<>();
		//all callers in a clcg belongs to a same class
		if(clcg==null) {
			return null;
		}
		for(JavaElement caller:clcg.getMethodCall().keySet()) {
			for(JavaElement callee:clcg.getMethodCall().get(caller)) {
				JavaClass jc = getClass(callee);
				if(jc==null) continue;
				callList.add(jc.getFile().getRelativePath());
			}
		}
		return callList;
	}
}
