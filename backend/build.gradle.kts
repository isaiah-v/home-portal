plugins {
    kotlin("jvm") version "1.9.22"
    kotlin("plugin.spring") version "1.9.22"
    id("org.springframework.boot") version "3.2.1"
    id("io.spring.dependency-management") version "1.1.0"
    application
}

group = "org.ivcode"
version = System.getenv("npm_package_version") ?: "dev-build"

repositories {
    mavenCentral()
}

dependencies {
    implementation(kotlin("stdlib"))
    implementation(kotlin("reflect"))

    implementation("org.springframework.boot:spring-boot-starter-web")
    implementation("com.fasterxml.jackson.module:jackson-module-kotlin")
    implementation("org.springframework.boot:spring-boot-starter-oauth2-resource-server")
    implementation("org.springdoc:springdoc-openapi-starter-webmvc-ui:2.3.0")

    implementation("com.squareup.retrofit2:retrofit:2.9.0")
    implementation("com.squareup.retrofit2:converter-jackson:2.9.0")


    implementation("org.apache.commons:commons-lang3")
    implementation("org.apache.httpcomponents:httpclient:4.5.14")


    implementation("org.mybatis:mybatis:3.5.14")
    implementation("org.mybatis.spring.boot:mybatis-spring-boot-starter:3.0.3")

    implementation("com.mysql:mysql-connector-j:8.2.0")

    testImplementation(kotlin("test"))
    testImplementation("org.mockito:mockito-core:5.8.0")

}

tasks.getByName<Jar>("jar") {
    enabled = false
}

fun String.runCommand(workingDirectory: File = layout.projectDirectory.asFile): Int {
    println("> $this")
    return project.exec {
        workingDir = workingDirectory
        commandLine = this@runCommand.split("\\s".toRegex())
        //standardOutput = System.out
        //errorOutput = System.err
    }.exitValue
}

/**
 * If the "withFrontend" parameter is defined, the frontend is built into the jar
 *
 * Note: npm isn't in the classpath when running Gradle in IntelliJ. Running with this property in IntelliJ results in errors
 */
if(project.hasProperty("withFrontend")) {
    val frontendDirectory = File(layout.projectDirectory.asFile.parentFile, "frontend")
    val resourcesDirectory = layout.buildDirectory.dir("resources").get().asFile
    val publicDirectory = File(resourcesDirectory, "main/public")

    tasks.named("clean").configure{ dependsOn("clean-frontend") }
    tasks.named("test").configure{ dependsOn("copy-frontend") }
    tasks.named("test").configure{ dependsOn("write-version") }
    tasks.named("bootJar").configure{ dependsOn("copy-frontend") }
    tasks.named("bootJar").configure{ dependsOn("write-version") }
    tasks.named("resolveMainClassName").configure{ dependsOn("copy-frontend") }
    tasks.named("resolveMainClassName").configure{ dependsOn("write-version") }

    tasks.register("clean-frontend") {
        doLast {
            "npm run clean".runCommand(frontendDirectory)
        }
    }

    tasks.register("build-frontend") {
        doLast {
            "npm install".runCommand(frontendDirectory)
            "npm run build".runCommand(frontendDirectory)
        }
    }

    tasks.register<Copy>("copy-frontend") {
        dependsOn(":processResources",":build-frontend")
        from(file(File(frontendDirectory,"build").absolutePath))
        into(publicDirectory.absolutePath)
    }

    tasks.register("write-version") {
        dependsOn(":processResources")
        doLast {
            File(resourcesDirectory, "main/version.txt").writeText(text = version as String)
        }
    }
}

tasks.test {
    useJUnitPlatform()
}

kotlin {
    explicitApi()
    jvmToolchain(17)
}

application {
    mainClass.set("org.ivcode.homeportal.MainKt")
}