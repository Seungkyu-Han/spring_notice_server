plugins {
    kotlin("jvm") version "2.0.0"
    kotlin("plugin.spring") version "1.9.25"
    id("org.springframework.boot") version "3.3.4"
    id("io.spring.dependency-management") version "1.1.6"
}

group = "seungkyu"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux:3.3.5")
    //AWS SNS
    implementation(platform("io.awspring.cloud:spring-cloud-aws-dependencies:3.0.1"))
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sqs")
    implementation("io.awspring.cloud:spring-cloud-aws-starter-sns")


    //LOMBOK
    compileOnly("org.projectlombok:lombok")
    annotationProcessor("org.projectlombok:lombok")



}

tasks.test {
    useJUnitPlatform()
}
kotlin {
    jvmToolchain(17)
}