

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CopyOnWriteArrayList;

import org.apache.thrift.TException;
/**
 * Description - This class is the file handler which performs the core operations for the application 
 * @author Vinayak
 *
 */
public class JavaFileHandler implements FileStore.Iface {
	// static hash table for the values of the owner and its contents
	public static Hashtable<String, CopyOnWriteArrayList<RFile>> fileNameandContents = null;
	/**
	 * Delete the contents of the file
	 * @param file
	 * @throws IOException
	 */
	public static void delete(File file) throws IOException{
    	if(file.isDirectory()){
    		if(file.list().length==0){
    		    file.delete();
    		    System.out.println("Directory is deleted : " + file.getAbsolutePath());
    		}else {
    			String files[] = file.list();
        	   for (String temp : files) {
        	      File fileDelete = new File(file, temp);
        	      delete(fileDelete);
        	   }if(file.list().length==0){
           	     file.delete();
        	     System.out.println("Directory is deleted : " + file.getAbsolutePath());
        	   }
    		}
    	}else {
    		file.delete();
    		System.out.println("File is deleted : " + file.getAbsolutePath());
    	}
	 }
	
	public JavaFileHandler() {
		fileNameandContents = new Hashtable<String, CopyOnWriteArrayList<RFile>>();
		File fileDeleteion = new File("./files/writtenFilehandler/");	
		try {
			delete(fileDeleteion);
		} catch (IOException e) {
			System.err.println("IO Exception has occurred while deleting the files from the folder.");
			System.exit(1);
		}
	}
	/**
	 * List the files which are owned by a particular owner
	 */
	@Override
	public List<RFileMetadata> listOwnedFiles(String user) throws SystemException, TException {
		// TODO Auto-generated method stub
		System.out.println("The Server has got the request for Listing Owned Files operation");
		CopyOnWriteArrayList<RFile> rFile = null ;
		Set<String> keySet = fileNameandContents.keySet();
		List<RFileMetadata> listOfOwnedFiles  = new ArrayList<RFileMetadata>(fileNameandContents.size());
		Iterator<String> keySetIterator = keySet.iterator();
		while(keySetIterator.hasNext()){
			String key = keySetIterator.next();
			rFile = fileNameandContents.get(key);
			for(RFile rfileSingle : rFile) {
				if(rfileSingle.getMeta().getOwner().equals(user)) {
					listOfOwnedFiles.add(rfileSingle.getMeta());
				}
			}
		}
		if(listOfOwnedFiles.isEmpty()) {
			SystemException exception= new SystemException();
			exception.setMessage("User "+user+" does not exist.");
			throw exception;
		} else {
			List<RFileMetadata> listOfOwnedFiles_unique  = new ArrayList<RFileMetadata>(fileNameandContents.size());
			
			for(int i =0; i < listOfOwnedFiles.size(); i++) {
				RFileMetadata fileMetadata = listOfOwnedFiles.get(i);
				for(int j=0; j<listOfOwnedFiles.size(); j++) {
					RFileMetadata fileMetadata_second = listOfOwnedFiles.get(j);
					if(fileMetadata.getFilename().equals(fileMetadata_second.getFilename())) {
						if(fileMetadata.getVersion() > fileMetadata_second.getVersion()) {
							listOfOwnedFiles_unique.add(fileMetadata);
							break;
						} else {
							listOfOwnedFiles_unique.add(fileMetadata_second);
							break;
						}
						
					} 
				}
			}
			HashSet<RFileMetadata> set = new HashSet<>(listOfOwnedFiles_unique);
			// Create ArrayList from the set.
			ArrayList<RFileMetadata> result = new ArrayList<>(set);
			return result;
		}
		
	}

public static Comparator<RFileMetadata> FruitNameComparator = new Comparator<RFileMetadata>() {

	public int compare(RFileMetadata rfileMetadata_one, RFileMetadata rfileMetadat_two) {
	Integer version_one = rfileMetadata_one.getVersion();
	Integer version_two = rfileMetadat_two.getVersion();
	return version_one.compareTo(version_two);
}

};

