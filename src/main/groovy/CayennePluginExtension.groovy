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
import org.gradle.api.tasks.InputDirectory
import org.gradle.api.tasks.InputFile

public class CayennePluginExtension {

	CayennePlugin plugin

	@InputFile
	File cayenneMap

	@InputFile
	File destDir // = file("${project.projectDir}/src/main/java")

	/*
		Java package name of generated superclasses.
		Ignored unless "makepairs" set to "true". If omitted, each superclass will be assigned
		the same package as subclass. Note that having superclass in a different package
		would only make sense when "usepkgpath" is set to "true". Otherwise classes from
		different packages will end up in the same directory.
	*/
	String superPkg


	String serverSuperPkg

	@InputDirectory
	File templateDir // = file("${project.projectDir}/src/main/resources/cayenne/templates/")

	/*
		Default is "standalone". Other options are "client" or "server" for a Remote Object
		Persistence environment where you have a split client and server
	*/
	String type = 'standalone'

	/*
		Specifies class generator iteration target. There are three possible values: 
		"entity" (default), "datamap", "all". "entity" performs one generator iteration 
		for each selected entity, applying standard entity templates (templates can of 
		course be overridden with user defined ones). "datamap" performs a single iteration, 
		applying standard DataMap templates. "All" is a combination of entity and datamap
	*/
	String mode = 'all'

	/*
		Entities (expressed as a perl5 regex) to exclude from template generation.
		(Default is to include all entities in the DataMap)
	*/
	String excludeEntities

	/*
		Entities (expressed as a perl5 regex) to include in template generation.
		(Default is to include all entities in the DataMap)
	*/
	String includeEntities

	public CayennePluginExtension(CayennePlugin plugin) {
		this.plugin = plugin
	}



}