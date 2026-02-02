plugins {
    id("java")
    id("io.freefair.lombok") version "9.1.0"

}

group = "com.uce"
version = "unspecified"

repositories {
    mavenCentral()
}

dependencies {
    testImplementation(platform("org.junit:junit-bom:5.10.0"))
    testImplementation("org.junit.jupiter:junit-jupiter")
    testRuntimeOnly("org.junit.platform:junit-platform-launcher")
    implementation("org.postgresql:postgresql:42.7.8")
    implementation ("org.hibernate.orm:hibernate-core:7.1.11.Final")
    implementation("io.helidon.webserver:helidon-webserver:4.3.3")
    implementation("jakarta.persistence:jakarta.persistence-api:3.2.0")
// Source: https://mvnrepository.com/artifact/io.helidon.media.jsonb/helidon-media-jsonb-common
    implementation("io.helidon.http.media:helidon-http-media-jsonb:4.3.3")

}
sourceSets{
    main{
        output.setResourcesDir(file("${buildDir}/classes/java/main"))
    }
}

tasks.test {
    useJUnitPlatform()
}