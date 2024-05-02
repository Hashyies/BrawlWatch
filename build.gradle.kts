plugins {
    application
}

repositories {
    mavenCentral()
    
    // Repositories for the Brawl Stars API wrapper
    maven {
        url = uri("https://packagecloud.io/mlieshoff/supercell-api-wrapper-essentials/maven2")
    }
        
    maven {
        name = "packagecloud-brawljars"
        url = uri("https://packagecloud.io/mlieshoff/brawljars/maven2")
    }
}

dependencies {
    // Discord Bot API (includes json libraries)
    implementation("net.dv8tion:JDA:5.0.0-beta.23")

    // Brawl Stars API
    implementation("brawljars:brawljars:4.0.1")
    implementation("supercell-api-wrapper-essentials:supercell-api-wrapper-essentials:1.0.1")

    // Misc
    implementation("io.github.cdimascio:dotenv-java:3.0.0")
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(22)
    }
}

application {
    mainClass = "me.hashy.BrawlWatch.Main"
}