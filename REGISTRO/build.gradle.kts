plugins {
    id("com.android.application") version "8.10.0" apply false
    id("org.jetbrains.kotlin.android") version "1.9.22" apply false
    id("com.google.devtools.ksp") version "1.9.22-1.0.16" apply false
}

repositories {
    google()
    mavenCentral()

}

dependencies {
    classpath("androidx.navigation:navigation-safe-args-gradle-plugin:2.7.7")
}