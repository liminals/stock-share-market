# Liminal 
## Stock-Share-Market simulation game
A real time multiplayer game

### Requirements
Download them, and Please set the PATH variables accordingly. Configure Maven and Tomcat in Eclipse(Google it!)
- [JAVA 8](http://www.oracle.com/technetwork/java/javase/downloads/jdk8-downloads-2133151.html)
- [Eclipse EE ](https://www.eclipse.org/downloads/packages/eclipse-ide-java-ee-developers/oxygen3a)
- [apache tomcat 8.5](http://tomcat.apache.org/download-80.cgi)
- [Maven](https://maven.apache.org/download.cgi)
- [Git](https://git-scm.com/downloads)
- Bootstar 4 (in future)


### How to import 
- open eclipse
- click File > Import
- choose Existing Maven Projects
- then Right click on project > Maven > Download Sources/ Download JavaDoc
- then Right click on project > Maven > Update Project

### How to run,
- Right click on project > Run as > Maven build...
- In the Edit Configuration window set Goals as 'clean install'
- In the Edit Configuration window select Skip Tests
- After 'Build Success', Right click on project > Run as > Run on server
- Choose Tomcat8 on localhost, Then Finish
