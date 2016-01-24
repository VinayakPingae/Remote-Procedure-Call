

import org.apache.thrift.TException;
import org.apache.thrift.protocol.TJSONProtocol;
import org.apache.thrift.protocol.TProtocol;
import org.apache.thrift.server.TServer;
import org.apache.thrift.server.TServer.Args;
import org.apache.thrift.server.TSimpleServer;
import org.apache.thrift.transport.TIOStreamTransport;
import org.apache.thrift.transport.TServerSocket;
import org.apache.thrift.transport.TServerTransport;
/**
 * Description - Java server which will listen on the port provided in the arguments
 * @author Vinayak
 *
 */
public class JavaServer {
	
	public static FileStore.Processor<FileStore.Iface> processor = null;
	public static JavaFileHandler fileHandler = null;
	
	public static void main(String[] args) {
	
		TIOStreamTransport transport = new TIOStreamTransport(System.out);
		TProtocol jsonProtocol = new TJSONProtocol.Factory().getProtocol(transport);
		fileHandler = new JavaFileHandler();
		processor = new FileStore.Processor<FileStore.Iface>(fileHandler);
		int portNumber = 0;
		try {
			
		if(args.length != 1) {
			SystemException systemException = new SystemException();
			systemException.setMessage("Please provide the argument properly for the JavaServer <portNumber>");
			throw systemException;
			}
		}catch (SystemException e) {
			try {
					e.write(jsonProtocol);
				} catch (TException e1) {
					System.err.println("Exception jas occurrent while generating the system exception write protocol function in the server");
					System.exit(1);
				}
		}
		portNumber = Integer.parseInt(args[0]);
		simple(processor, portNumber);
	}
	/**
	 * The simple server is generated for the specified version of the clients
	 * @param processor
	 * @param portNumber
	 */
	public static void simple(FileStore.Processor<FileStore.Iface> processor, int portNumber) {
	    try {
	      TServerTransport serverTransport = new TServerSocket(portNumber);
	      TServer server = new TSimpleServer(new Args(serverTransport).processor(processor));

	      // Use this for a multithreaded server
	      // TServer server = new TThreadPoolServer(new TThreadPoolServer.Args(serverTransport).processor(processor));

	      System.out.println("Starting the simple server...");
	      server.serve();
	      serverTransport.close();
	    } catch (Exception e) {
	      System.err.println("The exception has occurred while starting the server. Try using on other port");
	      System.exit(1);
	    }
	  }

}
