/**
 * This is a convention that actually will make you able to write tests in `jvmTest` source set,
 * not in `commonTest`. We not always need to have common tests, at least, for example, for domain
 * & presentation logic (mvi) – there's no need as it's not platform specific.
 *
 * So, basically, we will use mockk because it's much more easier to use comparing with `mockative`
 * or other ksp-based mocking libraries.
 *
 * Only known issue for now, it that it cannot mock suspend function that returns value class. It
 * also applies for class Result, so we're not going to use this class anywhere outside SDK (it
 * also is good approach as in business logic we want to mark specific errors to be checked).
 */
plugins {
    id("unit-tests-convention")
}

val mockkVersion = "1.13.5"

kotlin {
    sourceSets {
        val jvmTest by getting {
            dependencies {
                implementation("io.mockk:mockk:$mockkVersion")
            }
        }
    }
}