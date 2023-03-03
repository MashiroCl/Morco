package elementDependency;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.jtool.eclipse.cfg.CFGFieldEntry;
import org.jtool.eclipse.cfg.CFGMethodEntry;
import org.jtool.eclipse.cfg.CFGNode;
import org.jtool.eclipse.cfg.ControlFlow;
import org.jtool.eclipse.javamodel.JavaClass;
import org.jtool.eclipse.javamodel.JavaElement;
import org.jtool.eclipse.javamodel.JavaField;
import org.jtool.eclipse.javamodel.JavaMethod;
import org.jtool.eclipse.pdg.ClDG;

import structureInfo.Repository;

public class FieldAccessExtractor extends DependencyExtractor{
	
	public FieldAccessExtractor(Repository repository) {
		super(repository);
	}
	
	public boolean excludeSibling(CFGNode cfgnode, JavaClass srcclass) {
		return ((CFGFieldEntry)cfgnode).getJavaField().getDeclaringClass().equals(srcclass);
	}

	@Override
	public List<ClassLevelCallGraph> extract() {
		List<ClassLevelCallGraph> res = new LinkedList<>();
		for (JavaClass jclass : getJavaProject().getClasses()) {
			//set class name as the name of an item in call graph
			ClassLevelCallGraph callgraph = new ClassLevelCallGraph(jclass);
			//each class contain a map, key: caller method value: callee methods
			HashMap<JavaElement, List<JavaElement>> hashMap = new HashMap<>();
			//obtain class dependence graph (required before obtaining call graph)
			try {
				ClDG cldg = getBuilder().getClDG(jclass);
			}
			catch (NullPointerException e) {
				continue;
			}
		    
		    
			for(JavaMethod srcmethod: jclass.getMethods()) {
				List<JavaElement> callees = new LinkedList<>();
				for(ControlFlow edge: getBuilder().getCallGraph(srcmethod).getEdges()) {
					//callee is a method, exclude sibling callee
					CFGNode dstNode = edge.getDstNode();
					if(dstNode.getClass().equals(CFGFieldEntry.class) && !excludeSibling(dstNode, jclass)) {
						callees.add(((CFGFieldEntry)dstNode).getJavaField());
					}
				}
				//put method which has a call to other methods in map
				if(callees.size()!=0) {
					hashMap.put(srcmethod, callees);
				}
			}

			callgraph.setMethodCall(hashMap);
			res.add(callgraph);
	}
		
		return res;
	}
	
	//build call-in graph using built call out graph
	public List<ClassLevelCallGraph> buildCallIn(List<ClassLevelCallGraph> callOutGraph){
		
		Map<JavaClass, HashMap<JavaElement, List<JavaElement>>> classrecord = new HashMap<>();
		List<ClassLevelCallGraph> res = new LinkedList<>();
		for(ClassLevelCallGraph clcg:callOutGraph) {
			//obtain caller:src callee:dst from callOutGraph built by extract()
			for(JavaElement src:clcg.getMethodCall().keySet()) {
				for(JavaElement dst:clcg.getMethodCall().get(src)) {
					//if callee is a JavaMethod
					if(dst.getClass().equals(JavaField.class)) {
						JavaClass curclass = ((JavaField)dst).getDeclaringClass();
						HashMap<JavaElement, List<JavaElement>> singlecall = classrecord.getOrDefault(curclass, new HashMap<>());
						List<JavaElement> singlecallee =  singlecall.getOrDefault(dst, new LinkedList<JavaElement>());
						singlecallee.add(src);
						singlecall.put(dst, singlecallee);
						classrecord.put(curclass, singlecall);
					}
				}
			}
		}
		
		for (JavaClass javaclass:classrecord.keySet()) {
			ClassLevelCallGraph clcg = new ClassLevelCallGraph(javaclass);
			clcg.setMethodCall(classrecord.get(javaclass));
			res.add(clcg);
		}
		
		return res;
	}
	
	
}
