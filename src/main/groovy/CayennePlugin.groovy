/*
 * Copyright 2016 Ari Maniatis
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */


import org.gradle.api.GradleException
import org.gradle.api.Plugin
import org.gradle.api.Project
import org.gradle.api.Task

public class CayennePlugin implements Plugin<Project> {
	Project project

    def void apply(Project project) {
        this.project = project

		project.configurations {
			cayenneTools
		}
		project.dependencies {
			cayenneTools 'org.apache.cayenne:cayenne-tools:4.0.M3.2'
		}

        CayennePluginExtension extension = project.getExtensions().create("cayenne", CayennePluginExtension.class, this)

        Task cgenTask = project.task('cayenne') {
        	
            doFirst {
				ant.taskdef(resource: 'org/apache/cayenne/tools/antlib.xml', classpath: project.configurations.cayenneTools.asPath) \

				if (!project.cayenne.cayenneMap.exists()) {
					throw new GradleException('You must set cayenneMap to a Cayenne map file in your source repository.')
				}

				if (! new File(project.cayenne.templateDir, 'subclass.vm').exists() ) {
					throw new GradleException('You must set cayenneMap to a Cayenne map file in your source repository.');
				}

				switch (project.cayenne.type) {
					case 'standalone':
						ant.cgen( map: project.cayenne.cayenneMap, 
							destDir: project.cayenne.destDir, 
							superPkg: project.cayenne.superPkg,
							template: new File(project.cayenne.templateDir.path ,'subclass.vm'),
							superTemplate: new File(project.cayenne.templateDir.path , 'superclass.vm'),
							makepairs: true,
							client: 'false',
							mode: 'all')
						break

					case 'client':
						ant.cgen( map: project.cayenne.cayenneMap, 
							destDir: project.cayenne.destDir, 
							superPkg: project.cayenne.superPkg,
							template: new File(project.cayenne.templateDir.path ,'client-subclass.vm'),
							superTemplate: new File(project.cayenne.templateDir.path , 'client-superclass.vm'),
							makepairs: true,
							client: 'true',
							mode: 'all')
						break

					case 'server':
						ant.cgen( map: project.cayenne.cayenneMap, 
							destDir: project.cayenne.destDir, 
							superPkg: project.cayenne.superPkg,
							template: new File(project.cayenne.templateDir.path ,'client-subclass-serverside.vm'),
							superTemplate: new File(project.cayenne.templateDir.path , 'client-superclass-serverside.vm'),
							makepairs: true,
							client: 'true',
							mode: 'all')
						ant.cgen( map: project.cayenne.cayenneMap, 
							destDir: project.cayenne.destDir, 
							superPkg: project.cayenne.serverSuperPkg,
							template: new File(project.cayenne.templateDir.path ,'subclass.vm'),
							superTemplate: new File(project.cayenne.templateDir.path , 'superclass.vm'),
							makepairs: true,
							client: 'false',
							mode: 'all')
						break

					default:
						throw new GradleException('Type must be "standalone", "client" or "server".');

				}
            }
        }
        cgenTask.description = "Cayenne cgen execution"
        project.compileJava.dependsOn('cayenne')
	}

}