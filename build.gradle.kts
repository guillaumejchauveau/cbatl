plugins {
  id("com.gradle.build-scan") version "2.1"
  java
  application
}

repositories {
  jcenter()
}

dependencies {
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
