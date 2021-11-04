import org.jetbrains.kotlin.gradle.tasks.KotlinCompile
import org.springframework.boot.gradle.tasks.run.BootRun

plugins {
	id("org.springframework.boot") version "2.5.2"
	id("io.spring.dependency-management") version "1.0.11.RELEASE"
	war
	kotlin("jvm") version "1.5.20"
	kotlin("plugin.spring") version "1.5.20"
}

group = "turku.forge"
version = "0.1.1"
java.sourceCompatibility = JavaVersion.VERSION_17

repositories {
	mavenCentral()
}

dependencies {
	implementation("org.springframework.hateoas:spring-hateoas:1.3.4")
	implementation("org.springframework.boot:spring-boot-starter-web:2.5.6")
	implementation("org.springframework.boot:spring-boot-starter-websocket:2.5.6")
	implementation("com.fasterxml.jackson.module:jackson-module-kotlin:2.13.0")
	implementation("org.jetbrains.kotlin:kotlin-reflect:1.5.20")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8:1.5.20")
	developmentOnly("org.springframework.boot:spring-boot-devtools:2.5.6")
	providedRuntime("org.springframework.boot:spring-boot-starter-tomcat:2.5.6")
	testImplementation("org.springframework.boot:spring-boot-starter-test:2.5.6")
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "16"
	}
}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType(BootRun::class) {
	jvmArgs = listOf("-Xdebug", "-Xrunjdwp:transport=dt_socket,address=*:5005,server=y,suspend=n")
}
