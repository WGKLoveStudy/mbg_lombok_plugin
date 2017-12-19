# mbg_lombok_plugin
add lombok to mybatis generator

# Add lombok support to mbg by implements PluginAdapter

# Quick Start
* add the jar（get from assets folder or build from git） to your classpath 
* add depency in mybatis-generator-maven-plugin as follows:
```xml
<plugin>
	<groupId>org.mybatis.generator</groupId>
	<artifactId>mybatis-generator-maven-plugin</artifactId>
	<version>1.3.5</version>
	<configuration>
		<configurationFile>src/main/resources/mbg/genarateMybatis.xml</configurationFile>
		<verbose>true</verbose>
		<overwrite>false</overwrite>
	</configuration>
	<dependencies>
		<dependency>
			<groupId>mysql</groupId>
			<artifactId>mysql-connector-java</artifactId>
			<version>5.1.38</version>
	  </dependency>
		<dependency>
			<groupId>mbg.plugin</groupId>
			<artifactId>mbg.lombok</artifactId>
			<version>1.0-SNAPSHOT</version>
			<scope>system</scope>
			<systemPath>your mbg.lombok.jar absolute path</systemPath>
		</dependency>
	</dependencies>
</plugin>
```
* add <plugin> element to mbg-config.xml type="org.mybatis.generator.plugins.lombok.LombokPlugin"
1. if add plugin with empty body ,the plugin default add Builder,Data,NoArgsConstructor,AllArgsConstructor to java model
2. you can add property name: customLombok ,value: lombok annotation spilit by "," ;to custom the lombok annotation you need ,eg:<property name="customLombok" value="@Builder,@Data"/
>pay attention to the element order ,the <plugin> element should before other element except propeties
