Name - Vinayak Subhash Pingale
Email - vpingal1@binghamton.edu

Languages Used - Java

Implementation Details

When Thift processes the interface definition file, fileservice.thrift, it generates method stubs corresponding to the defined services. You must implement methods corresponding to the following server-side stubs:
writeFile given a name, owner, and contents, the corresponding file should be written to the server. Metainformation, including the filename, creation-time, update-time, version (start from 0), owner, content length,
and content hash (use the MD5 hash) should also be stored at the server side. If the filename does not exist on the server, a new file should be created with its version attribute set to 0. Otherwise, the file contents should be overwritten and the version number should be incremented. readFile if a file with a given name and owner exists on the server, both the contents and meta-information should be returned. Otherwise, an exception should be thrown. listOwnedFiles given an user name, all files owned by the user should be listed. If the user does not exist, then an  exception should be thrown.


Sample Output

./server.sh 9091
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest/in1.txt
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest/node.txt
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest/readme.txt
Directory is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_1/in1.txt
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_1/node.txt
Directory is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_1
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_2/readme.txt
Directory is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_2
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_3/in1.txt
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_3/node.txt
File is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_3/readme.txt
Directory is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler/guest_3
Directory is deleted : /import/linux/home/vpingal1/apache thrift/src/./files/writtenFilehandler
Starting the simple server...
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File
The Server has got the request for Write File                             


WRITE 
======================================

./client.sh remote05.cs.binghamton.edu 9090 --operation write --filename in1.txt --user guest_1
^Z
[1]+  Stopped                 ./client.sh remote05.cs.binghamton.edu 9090 --operation write --filename in1.txt --user guest_1
vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename i.txt --user guest_1
{"1":{"str":"File Does not exist"}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename node.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename node.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename node.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$ ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_1
{"1":{"i32":1}}vpingal1@remote01:~/apache thrift/src$       



 ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename node.txt --user guest_2
{"1":{"i32":1}}vpingal1@remote02:~/apache thrift/src$
                                                           
														   
														   
./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_13
{"1":{"i32":1}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_13
{"1":{"i32":1}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename readme.txt --user guest_13
{"1":{"i32":1}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in2.txt --user guest_13
{"1":{"str":"File Does not exist"}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in2.txt --user guest_13
{"1":{"str":"File Does not exist"}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1 --user guest_13
{"1":{"str":"File Does not exist"}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_13
{"1":{"i32":1}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_13
{"1":{"i32":1}}vpingal1@remote03:~/apache thrift/src$  ./client.sh remote05.cs.binghamton.edu 9091 --operation write --filename in1.txt --user guest_13
{"1":{"i32":1}}vpingal1@remote03:~/apache thrift/src$       



READ
=================================

  ./client.sh remote05.cs.binghamton.edu 9091 --operation read --filename in1.txt --user guest_13
{"1":{"rec":{"1":{"str":"in1.txt"},"2":{"i64":1445811717762},"3":{"i64":1445811722578},"4":{"i32":2},"5":{"str":"guest_13"},"6":{"i32":44},"7":{"str":"8d86c79752b7c288d0664a645d169027"}}},"2":{"str":"Performancefgh\nPerformancefgh\nPerformancefgh"}}vpingal1@remote03:~/apache thrift/src$  

LIST
================================

  ./client.sh remote05.cs.binghamton.edu 9091 --operation list --user guest_13
["rec",2,{"1":{"str":"readme.txt"},"2":{"i64":1445811697009},"3":{"i64":1445811699272},"4":{"i32":2},"5":{"str":"guest_13"},"6":{"i32":2291},"7":{"str":"697deaa7ae6698a359b3853ef99534e8"}},{"1":{"str":"in1.txt"},"2":{"i64":1445811717762},"3":{"i64":1445811722578},"4":{"i32":2},"5":{"str":"guest_13"},"6":{"i32":44},"7":{"str":"8d86c79752b7c288d0664a645d169027"}}]