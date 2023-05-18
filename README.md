# LeetCodeExplorer ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white)

This is a project that focuses on the use of data structures.

## 🚀 Running LeetCodeExploere

1. Clone the repository

2. Open the project in your favourite [IntelliJ IDEA](https://www.jetbrains.com/idea/)

3. Install dependencies: opencsv

    IntelliJ IDEA:
    
    - Add opencsv library to the project through Maven 
    
      `File -> Project Structure -> Libraries -> + -> From Maven -> search for com.opencsv:opencsv:5.7.0 -> add -> apply`
      
    Eclipse:
    
    - Download the [opencsv JAR file](https://sourceforge.net/projects/opencsv/) and [commons-lang3 JAR file](https://jar-download.com/artifacts/com.opencsv/opencsv/4.1/source-code).

    - Save the downloaded JAR files in a folder, for example, "libs" inside your project folder.

    - In Eclipse, right-click on your project in the Project Explorer and click "Properties". In the Properties window, click "Java Build Path" on the left side. In the "Libraries" tab, click the "Add External JARs..." button.

    - Browse to the folder where you saved the JAR files, select all the JAR files you downloaded, and click "Open". Click "Apply and Close" in the Properties window.

4. Run `ExplorerUI` with argument `leetcode_dataset.csv` and get customized experience exploring LeetCode problems!
