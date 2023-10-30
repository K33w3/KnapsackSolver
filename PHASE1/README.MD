# Project 1-1 Group 39

Welcome to Group 39's solution to Phase 1 of the Project! We were tasked with solving a Pentomino game using different grids and pieces.  Here decent optimisation and fast algorithms are the play!


## Group Members

- Eskil Gjerde Sviggum
- Octavian Covalciuc
- Alexander Kadri
- Andrei Visoiu
- Jack Bikar 
- Vasileios Rallis
- Patrick Van Maele


## Prerequisites

1. Java Development Kit (JDK): At least version 20.0.2 
2. VS Code, Intellij, Sublime or any text editor or IDE of the choice

## Instructions
Follow the steps below to correctly run the code:
        
###  1.Navigate to the Source Code Folder:

Using either a terminal or command prompt and navigate to 'src/model/' to find
the **Search.java** file.
            
The command you issue should look something like:
            
            cd /path/to/project/src/model 
        
###  2. Edit Input Array and Grid Size:
Open the 'Search.java' file using a test editor or IDE of your choice. Using either VS Code, Intellij,any text-editor or IDE you should be able to open the 'Search.java' and modify the values of the HORIZONTAL_GRID_SIZE and VERTICAL_GRID_SIZE. The **Search.java** file should have, at the beginning of the class, 2 constants. 

Example HORIZONTAL_GRID_SIZE and VERTICAL_GRID_SIZE:
                
                public static final int HORIZONTAL_GRID_SIZE = 8;
                public static final int  VERTICAL_GRID_SIZE = 5; 

To modify the values of the pieces, please change the characters within the **INPUT** Array. 

Example INPUT Array:

                  public static final char[] INPUT = { 'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y' }; //{'X', 'I', 'Z', 'T', 'U', 'V', 'W', 'Y', 'L', 'P'};


           

     
###  3. Compile the Code
Compile the 'Search.java' file making use of the **javac** command. This will generate the executable.

```bash
javac -d target src/**/*.java
```

### 4. Run the Code
Execute the compiled Java file using the command 'java'.

```bash
java -cp target model.Search
```

> NOTE: To build and run, you can use the following command: 
> ```bash
> javac -d target src/**/*.java && java -cp target model.Search
> ```

Once run you may choose in the terminal, which algorithm to use. You can choose between 5 choices :

            0. Searching using the best algorithm based on the grid size.
            1. Searching using deterministic brute force
            2. Searching using random brute force
            3. Searching using branching with flooding algorithm
            4. Searching using branching for grid's with size 8x5

After choosing a GUI window will pop up and if a solution exists , show you a solution.
 

## Additional Notes
- Make sure that Java is correctly installed.
- Do not move the 'Search.Java' file from the src/model folders.
    
For more specific details concering the functionality and customization, please consult the codes official documentation found
    under the docs folder. 

## Generating Documentation

Documentation can be generated using the following command:

```bash
javadoc -d doc -sourcepath src **/*.java
```