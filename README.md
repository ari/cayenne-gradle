# Apache Cayenne gradle plugin

Add this to your configuration file

```
cayenne {
    cayenneMap = file ("${projectDir}/src/main/resources/cayenne/MyApp.map.xml")
    superPkg = "acme.myApp"
    templateDir = file ("${projectDir}/src/main/resources/cayenne/templates/")
    type = 'client'
    mode = 'all'
    destDir = file ("${projectDir}/src/main/java")
}
```