	@Override
	public StatusReport writeFile(RFile rFile) throws SystemException,TException {
		System.out.println("The Server has got the request for Write File");
		// TODO Auto-generated method stub
		File file = null;
		RFile rFile_WriteOperation = rFile;
		RFileMetadata rFileMetadata_WriteOperation = rFile.getMeta();
		
		int index = 0;
		// Iterate through the file name and find whether its the first version of the file that has been passed or not
			if(fileNameandContents.containsKey(rFile_WriteOperation.getMeta().getOwner())) {
			  	CopyOnWriteArrayList<RFile> existingRFile = fileNameandContents.get(rFile_WriteOperation.getMeta().getOwner());
			  	//System.out.println("ABOVE ::: "+fileNameandContents.containsKey(rFile_WriteOperation.getMeta().getOwner())+"    index_element  "+index_element);
			  	//System.out.println("FIRST ENTRY OWNER IS "+"   rFile_WriteOperation.getMeta().getOwner()   "+rFile_WriteOperation.getMeta().getOwner() +" + ------ "+existingRFile.toString());
			    for (int i = 0; i < existingRFile.size(); i++)
			    {
			        RFile auction = existingRFile.get(i);
			        if (rFile_WriteOperation.getMeta().getFilename().equals(auction.getMeta().filename)) {
			            index =  i;
			        }
			    } 
			    
			  	for(RFile rfile : existingRFile ) {
			  		RFileMetadata existingFileMetaData = rfile.getMeta();
			  		//System.out.println("2-----------"+existingFileMetaData.getFilename()+"----------------"+(rFile_WriteOperation.getMeta().getFilename()));
			  		if(existingFileMetaData.getFilename().equals(rFile_WriteOperation.getMeta().getFilename())) {
				  		PrintWriter writer;
						try {
							writer = new PrintWriter(new BufferedWriter(new FileWriter("./files/writtenFilehandler/"+existingFileMetaData.getOwner()+"/"+existingFileMetaData.getFilename(), true)));
							writer.write("\n"+rFile_WriteOperation.getContent());
							writer.close();
							
						} catch (IOException e) {
							// TODO Auto-generated catch block
							e.printStackTrace();
						}
						existingFileMetaData.setVersion(existingFileMetaData.getVersion()+1);
						existingFileMetaData.setUpdated(getCurrentTimeStamp());
						// Updating the new content and the content length
						StringBuilder stringBuilder = new StringBuilder();
						stringBuilder.append(rfile.getContent());
						stringBuilder.append("\n");
						stringBuilder.append(rFile_WriteOperation.getContent());
						//System.out.println(rfile.getContent() +"----"+rFile_WriteOperation.getContent());
						// Check here for the contetnts
						rfile.setContent(stringBuilder.toString());
						existingFileMetaData.setContentLength(stringBuilder.length());
						existingFileMetaData.setContentHash(generateMD5hashFunction(stringBuilder.toString()));
						rfile.setMeta(existingFileMetaData);
						//System.out.println(existingFileMetaData.toString()+"   "+rfile.toString()+"  index "+index);		
						existingRFile.set(index, rfile);
						fileNameandContents.put(rfile.getMeta().getOwner(), existingRFile);
						//System.out.println("fileNameandContents FIRST IF "+fileNameandContents.toString());
						//break;
			  		} else if(!existingFileMetaData.getFilename().equals(rFile_WriteOperation.getMeta().getFilename())){
			  			try {
							file = new File("./files/writtenFilehandler/"+rFile_WriteOperation.getMeta().getOwner()+"/"+rFile_WriteOperation.getMeta().filename);
							if (!file.exists()) {
								file.createNewFile();
							}
							FileWriter fw = new FileWriter(file.getAbsoluteFile());
							BufferedWriter bw = new BufferedWriter(fw);
							bw.write(rFile_WriteOperation.getContent());
							bw.close();
						} catch (IOException e) {
							SystemException systemException = new SystemException();
							systemException.setMessage("Error ocuurred while writing the file to the server side");
						}
						
						rFileMetadata_WriteOperation.setVersion(0);
						rFileMetadata_WriteOperation.setCreated(getCurrentTimeStamp());
						rFileMetadata_WriteOperation.setUpdated(getCurrentTimeStamp());
						rFileMetadata_WriteOperation.setContentHash(generateMD5hashFunction(rFile_WriteOperation.getContent()));
						rFileMetadata_WriteOperation.setContentLength(rFile_WriteOperation.getContent().length());
						rFile_WriteOperation.setMeta(rFileMetadata_WriteOperation);
						existingRFile.add(rFile_WriteOperation);
						fileNameandContents.put(rFile_WriteOperation.getMeta().getOwner(),existingRFile);
						//System.out.println("fileNameandContents FIRST ELSE "+fileNameandContents.toString());
				  	}
			  	}
			  		StatusReport statusReport = new StatusReport();
					statusReport.status = Status.SUCCESSFUL;
					return statusReport; 
		  } else {

				// When its the first file
			  	//System.out.println("INSIDE ELSE");
				try {
					boolean isDirectoryCreated ;
					new File("./files/writtenFilehandler/").mkdir();
					isDirectoryCreated = new File("./files/writtenFilehandler/"+rFile_WriteOperation.getMeta().getOwner()).mkdir();
					file = new File("./files/writtenFilehandler/"+rFile_WriteOperation.getMeta().getOwner()+"/"+rFile_WriteOperation.getMeta().filename);
					if (!file.exists() || isDirectoryCreated) {
						file.createNewFile();
					}
			
					FileWriter fw = new FileWriter(file.getAbsoluteFile());
					BufferedWriter bw = new BufferedWriter(fw);
					bw.write(rFile_WriteOperation.getContent());
					bw.close();
				} catch (IOException e) {
					SystemException systemException = new SystemException();
					systemException.setMessage("Error ocuurred while writing the file to the server side");
				}
				
				/*
				 * When the server receives this request, it will fill in the rest of the fields, 
				 * i.e., time of creation, update, version, content length in bytes, and md5 hash of the content. 
				 */
				rFileMetadata_WriteOperation.setVersion(0);
				rFileMetadata_WriteOperation.setCreated(getCurrentTimeStamp());
				rFileMetadata_WriteOperation.setUpdated(getCurrentTimeStamp());
				rFileMetadata_WriteOperation.setContentHash(generateMD5hashFunction(rFile_WriteOperation.getContent()));
				rFileMetadata_WriteOperation.setContentLength(rFile_WriteOperation.getContent().length());
				rFile_WriteOperation.setMeta(rFileMetadata_WriteOperation);
				
				// putting the first contents of the file for operation
				CopyOnWriteArrayList<RFile> listFirst = new CopyOnWriteArrayList<RFile>();
				listFirst.add(rFile_WriteOperation);
				fileNameandContents.put(rFile_WriteOperation.getMeta().getOwner(),listFirst);
				
		  }
			//System.out.println(fileNameandContents.toString());
			StatusReport statusReport = new StatusReport();
			statusReport.status = Status.SUCCESSFUL;
			return statusReport;

		}
		

