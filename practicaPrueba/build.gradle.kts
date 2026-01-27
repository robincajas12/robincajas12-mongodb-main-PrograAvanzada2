plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"

}

group = "com.project007"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation ("org.hibernate.orm:hibernate-core:7.1.11.Final")
    //    implementation("org.hibernate.orm:hibernate-core:7.2.0.CR3")

    implementation("org.postgresql:postgresql:42.7.8")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")

    implementation("io.smallrye:jandex:3.2.3")
    // CDI (Weld SE)
    implementation("org.jboss.weld.se:weld-se-core:6.0.3.Final")

    // Opcional: Jandex (útil si haces scanning)
    implementation("io.smallrye:jandex:3.2.3")
}
sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}
tasks.test {
    useJUnitPlatform()
}