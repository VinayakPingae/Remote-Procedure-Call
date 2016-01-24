

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TBinaryProtocol;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TSocket;
import org.apache.thrift.transport.TTransport;
import org.apache.thrift.transport.TTransportException;
/**
 * This is the java client file which has been added in the class to perform operation related to the clients
* --operation [operation], where operation can be "read", "write", and "list", and should invoke the respective remote procedure call
* --filename [filename], should be used as input to specify either a local file to be written to the remote system, or a 
* --user [user], should specify the owner of the file for the current operation
 * @author Vinayak
 *
 */
public class JavaClient {
	public static List<File> fileInAFolder = new ArrayList<File>();
	
		public static void main(String[] args) {
		TTransport transport;
		/** 
		 * Arguments for the thrift has been specified over here
		 */
		String hostName = null;
		int clientPortNumber;
		String operation = null;
		String operationType = null;
		String operationFileName = null;
		String userName =null;
		
		/**
		 * Argument checking for the variable length arguments
		 */
		if(args.length !=6 && args.length != 8) {
			System.err.println("Please provide arguments in  Following format: \n<hostName> <portNumber> --operation <operationName> --filename <fileName> --user <user>");
			System.exit(1);
		}
		  
		hostName = args[0].trim().toLowerCase();
		clientPortNumber = Integer.parseInt(args[1].trim());
		/**
		 * Checking the argi=ument for different operation list , write and read
		 */
		int count = 0;
		for(int i=2; i<args.length; i++) {
			operation = args[i].trim().toLowerCase();
			if(operation.equals("--operation")) {
				operationType = args[i+1].trim().toLowerCase();
				if(operationType.equals("list") && args.length > 6) {
					System.err.println("The argument length for list operation is incorrect  provide usage as : client remote01.cs.binghamton.edu 9090 --operation list --user nonexist");
					System.exit(1);
				} else {
					count +=1;
				}
			} else if(operation.equals("--filename")) {
				operationFileName = args[i+1].trim().toLowerCase();
				count +=1;
			} else if(operation.equals("--user")) {
				userName = args[i+1].trim().toLowerCase();
				count +=1;
			}
			
		}
		try {
			transport = new TSocket(hostName,clientPortNumber);
			transport.open();
			// Binary protocl has been used for marshalling and unmarshalling the parameters
			TProtocol protocol = new  TBinaryProtocol(transport);
		    FileStore.Client client = new FileStore.Client(protocol);
		    perform(client,operationType,operationFileName,userName);
		    transport.close();
		} catch (TTransportException e) {
			SystemException systemException = new SystemException();
			systemException.setMessage("Connection refused occurred while opening the code . Check the server if its ready or the <portNumber>");
			System.exit(1);
		}
		/* 
		0- localhost
		1- 9090
		2- --operation
		3- read
		4- --filename
		5- example.txt
		6- --user
		7- guest 
		*/
		
	}

	/**
	 * Perform operation which accepts the client and the operation as well as the filename and the user operations.	
	 * @param client
	 * @param operationType
	 * @param operationFileName
	 * @param userName
	 */
	public static void perform(FileStore.Client client, String operationType, String operationFileName, String userName ) {
		TIOStreamTransport transport = new TIOStreamTransport(System.out);
		TProtocol jsonProtocol = new TJSONProtocol.Factory().getProtocol(transport);
		if(operationType.equalsIgnoreCase("read")) {
			try {
				RFile rFile = client.readFile(operationFileName, userName);
				rFile.write(jsonProtocol);
				System.out.println();
			}  catch (SystemException e1) {
				try {
					e1.write(jsonProtocol);
				} catch (TException e) {
					System.err.println("Exception jas occurrent while generating the system exception write protocol function in the perform operatio of client");
					System.exit(1);
				}
			} catch (TException e) {
				System.err.println("Exception jas occurrent while generating the system exception write protocol function in the server");
				System.exit(1);
			} 
		} if(operationType.equalsIgnoreCase("write")) {
			
			RFile rFile = new RFile();
			RFileMetadata rFileMetadata = new RFileMetadata();
			String contents_File  = readFile(operationFileName);
			if(!contents_File.equals("void")) { 
				rFileMetadata.setOwner(userName);
				rFileMetadata.setFilename(operationFileName);
				rFile.setMeta(rFileMetadata);
				rFile.setContent(contents_File);
				
				try {
					StatusReport status = client.writeFile(rFile);
					status.write(jsonProtocol);
					System.out.println();
				} catch (TException e) {
					System.err.println("Exception jas occurrent while generating the system exception write protocol function in the status report class");
					System.exit(1);
				}
			} if(contents_File.equals("void")){
				// If it returns void from the other end then handle this things
				SystemException systemException = new SystemException();
				systemException.setMessage("File Does not exist");
				try {
					systemException.write(jsonProtocol);
				} catch (TException e) {
					System.err.println("Exception jas occurrent while generating the system exception write protocol in the file does not exist.");
					System.exit(1);
				}
			
			}  
		} if(operationType.equalsIgnoreCase("list")) {
			try {
				List<RFileMetadata> struct = client.listOwnedFiles(userName);
				jsonProtocol.writeListBegin(new org.apache.thrift.protocol.TList(org.apache.thrift.protocol.TType.STRUCT, struct.size()));
		        for (RFileMetadata _iter3 : struct) {
		              _iter3.write(jsonProtocol);
		        }
		        jsonProtocol.writeListEnd();
			System.out.println();
			}catch (SystemException e) {
				try {
					e.write(jsonProtocol);
				} catch (TException e1) {
					// TODO Auto-generated catch block
					System.err.println("Exception jas occurrent while generating the system exception write protocol function in the cliet");
					System.exit(1);
				}
			}
			catch (TException e) {
				System.err.println("Exception jas occurrent while generating the system exception write protocol function in the client");
				System.exit(1);
			}
		}
		
	}
	/**
	 * List files in a particular folder
	 * @param folder
	 */
	public static void listFilesForFolder(File folder) {
	    for (File fileEntry : folder.listFiles()) {
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry);
	        } else {
	        	fileInAFolder.add(fileEntry);
	        	//System.out.println("-----"+fileEntry.getName());
	        }
	    }
	}
	/**
	 * Read the contents of the file
	 * @param fileName
	 * @return the string which needs to be read from the file
	 */
	public static String readFile(String fileName) {
		File file = new File("./files/");
		listFilesForFolder(file);
		StringBuilder stringBuilder = new StringBuilder();
		String lineFromFile = null;
		for(File singleFileFromList : fileInAFolder) {
			if(fileName.equals(singleFileFromList.getName())) {
				FileProcessor fileProcessor = new FileProcessor(singleFileFromList.getAbsolutePath(),singleFileFromList.getAbsolutePath());
				fileProcessor.openFile();
				while((lineFromFile = fileProcessor.readFromFile()) != null) {
					stringBuilder.append(lineFromFile);
				}
				return stringBuilder.toString();
			}
		}
		return "void";
	}
}
