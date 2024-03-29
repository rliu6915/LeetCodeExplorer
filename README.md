# LeetCodeExplorer ![Java](https://img.shields.io/badge/java-%23ED8B00.svg?style=flat-square&logo=openjdk&logoColor=white)

CIT 5940 Data Structures &amp; Software Design - Final Project. We currently use this repo instead of LeetCodeExplorerNew

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

## [Slides](https://docs.google.com/presentation/d/1Is3IqZ233_8907uQX_GoTMk66cFU1yWh5VWjWn6PmjA/edit#slide=id.g24142c0a82a_0_5)
