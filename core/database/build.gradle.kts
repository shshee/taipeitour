plugins {
    alias(libs.plugins.android.library)
    alias(libs.plugins.org.jetbrains.kotlin.android)
    alias(libs.plugins.android.ksp)
    //alias(libs.plugins.protobuf)
}

android {
    namespace = "com.tangerine.core.database"
    compileSdk = libs.versions.sdk.target.get().toInt()

    defaultConfig {
        minSdk = libs.versions.sdk.min.get().toInt()
    }

    java {
        sourceCompatibility = JavaVersion.VERSION_1_8
        targetCompatibility = JavaVersion.VERSION_1_8
    }

    kotlinOptions {
        jvmTarget = "1.8"
    }
}

dependencies {
    implementation(project(mapOf("path" to ":core:model")))
    implementation(project(mapOf("path" to ":core:utils")))

    implementation(libs.bundles.koin)
    implementation(libs.gson.core)

    implementation(libs.bundles.room)
    ksp(libs.room.complier)

    //implementation(libs.bundles.datastore.proto)
    implementation(libs.datastore.preferences)

    testImplementation(libs.bundles.test.impl)
    androidTestImplementation(libs.bundles.android.test.impl)
}

//protobuf {
//    //Having problem while importing this s...
//    protoc {
//        artifact = libs.protobuf.protoc.get().toString()
//    }
//
//    // Generates the java Protobuf-lite code for the Protobufs in this project. See
//    // https://github.com/google/protobuf-gradle-plugin#customizing-protobuf-compilation
//    // for more information.
//    generateProtoTasks {
//        all().forEach { task ->
//            task.builtins {
//                create("java") {
//                    option("lite")
//                }
//            }
//        }
//    }
//}