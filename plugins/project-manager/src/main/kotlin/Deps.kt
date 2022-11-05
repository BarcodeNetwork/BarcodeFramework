import com.vjh0107.barcode.framework.projectmanager.ProjectManagerPlugin
import org.gradle.kotlin.dsl.provideDelegate

object Deps {
    private val project = ProjectManagerPlugin.project

    val kotlinVersion: String by project
    object Minecraft {
        private const val version = "1.19.2-R0.1-SNAPSHOT"

        const val PAPER_API = "io.papermc.paper:paper-api:$version"
        const val SPIGOT = "org.spigotmc:spigot:$version"
        const val SPIGOT_API = "org.spigotmc:spigot-api:$version"
        const val SPIGOT_REMAPPED = "org.spigotmc:spigot:$version:remapped-mojang"

        const val AUTH_LIB = "com.mojang:authlib:1.5.21"
        const val DATA_FIXER = "com.mojang:datafixerupper:4.0.26"
        const val BRIGADIER = "com.mojang:brigadier:1.0.18"

        const val MOCK_BUKKIT = "com.github.seeseemelk:MockBukkit-v1.18:2.85.2"

        object KyoriAdventure {
            private val kyoriAdventureVersion: String by project
            val API = "net.kyori:adventure-api:$kyoriAdventureVersion"
            private val kyoriAdventureBukkitVersion: String by project
            val BUKKIT = "net.kyori:adventure-platform-bukkit:$kyoriAdventureBukkitVersion"
        }

        object Plugin {
            const val COMMAND_API = "dev.jorel:commandapi-shade:8.5.1"
            const val VAULT = "com.github.MilkBowl:VaultAPI:1.7"
            const val PAPI = "me.clip:placeholderapi:2.11.1"
        }
    }

    object Library {
        val KOTLIN_REFLECT = "org.jetbrains.kotlin:kotlin-reflect:$kotlinVersion"
        val KOTLIN_TEST = "org.jetbrains.kotlin:kotlin-test-junit:$kotlinVersion"

        const val MYSQL_CONNECTOR = "mysql:mysql-connector-java:8.0.28"
        const val HIKARICP = "com.zaxxer:HikariCP:5.0.1"
        const val SQLITE = "org.xerial:sqlite-jdbc:3.36.0.3"
        const val NETTY = "io.netty:netty-all:4.1.78.Final"
        val mockKVersion: String by project
        val MOCKK = "io.mockk:mockk:$mockKVersion"

        object Logger {
            const val LOGBACK_CLASSIC ="ch.qos.logback:logback-classic:1.4.0"
            const val SLF4J_JDK14 = "org.slf4j:slf4j-jdk14:2.0.1"
        }
    }

    object KSP {
        private val kspVersion: String by project
        val API = "com.google.devtools.ksp:symbol-processing-api:$kspVersion"
    }

    object Ktor {
        private val ktorVersion: String by project
        private val SERIALIZATION_JSON = "io.ktor:ktor-serialization-kotlinx-json:$ktorVersion"

        object CLIENT : DependencySet<String> {
            private val CORE = "io.ktor:ktor-client-core:$ktorVersion"
            private val COROUTINES_IO = "io.ktor:ktor-client-cio:$ktorVersion"
            private val CONTENT_NEGOTIATION = "io.ktor:ktor-client-content-negotiation:$ktorVersion"

            override fun getDependencies(): Collection<String> {
                return setOf(CORE, COROUTINES_IO, CONTENT_NEGOTIATION, SERIALIZATION_JSON)
            }
        }

        object SERVER : DependencySet<String> {
            private val CORE = "io.ktor:ktor-server-core-jvm:$ktorVersion"
            private val CONTENT_NEGOTIATION = "io.ktor:ktor-server-content-negotiation-jvm:$ktorVersion"
            // private const val NETTY = "io.ktor:ktor-server-netty-jvm:$VERSION"
            private val COROUTINES_IO = "io.ktor:ktor-server-cio:$ktorVersion"
            private val RESOURCES = "io.ktor:ktor-server-resources:$ktorVersion"
            private val STATUS_PAGES = "io.ktor:ktor-server-status-pages:$ktorVersion"

            override fun getDependencies(): Collection<String> {
                return setOf(CORE, CONTENT_NEGOTIATION, COROUTINES_IO, RESOURCES, STATUS_PAGES, SERIALIZATION_JSON)
            }
        }
        val SERVER_TEST = "io.ktor:ktor-server-tests-jvm:$ktorVersion"
    }

    object KotlinX {
        object Coroutines {
            private const val VERSION = "1.6.1"

            const val CORE = "org.jetbrains.kotlinx:kotlinx-coroutines-core:$VERSION"
            const val TEST = "org.jetbrains.kotlinx:kotlinx-coroutines-test:$VERSION"
        }

        object Serialization {
            private const val VERSION = "1.4.0"
            const val JSON = "org.jetbrains.kotlinx:kotlinx-serialization-json:$VERSION"
        }
    }

    object KOTEST : DependencySet<String> {
        private const val VERSION = "5.3.1"
        private const val KOTLIN_TEST_RUNNER = "io.kotest:kotest-runner-junit5:$VERSION"
        private const val KOTLIN_TEST_ASSERTIONS_CORE = "io.kotest:kotest-assertions-core:$VERSION"

        override fun getDependencies(): Collection<String> {
            return setOf(KOTLIN_TEST_RUNNER, KOTLIN_TEST_ASSERTIONS_CORE)
        }
    }

    object EXPOSED : DependencySet<String> {
        private val exposedVersion: String by project
        private val CORE = "org.jetbrains.exposed:exposed-core:$exposedVersion"
        private val DAO = "org.jetbrains.exposed:exposed-dao:$exposedVersion"
        private val JDBC = "org.jetbrains.exposed:exposed-jdbc:$exposedVersion"
        private val JAVA_TIME = "org.jetbrains.exposed:exposed-java-time:$exposedVersion"

        override fun getDependencies(): Collection<String> {
            return setOf(CORE, DAO, JDBC, JAVA_TIME)
        }
    }

    object GOOGLE_SHEETS : DependencySet<String> {
        private const val API_CLIENT = "com.google.api-client:google-api-client:1.34.1"
        private const val OAUTH_CLIENT = "com.google.oauth-client:google-oauth-client-jetty:1.34.1"
        private const val GOOGLE_SHEETS = "com.google.apis:google-api-services-sheets:v4-rev20210629-1.32.1"
        private const val OAUTH2_HTTP = "com.google.auth:google-auth-library-oauth2-http:1.8.1"

        override fun getDependencies(): Collection<String> {
            return setOf(API_CLIENT, OAUTH_CLIENT, GOOGLE_SHEETS, OAUTH2_HTTP)
        }
    }

    object Koin {
        private val koinVersion: String by project
        private val koinAnnotationVersion: String by project
        val CORE = "io.insert-koin:koin-core:$koinVersion"
        val KTOR = "io.insert-koin:koin-ktor:$koinVersion"
        val ANNOTATIONS = "io.insert-koin:koin-annotations:$koinAnnotationVersion"
        val KSP_COMPILER = "io.insert-koin:koin-ksp-compiler:$koinAnnotationVersion"

        val TEST = "io.insert-koin:koin-test:$koinVersion"
    }
}