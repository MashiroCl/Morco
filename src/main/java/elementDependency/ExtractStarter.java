package elementDependency;

import java.io.FileWriter;
import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;

import structureInfo.Repository;

public class ExtractStarter {
	public static ClassLevelCallGraph search(List<ClassLevelCallGraph> clcgs, String path) {
		for(ClassLevelCallGraph clcg:clcgs) {
			if(clcg.getPath().equals(path)) {
				return clcg;
			}
		}
		return null;
	}
	
	public static Map<String,List<ClassLevelCallGraph>> alignment(List<ClassLevelCallGraph> mocgs, 
															List<ClassLevelCallGraph> mlicgs, 
															List<ClassLevelCallGraph> focgs, 
															List<ClassLevelCallGraph> ficgs){
		Map<String,List<ClassLevelCallGraph>> res = new HashMap<>();
		for(ClassLevelCallGraph mocg:mocgs) {
			String curPath =mocg.getJavaClass().getFile().getRelativePath();
			List<ClassLevelCallGraph> callInfo = new LinkedList<>();
			callInfo.add(mocg);
			callInfo.add(search(mlicgs, curPath));
			callInfo.add(search(focgs, curPath));
			callInfo.add(search(ficgs, curPath));
			res.put(curPath, callInfo);
		}
		
		return res;
	}
	
	public static List<DetailMethodCall> startExtract(Repository repo) {
		List<DetailMethodCall> res = new LinkedList<>(); 	

		MethodCallExtractor mce = new MethodCallExtractor(repo);
		List<ClassLevelCallGraph> mocgs = mce.extract();
		List<ClassLevelCallGraph> micgs =mce.buildCallIn(mocgs);
		FieldAccessExtractor fae = new FieldAccessExtractor(repo);
		List<ClassLevelCallGraph> focgs = fae.extract();
		List<ClassLevelCallGraph> ficgs = fae.buildCallIn(focgs);
		Map<String,List<ClassLevelCallGraph>> classKeyGraph = alignment(mocgs, micgs, focgs, ficgs);
		for(String path:classKeyGraph.keySet()) {
			res.add(new DetailMethodCall(classKeyGraph.get(path).get(0),
					classKeyGraph.get(path).get(1),
					classKeyGraph.get(path).get(2),
					classKeyGraph.get(path).get(3)));
		}
		return res;
	}
	
	public static JSONArray toJson(List<MethodCall> methodCallList) {
		JSONArray jsonArray = new JSONArray();
		for(MethodCall each:methodCallList) {
			Object o = JSON.toJSON(each);
			JSON.toJSONString(each);
		}
		return jsonArray;
	}
	
	public static void writeFile(String filePath, String content) throws IOException{
		FileWriter fw = new FileWriter(filePath);
		fw.write(content);
		fw.flush();
		fw.close();
	}
	
	public static void main(String [] args) {
		String repoPath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador";
		String jsonFilePath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/mbassador/MORCOoutput/csv/callgraph.json";
		repoPath = "/Users/leichen/Desktop/Student";
		jsonFilePath = "/Users/leichen/Desktop/student.json";
		Repository repo = new Repository(repoPath);
		String res = JSON.toJSONString(startExtract(repo), SerializerFeature.WriteMapNullValue);
		try {
			writeFile(jsonFilePath, res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println(res);
	
	}
}
