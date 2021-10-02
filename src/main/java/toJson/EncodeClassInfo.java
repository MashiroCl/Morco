package toJson;

import java.util.List;

public interface EncodeClassInfo {
	String encodeFilePath();
	String encodeJavaPackage();
	String encodeClassName();
	List<String> encodeJavaFields();
	List<String> encodeJavaMethods();
	List<List<String>> encodeSuperClasses();
}
