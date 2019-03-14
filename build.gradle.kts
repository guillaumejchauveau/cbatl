plugins {
  id("com.gradle.build-scan") version "2.1"
  java
  application
}

repositories {
  jcenter()
}

dependencies {
  implementation("org.apache.commons:commons-csv:1.6")
  testImplementation("junit:junit:4.12")
}

application {
  mainClassName = "cbatl.App"
}

buildScan {
  setTermsOfServiceUrl("https://gradle.com/terms-of-service")
  setTermsOfServiceAgree("yes")
}

tasks.clean {
  doFirst {
    delete("livraison/dist", "livraison/doc", "livraison/src")
  }
}

tasks.jar {
  manifest {
    attributes(
      "Main-Class" to application.mainClassName
    )
  }
}

tasks.register<Copy>("livraison") {
  dependsOn(tasks.withType<Javadoc>())
  dependsOn(tasks.withType<Jar>())
  from("src") {
    into("src")
  }
  from("build/docs/javadoc") {
    into("doc")
  }
  from("build/libs") {
    into("dist")
  }
  into("livraison")
}
