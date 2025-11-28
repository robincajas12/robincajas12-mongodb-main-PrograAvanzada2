plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"
}

group = "com.progavanzada"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.hibernate.orm:hibernate-core:7.1.10.Final")
    implementation("org.postgresql:postgresql:42.7.8")

}
sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}
tasks.test {
    useJUnitPlatform()
}