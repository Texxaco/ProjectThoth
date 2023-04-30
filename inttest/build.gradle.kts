plugins {
    id("java")
}

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
    testImplementation(project(":core"))
    testImplementation(project(":serializer:json"))
    testImplementation(project(":ruleengines:homegrow:core"))
    testImplementation(project(":ruleengines:homegrow:serializer:json"))
    testImplementation(project(":renderer:xml-fo:core"))
    testImplementation(project(":renderer:xml-fo:serializer:json"))
    testImplementation("org.junit.jupiter:junit-jupiter-api:5.8.1")
    testRuntimeOnly("org.junit.jupiter:junit-jupiter-engine:5.8.1")

    testImplementation("net.contexx.qft:qft-core:1.2.8")
    testImplementation("net.contexx.qft:qft-junit:1.2.8")
}

tasks.getByName<Test>("test") {
    useJUnitPlatform()
}