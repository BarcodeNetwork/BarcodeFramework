object Modules {
    object COMMON : ModuleNotation {
        override val path = ":modules:common"
    }
    object DATABASE : ModuleNotation {
        override val path = ":modules:database"
    }
    object SHEETS : ModuleNotation {
        override val path = ":modules:google-sheets"
    }
    object KOIN : ModuleNotation {
        override val path = ":modules:koin"
    }
    object Bukkit {
        object PLUGIN : ModuleNotation {
            override val path = ":modules:platform-bukkit-plugin"
        }
        object COMMON : ModuleNotation {
            override val path = ":modules:platform-bukkit-common"
        }
        object V1_19_R1 : ModuleNotation {
            override val path = ":modules:platform-bukkit-v1_19_R1"
        }
    }
    object KTOR : ModuleNotation {
        override val path = ":modules:platform-ktor"
    }
    object VELOCITY : ModuleNotation {
        override val path: String = ":modules:platform-velocity"
    }
    object NETTY : ModuleNotation {
        override val path: String = ":modules:netty"
    }
    object PROXY_API : ModuleNotation {
        override val path: String = ":modules:proxy-api"
    }
}