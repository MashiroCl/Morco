package toJson;

import java.util.List;

public interface EncodeClassInfo {
	String encodeFilePath();
	String encodeJavaPackage();
	String encodeClassName();
	List<String> encodeJavaFields();
	List<String> encodeJavaMethods();
	//List<List<List<String>>> -> [[[className1],[method1,method2,method3]], [[className2],[method1,method2,method3]]]
	List<List<List<String>>> encodeSuperClasses();
	List<String> encodeChildClasses();
}
