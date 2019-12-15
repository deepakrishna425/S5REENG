# S5REENG

Project Description: 

The Summer Scientific Seminar Scheduling Software (S5) schedules students for seminar workshops based on their order of preference, the offering times of the workshops, and maximum and minimum enrollments in each workshop.  The software is designed to do the scheduling fairly by randomly ordering the students first. 

The scheduling software was originally written in FORTRAN, then extensively redesigned, significantly improved, and written in Ada by Maj Tim Chamillard, DFCS, and Capt Pam Magee, DFAS.  That version was then enhanced by Maj Chamillard to use a Graphical User Interface (GUI).
Ada version of this project has roughly around 1500 LOC (Lines of Code). ADA version of S5 can only run on a 32-bit Windows 7 machine. 

Proposed Maintenance Objectives: 

1.	Platform Independence: The S5 project needs to be able to run on any platform (Windows or Linux) and on any architecture (32-bit or 64-bit) machines. 

2.	Object Oriented Design: Simple and Easy to understand, cleaner implementation and code reusability. 

3.	Read Input Data from Excel: Reading data from excel instead of text files. Users will not be needed to understand how to format data in text files for S5 program to read it successfully. 

Maintainability Evaluation:

Pros: 
1.	Structured and Modular approach taken while implementing project. Each file, package, procedure, function and variables have been given meaningful names.
2.	Decent documentation available for majority of the files.
3.	Smaller codebase makes it easier to understand and make changes

Cons: 
1.	Legacy language used to build the project – Ada. Would be challenging to meet modern UI standards and performance.
2.	Project is not compatible with modern operating systems. Makes it harder to work and test the project locally on maintainer’s machine.
3.	No standard data format used to store “student” and “workshop” information.   

Maintenance Plan:

1.	Understand the purpose and high level working of the S5 project by reading the User Manual attached with the project. 
•	Estimated Effort: 5 hrs 
•	Actual Effort: 5 hrs

2.	Understand the implementation / logic by going through the code files. 
•	Estimated Effort: 10 hrs 
•	Actual Effort: 10 hrs

3.	Apply object-oriented design concepts to the code and identify the High-level objects and relationships among them. 
•	Estimated Effort: 5 hrs 
•	Actual Effort: 5 hrs

4.	Implement the new design using an object-oriented language. 
•	Estimated Effort: 48 hrs 
•	Actual Effort: 48 hrs

5.	Test and confirm if the new design adheres to the original requirements and achieves the maintenance objectives.  
•	Estimated Effort: 5 hrs 
•	Actual Effort: 5 hrs

Maintenance Results:

1.	Achieved Platform Independence: I have used Java as the new language to reengineer the S5 project. Since Java is a platform independent language, it inherently makes S5 also platform independent enabling it to be hosted or be able to run on any operating system.

2.	OOD: By modifying procedural design into an object-oriented design, I have made the S5 system less complex to understand and I have also taken advantage of code reusability to reduce the overall lines of code. 

3.	Maintainability: It is easier to find human resources who can modify and maintain the Reengineered S5 project than the procedural S5 project. 

4.	Input Data Format: The Reengineered code is capable for reading the data in the old format (.txt) as well as reading the data from an excel sheet. Having capability for reading from both formats enables it to be used on data that already exists in the .txt format as well as be able to specify the data in excel which should be the data format preferred going ahead. 
