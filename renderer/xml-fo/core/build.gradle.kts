plugins {
    id("java")
}

group = "net.contexx.projectthoth.renderer.xml-fo"
version = "1.0-SNAPSHOT"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.9.1"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    implementation(project(":core"))

    implementation("org.thymeleaf:thymeleaf:3.1.1.RELEASE")
    implementation("org.apache.xmlgraphics:fop:2.8")
}

tasks.test {
    useJUnitPlatform()
}