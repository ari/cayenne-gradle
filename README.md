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

Is that it? How do I call this?

Well, you don't need to. This plugin injects itself automatically into the build process and runs before compile to ensure the Cayenne classes are all kept up to date.