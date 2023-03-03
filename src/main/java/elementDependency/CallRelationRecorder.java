package elementDependency;

import java.util.List;

import com.alibaba.fastjson.annotation.JSONField;

public class CallRelationRecorder{
	static CallGraphBuilder dcgb = new CallGraphBuilder();
	
	@JSONField(name = "class")
	private String path;
	
	@JSONField(name = "methodCallOut")
	private List<String> methodCallOut;
	
	@JSONField(name = "methodCallIn")
	private List<String> methodCallIn;
	
	@JSONField(name = "fieldAccess")
	private List<String> fieldAccess;
	
	
	public String getPath() {
		return this.path;
	}
	
	public List<String> getMethodCallOut(){
		return this.methodCallOut;
	}
	
	public List<String> getMethodCallIn(){
		return this.methodCallIn;
	}
	
	public List<String> getFieldAccess(){
		return this.fieldAccess;
	}
	
	public CallRelationRecorder(
			ClassLevelCallGraph mco, 
			ClassLevelCallGraph mci, 
			ClassLevelCallGraph fa) {
		this.path = mco.getPath();
		this.methodCallOut = dcgb.extractMethodCalleeList(mco);
		this.methodCallIn = dcgb.extractMethodCalleeList(mci);
		this.fieldAccess = dcgb.extractFieldAccessList(fa);
	}

}
