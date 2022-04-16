package elementDependency;

import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.annotation.JSONField;

/**
 * 
 * @author leichen
 *
 */
public class DetailMethodCall{
	static CallGraphBuilder dcgb = new CallGraphBuilder();
	
	@JSONField(name = "class")
	private String path;
	
	@JSONField(name = "methodCallOut")
	private List<String> methodCallOut;
	
	@JSONField(name = "methodCallIn")
	private List<String> methodCallIn;
	
	@JSONField(name = "fieldAccessOut")
	private List<String> fieldAccessOut;
	
	@JSONField(name = "fieldAccessIn")
	private List<String> fieldAccessIn;
	
	public String getPath() {
		return this.path;
	}
	
	public List<String> getMethodCallOut(){
		return this.methodCallOut;
	}
	
	public List<String> getMethodCallIn(){
		return this.methodCallIn;
	}
	
	public List<String> getFieldAccessOut(){
		return this.fieldAccessOut;
	}
	
	public List<String> getFieldAccessIn(){
		return this.fieldAccessIn;
	}
	
	
	public DetailMethodCall(ClassLevelCallGraph mco, ClassLevelCallGraph mci, ClassLevelCallGraph fao,
			ClassLevelCallGraph fai) {
		this.path = mco.getPath();
		this.methodCallOut = dcgb.detailbuild(mco);
		this.methodCallIn = dcgb.detailbuild(mci);
		this.fieldAccessOut = dcgb.detailbuild(fao);
		this.fieldAccessIn = dcgb.detailbuild(fai);
	}
	
}
