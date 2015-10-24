#Assignment 1

The main xml file is created after generating the required number of people in the class HealthProfileWriter
and marshalling them. It refers to the target execute.HPWriter
The required functions on the xml file have been grouped into four targets  

-execute.HPReader arg  

	The default target, it prints all the content of the file.
	The argument (interpreting the given instructions) refers to the mode in which the content has to be printed.
	In this case only "in detail" has been implemented. 
	
-execute.HPReader.get arg id

	The targets returns the value of the specified attribute (arg) of the person with id as specified.
	As per instruction "Weight" and "Height" have been implemented
	
-execute.HPReader.print id

	Prints a single element of the three based on id
	The implementation covers only "HealthProfile" as instructed
	
-execute.HPReader.criteria operator value
	This target should be used for operations that apply a criteria (in this case the one specified in the instructions) 
	and prints the entries that satisfy it

All these targets refer to the class HealthProfileReader which interprets the required action based on the first argument
and executes the correct method.
The operation of marshalling, unmarshalling and conversion to JSON are done in the respective classes on the file marshalledPeople.xml
The corresponding targets in the build file are self explaning

How to run:  
The ant command execute.evaluation executes the compilation, the generation and all the operations above

notes:  
-when a method does not require to print data and data is instead printed in the main method for verification,
	such output is preceded by ">>>" 

Known issues:  
-command clean does not delete the generated directory in src (on test environment/machine)  
-imports of type com.fasterxml on JSONConverter class are not recognized causing errors when referenced (on test environment/machine).
	The class however do compile and function correctly 
