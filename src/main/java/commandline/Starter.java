package commandline;

import picocli.CommandLine;
import picocli.CommandLine.Command;
import picocli.CommandLine.Option;
import picocli.CommandLine.Parameters;

import java.util.concurrent.Callable;
import structureInfo.Main;
import elementDependency.ExtractStarter;

@Command(name = "Static Analysis",
		description = "Extract abstract representation and call relations from java files")
public class Starter implements Callable<Integer>{

	@Option(names= {"-a","--abs"},description="Extract abstract representation")
	private boolean  abs;
	@Option(names= {"-c","--call"},description="Extract call relationship")
	private boolean  callr;
	
	@Parameters(index = "0", description = "The repository path which will be used to extract abstract representation or call relationship")
	private String repoPath;
	@Parameters(index = "1",description = "The output path")
	private String outputPath;
	

	@Override
	public Integer call() throws Exception{
		if(abs) {
			Main.extract(repoPath, outputPath);
		}
		else if(callr) {
			ExtractStarter.extract(repoPath, outputPath);
		}
		else {
			return 1;
		}
		return 0;
		
	}
	
	public static void main(String [] args) {
		int exitCode = new CommandLine(new Starter()).execute(args);
		if(exitCode==1) {
			System.out.println("Missing option '-a' or '-c'");
		}
		System.exit(exitCode);
	}
	
}