	@Override
	public RFile readFile(String filename, String owner) throws SystemException, TException {
		System.out.println("The Server has got the request for Read File");
		// Response from here goes to the server
		RFile localFileToreturn = null;
		boolean isFound =false;
		CopyOnWriteArrayList<RFile> localRead = fileNameandContents.get(owner);
		if(localRead==null){
			SystemException exception= new SystemException();
			exception.setMessage("File not found.");
			throw exception;
		} else {
			for(RFile rfile: localRead) {
				RFileMetadata rFileMetadata = rfile.getMeta();
				//System.out.println(rFileMetadata.toString());
				if(rFileMetadata.getFilename().equals(filename)) {
					isFound = true;
					localFileToreturn = rfile;
					break;
				} else {
					isFound = false;
				}
			}
		}
		if(isFound) {
			return localFileToreturn;
		} else {
			SystemException exception= new SystemException();
			exception.setMessage("File not found because the owner name is different");
			throw exception;
	
		}
	}
	
	/**
	 * Get the current time stamp of the system and set the created and updated date time.
	 * @return
	 */
	public static long getCurrentTimeStamp() {

		java.util.Date today = new java.util.Date();
		return new java.sql.Timestamp(today.getTime()).getTime();

	}
	/**
	 * Generate the MD5 hash function for the string contents
	 * @param fileContents
	 * @return
	 */
	public static String generateMD5hashFunction(String fileContents) {
        MessageDigest md;
        StringBuffer sb = new StringBuffer();
        try {
			md = MessageDigest.getInstance("MD5");
		       md.update(fileContents.getBytes());
		       byte byteData[] = md.digest();
		        //convert the byte to hex format method 1
		       for (int i = 0; i < byteData.length; i++) {
		         sb.append(Integer.toString((byteData[i] & 0xff) + 0x100, 16).substring(1));
		       }
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
    	return sb.toString();
	}

}
