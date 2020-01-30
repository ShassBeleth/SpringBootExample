import org.jetbrains.kotlin.gradle.tasks.KotlinCompile

plugins {
	id("org.springframework.boot") version "2.2.4.RELEASE"
	id("io.spring.dependency-management") version "1.0.8.RELEASE"
	kotlin("jvm") version "1.3.61"
	kotlin("plugin.spring") version "1.3.61"

	// warのプラグイン追加
	// war

}

group = "com.example.spring.boot"
version = "0.0.1-SNAPSHOT"
java.sourceCompatibility = JavaVersion.VERSION_11

repositories {
	mavenCentral()
	jcenter()
}

val developmentOnly: Configuration by configurations.creating
configurations {
	runtimeClasspath {
		extendsFrom(developmentOnly)
	}
}

dependencies {

	// dev-tools
	developmentOnly("org.springframework.boot:spring-boot-devtools")

	implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.jetbrains.kotlin:kotlin-reflect")
	implementation("org.jetbrains.kotlin:kotlin-stdlib-jdk8")
	testImplementation("org.springframework.boot:spring-boot-starter-test") {
		exclude(group = "org.junit.vintage", module = "junit-vintage-engine")
	}

	// web
	implementation("org.springframework.boot:spring-boot-starter-web")

	// warファイルを作成するなら以下を追加
	// providedRuntime("org.springframework.boot:spring-boot-starter-tomcat")

	// spring fox
	// spring foxが依存しているライブラリの問題で、SwaggerUIのページを表示した時に警告が表示される
	// 依存ライブラリを取り除き、別バージョンのライブラリを入れることで警告を回避
	implementation("io.springfox:springfox-swagger2:2.9.2") {
		exclude(module = "swagger-annotations")
		exclude(module = "swagger-models")
	}
	implementation("io.swagger:swagger-annotations:1.5.21")
	implementation("io.swagger:swagger-models:1.5.21")
	// This is still in incubation.
	// implementation( "io.springfox:springfox-data-rest:2.9.2")
	implementation( "io.springfox:springfox-swagger-ui:2.9.2" )


}

tasks.withType<Test> {
	useJUnitPlatform()
}

tasks.withType<KotlinCompile> {
	kotlinOptions {
		freeCompilerArgs = listOf("-Xjsr305=strict")
		jvmTarget = "1.8"
	}
}
