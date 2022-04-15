package elementDependency;

import java.util.Map;

import com.alibaba.fastjson.*;
import com.alibaba.fastjson.annotation.JSONField;

public class MethodCall {
	//{classPath:{methodCallOut:{A.java:3,B.java:4}, {methodCallIn:{C.java:1,D.java2}, FieldAccessOut:{},FieldAccessIn:{}}}}
	static SimplifyCallGraphBuilder scgb = new SimplifyCallGraphBuilder();
	
	@JSONField(name = "class")
	private String path;
	
	@JSONField(name = "methodCallOut")
	private Map<String, Integer> methodCallOut;
	
	@JSONField(name = "methodCallIn")
	private Map<String, Integer> methodCallIn;
	
	@JSONField(name = "fieldAccessOut")
	private Map<String, Integer> fieldAccessOut;
	
	@JSONField(name = "fieldAccessIn")
	private Map<String, Integer> fieldAccessIn;
	
	public void print() {
		System.out.println("MethodCall is:"+methodCallOut.toString());
	}
	
	public String getPath() {
		return this.path;
	}
	
	public Map<String, Integer> getMethodCallOut(){
		return this.methodCallOut;
	}
	
	public Map<String, Integer> getMethodCallIn(){
		return this.methodCallIn;
	}
	
	public Map<String, Integer> getFieldAccessOut(){
		return this.fieldAccessOut;
	}
	
	public Map<String, Integer> getFieldAccessIn(){
		return this.fieldAccessIn;
	}
	
	
	public MethodCall(ClassLevelCallGraph mco, ClassLevelCallGraph mci, ClassLevelCallGraph fao, ClassLevelCallGraph fai) {
		this.path = mco.getPath();
		this.methodCallOut = scgb.build(mco);
		this.methodCallIn = scgb.build(mci);
		this.fieldAccessOut = scgb.build(fao);
		this.fieldAccessIn = scgb.build(fai);
	}
	
}
