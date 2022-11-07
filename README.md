# BarcodeFramework
바코드 네트워크의 framework 입니다.

강력한 DI, IoC 툴을 제공하며, BarcodeComponent 를 통해 객체의 생성을 관리할 수 있습니다.
다양한 플랫폼을 위하여 구현될 예정이며 현재는 Ktor, BukkitAPI 플랫폼이 구현되어 있습니다. 앞으로 Velocity 및 JDA 를 위한 플랫폼도 구현될 예정입니다.
BarcodeComponent를 통한 객체 생성은, Koin 어노테이션이 있을 경우 Koin 의 Bean 과 자동으로 연동됩니다.
또한, BarcodeComponent 를 통한 객체 생성 시, 생성자 주입을 지원합니다. 함수 내에서의 의존성 주입을 최대한 삼가하여 주세요.
