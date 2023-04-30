plugins {
    id("java")
}

group = "net.contexx.projectthoth.renderer.xml-fo.serializer"
version = "1.0-SNAPSHOT"

repositories {
    mavenLocal()
    mavenCentral()
    maven {
        name = "Gitea"
        url = uri("https://gitea.cloud.contexx.one/api/packages/contexx/maven")
        credentials.username = project.properties["gitea_user"].toString()
        credentials.password = project.properties["gitea_password"].toString()
    }
}

dependencies {
    implementation(project(":core"))
    implementation(project(":renderer:xml-fo:core"))
    implementation(project(":serializer:json"))

    implementation("com.fasterxml.jackson.core:jackson-databind:2.14.2")


    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
}

tasks.test {
    useJUnitPlatform()
}