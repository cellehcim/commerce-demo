val commercetoolsVersion = "latest.release"

group = "com.cellehcim"
version = "0.0.1-SNAPSHOT"
description = "lulu-demo"

java {
	sourceCompatibility = JavaVersion.VERSION_17
}

plugins {
    `application`
    `java`
    `java-library`
    `maven-publish`
    id("org.springframework.boot") version "3.3.0"
	id("io.spring.dependency-management") version "1.1.5"
	id("org.graalvm.buildtools.native") version "0.10.2"
}

application {
    mainClass = "com.cellehcim.commercedemo.CommerceDemoApplication"
}

graalvmNative {
    binaries {
        named("main") {
            imageName.set("app")
        }
    }
}

graalvmNative {
    binaries.all {
        resources.autodetect()
    }
}

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        url = uri("https://repo.maven.apache.org/maven2/")
    }
}

dependencies {

    implementation("org.springframework.boot:spring-boot-starter")
	implementation("org.springframework.boot:spring-boot-starter-web")
	implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-validation")

	implementation("com.commercetools.sdk:commercetools-http-client:${commercetoolsVersion}")
    implementation("com.commercetools.sdk:commercetools-sdk-java-api:${commercetoolsVersion}")
    implementation("com.commercetools.sdk:commercetools-sdk-java-importapi:${commercetoolsVersion}")
    implementation("com.commercetools.sdk:commercetools-sdk-java-history:${commercetoolsVersion}")

	implementation(platform("io.projectreactor:reactor-bom:2020.0.9"))
    implementation("io.projectreactor:reactor-core")

    implementation("io.github.cdimascio:dotenv-java:3.0.0")

    developmentOnly("org.springframework.boot:spring-boot-devtools")

	testImplementation("org.springframework.boot:spring-boot-starter-test")
	testImplementation("io.projectreactor:reactor-test")
	testImplementation("io.rest-assured:rest-assured:5.4.0")

	testRuntimeOnly("org.junit.platform:junit-platform-launcher")
}

publishing {
    publications.create<MavenPublication>("maven") {
        from(components["java"])
    }
}

tasks.named<Test>("test") {
    useJUnitPlatform() 
}

tasks.withType<JavaCompile>().configureEach {
    options.encoding = "UTF-8"
    options.compilerArgs.add("-parameters")
}

tasks.withType<Javadoc>() {
    options.encoding = "UTF-8"
}
