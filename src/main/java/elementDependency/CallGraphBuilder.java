package elementDependency;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jtool.eclipse.javamodel.JavaElement;

public class CallGraphBuilder extends SimplifyCallGraphBuilder{
	public List<String> detailbuild(ClassLevelCallGraph clcg) {
		List<String> callList = new LinkedList<>();
		//all callers in a clcg belongs to a same class
		if(clcg==null) {
			return null;
		}
		for(JavaElement caller:clcg.getMethodCall().keySet()) {
			for(JavaElement callee:clcg.getMethodCall().get(caller)) {
				String curClassPath = getClass(callee).getFile().getRelativePath();
				callList.add(curClassPath);
			}
		}
		return callList;
	}
}
