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
	
	public static Map<String,List<ClassLevelCallGraph>> alignment(List<ClassLevelCallGraph> mocgs, 
			List<ClassLevelCallGraph> mlicgs, 
			List<ClassLevelCallGraph> fas)
	{
			Map<String,List<ClassLevelCallGraph>> res = new HashMap<>();
			for(ClassLevelCallGraph mocg:mocgs) {
			String curPath =mocg.getJavaClass().getFile().getRelativePath();
			List<ClassLevelCallGraph> callInfo = new LinkedList<>();
			callInfo.add(mocg);
			callInfo.add(search(mlicgs, curPath));
			callInfo.add(search(fas, curPath));
			res.put(curPath, callInfo);
			}
			
			return res;
	}
	
	public static List<CallRelationRecorder> startExtract(Repository repo) {
//		List<DetailMethodCall> res = new LinkedList<>(); 	
		List<CallRelationRecorder> res = new LinkedList<>();
		MethodCallExtractor mce = new MethodCallExtractor(repo);
		List<ClassLevelCallGraph> mocgs = mce.extract();
		List<ClassLevelCallGraph> micgs =mce.buildCallIn(mocgs);
		FieldAccessExtractor fae = new FieldAccessExtractor(repo);
//		List<ClassLevelCallGraph> focgs = fae.extract();
//		List<ClassLevelCallGraph> ficgs = fae.buildCallIn(focgs);
		List<ClassLevelCallGraph> fas = fae.extract();
		Map<String,List<ClassLevelCallGraph>> classKeyGraph = alignment(mocgs, micgs, fas);
		for(String path:classKeyGraph.keySet()) {
//			res.add(new DetailMethodCall(classKeyGraph.get(path).get(0),
//					classKeyGraph.get(path).get(1),
//					classKeyGraph.get(path).get(2),
//					classKeyGraph.get(path).get(3)));
			res.add(new CallRelationRecorder(
					classKeyGraph.get(path).get(0),
					classKeyGraph.get(path).get(1),
					classKeyGraph.get(path).get(2)
					));
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
	
	public static void extract(String repoPath, String outputPath) {
		Repository repo = new Repository(repoPath);
		String res = JSON.toJSONString(startExtract(repo), SerializerFeature.WriteMapNullValue);
		try {
			writeFile(outputPath, res);
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("** Call graph extraction finished\n");	
	}
	
	public static void main(String [] args) {
		String repoName = "mbassador";
//		repoName = "redisson";
		String repoPath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/"+repoName+"/";
//		repoPath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/redisson/redisson-spring-data/redisson-spring-data-22/";
		String jsonFilePath = "/Users/leichen/ResearchAssistant/InteractiveRebase/data/"+repoName+"/callgraph.json";
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
