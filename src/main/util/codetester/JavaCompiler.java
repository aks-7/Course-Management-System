package main.util.codetester;

import main.util.sshcommands.SSHCommands;
import net.neoremind.sshxcute.exception.TaskExecFailException;

public class JavaCompiler extends Languages{
	
	public String errormessage="";
	//public String s="";
	public JavaCompiler(){
		super("");
	}
	/* Provide the path upto the directory in which the assignment is e.g  cms/MA201_ashish_prashant/uploads/assignment1/*/
	/* Provide the complete filename */
	public int compile(String path, String filename)throws Exception
	{
		System.out.println("In compile path : "+filename);
		SSHCommands sh=new SSHCommands();
		filename.trim();
		String command1 = "cd "+path;
		String temp=filename.substring(filename.indexOf('_')+1);
		sh.runSingleCommand("mv "+path+filename+" "+path+temp);
		sh.close();
		String command2 = "javac "+temp;
		System.out.println("FILENAME::"+command2);
		String com[]={command1,command2};
		sh=new SSHCommands();
		s = sh.runMultipleCommand(com);
		sh.close();
		sh=new SSHCommands();
		sh.runSingleCommand("mv "+path+temp+" "+path+filename);
		sh.close();
		if(s.startsWith("error")){
			errormessage=s.substring(6);
			return 1;
		}
		return 0;
	}
	public int compile(String path, String filename,boolean rename)throws Exception
	{	
		System.out.println("In compile path : "+filename);
		SSHCommands sh=new SSHCommands();
		filename.trim();
		String command1 = "cd "+path;
		String temp=filename;
		if(rename)
		{
			temp=filename.substring(filename.indexOf('_')+1);
			sh.runSingleCommand("mv "+path+filename+" "+path+temp);
			
		}
		sh.close();
		String command2 = "javac "+temp;
		System.out.println("FILENAME::"+command2);
		String com[]={command1,command2};
		sh=new SSHCommands();
		s = sh.runMultipleCommand(com);
		sh.close();
		if(rename)
		{	
			sh=new SSHCommands();
			sh.runSingleCommand("mv "+path+temp+" "+path+filename);
			sh.close();
		}
		if(s.startsWith("error")){
			errormessage=s.substring(6);
			return 1;
		}
			
		return 0;
	}
	/* Provide the path upto the directory in which the assignment is*/
	/* Provide the complete filename */
	/* Provide the name of the input file */
	public int execute(String path, String filename, String inputfile)throws Exception
	{
		SSHCommands sh=new SSHCommands();
		filename.trim();
		inputfile.trim();
		String temp=filename.substring(filename.indexOf('_')+1);
		sh.runSingleCommand("mv "+path+filename+" "+path+temp);
		sh.close();
		String command1 = "cd "+path;
		String fn = temp.substring(0, temp.length()-5);
		System.out.println("FILE"+fn);
		String command2 = "java "+fn+" < inputFiles/"+inputfile+" > "+"out.txt";
		System.out.println("COOOOOOOOOOOMMMMMMMMMAAAAAAAANNNNNNNNNDDDDDDDDDDD"+command2);
		String com[]={command1,command2};
		sh=new SSHCommands();
		ExecuteProgram ep = new ExecuteProgram(sh,com,this);
		Thread.sleep(4000);
		sh.close();
		sh=new SSHCommands();
		sh.runSingleCommand("mv "+path+temp+" "+path+filename);
		sh.close();
		if(!ep.t.getState().equals(Thread.State.TERMINATED))
		{System.out.println("ksjtimeout");
			errormessage = "terminated";
			ep.t.stop();
			
			return 2;
		}
		if(s.startsWith("error")){
			errormessage=s.substring(6);
			System.out.println("ksj:"+errormessage);
			return 1;
		}
		
		errormessage = "Successful";
		return 0;
	}
	
	public int execute1(String path, String filename, String inputfile)throws Exception
	{
		SSHCommands sh=new SSHCommands();
		filename.trim();
		inputfile.trim();
		String command1 = "cd "+path;
		String fn = filename.substring(0, filename.length()-5);
		System.out.println("FILE"+fn);
		String command2 = "java "+fn+" < inputFiles/"+inputfile+" > "+" outputFiles/"+inputfile;
		System.out.println("COOOOOOOOOOOMMMMMMMMMAAAAAAAANNNNNNNNNDDDDDDDDDDD"+command2);
		String com[]={command1,command2};
		ExecuteProgram ep = new ExecuteProgram(sh,com,this);
		Thread.sleep(4000);
		
		if(!ep.t.getState().equals(Thread.State.TERMINATED))
		{
			errormessage = "terminated";
			ep.t.stop();
			return 2;
		}
		if(s.startsWith("error")){
			errormessage=s.substring(6);
			return 1;
		}
		
		errormessage = "Successful";
		return 0;
	}

	
	
	public String getErrormessage() {
		return errormessage;
	}

	public void setErrormessage(String errormessage) {
		this.errormessage = errormessage;
	}

	public static void main(String arg[])throws Exception
	{
		JavaCompiler obj = new JavaCompiler();
		
		String path = "/home/stud/btech/cse/2015/kshitij.cs15/test/";
		String filename = "testing.java";
		String inputfile = "input2.txt";
		int res = obj.compile(path,filename);
		
		System.out.println("Inside Main now :: \n\n ");
		
		if(res == 1)
		{
			System.out.println("Compile Time Error\n\n" + obj.errormessage);
			System.exit(1);
		}
		
		res = obj.execute(path,filename,inputfile);
		
		System.out.println("Inside Main now :: \n\n ");
		
		if(res == 1)
		{
			System.out.println("Run Time Error\n\n" + obj.errormessage);
			System.exit(1);
		}
		if (res == 2)
		{
			obj.errormessage="Timed Out";
			System.out.println("TimeOut Error\n\n" + obj.errormessage);
			System.exit(1);
		}
		System.out.println("Successful");
		
	}
	
	/*class ExecuteProgram implements Runnable{
		SSHCommands sh;
		String cmd[];
		Thread t;
		public ExecuteProgram(SSHCommands sh, String cmd[]){
			this.sh=sh;
			this.cmd=cmd;
			 t = new Thread(this,"Execute Program");
			t.start();
		}
		
		public void run(){
			try {
			s=sh.runMultipleCommand(cmd);
			} catch (TaskExecFailException e) {
				// TODO Auto-generated catch block
				//e.printStackTrace();
			}finally{
				sh.close();
			}
		}
	}*/
}
