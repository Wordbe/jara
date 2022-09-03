dependencies {
    implementation("org.springframework.boot:spring-boot-starter-webflux")
    implementation("org.springframework.boot:spring-boot-starter-data-r2dbc")

    implementation("io.projectreactor.kotlin:reactor-kotlin-extensions")
    implementation("org.jetbrains.kotlinx:kotlinx-coroutines-reactor")
    implementation("at.favre.lib:bcrypt:0.9.0")

    kapt("org.springframework.boot:spring-boot-configuration-processor")

    runtimeOnly("io.r2dbc:r2dbc-h2")

    testImplementation("io.projectreactor:reactor-test")
}