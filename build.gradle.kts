plugins {
	java
	`java-library`
	id("org.springframework.boot") version "3.3.0-M2"
	id("io.spring.dependency-management") version "1.1.4"
}

group = "com.example"
version = "0.0.1-SNAPSHOT"
var mapstructVersion = "1.6.0.Beta1"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

configurations {
	compileOnly {
		extendsFrom(configurations.annotationProcessor.get())
	}
}

repositories {
	mavenCentral()
	maven { url = uri("https://repo.spring.io/milestone") }
}

dependencies {
	implementation("org.springframework.boot:spring-boot-starter-data-rest")
	implementation("org.springframework.boot:spring-boot-starter-web") {
		exclude("org.springframework.boot", "spring-boot-starter-logging")
	}
	implementation("org.apache.commons:commons-collections4:4.4")
	implementation("org.apache.commons:commons-lang3:3.14.0")
	implementation("org.springframework.boot:spring-boot-starter-data-mongodb")
	implementation("org.springframework.boot:spring-boot-starter-validation")
	implementation("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	implementation("org.mapstruct:mapstruct:${mapstructVersion}")
	annotationProcessor("org.projectlombok:lombok")
	annotationProcessor("org.projectlombok:lombok-mapstruct-binding:0.2.0")
	annotationProcessor("org.mapstruct:mapstruct-processor:${mapstructVersion}")

	compileOnly("org.projectlombok:lombok")



	testImplementation("org.springframework.boot:spring-boot-starter-test")
}

tasks.compileJava {
	options.compilerArgs.plusAssign(listOf("-Amapstruct.defaultComponentModel=spring"))
}

tasks.withType<Test> {
	useJUnitPlatform()
}
