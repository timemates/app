package app.timemate.client.build.conventions.feature

import gradle.kotlin.dsl.accessors._966ecc2ba0326bde76ac5b033d6ec0f7.kover
import kotlinx.kover.gradle.plugin.dsl.AggregationType
import kotlinx.kover.gradle.plugin.dsl.CoverageUnit
import org.gradle.accessors.dm.LibrariesForLibs

plugins {
    id("app.timemate.client.build.conventions.detekt-convention")
    id("app.timemate.client.build.conventions.multiplatform-convention")
    id("app.timemate.client.build.conventions.tests-convention")
    id("app.timemate.client.build.conventions.kover-convention")
}

val libs = the<LibrariesForLibs>()

dependencies {
    commonMainApi(libs.y9vad9.ktiny.kotlidator)
}

kover {
    reports {
        verify.rule {
            /**
             * We want to enforce domain to be fully tested as logic there
             * is totally deterministic, pure; therefore, should be well-tested
             */
            minBound(
                minValue = 85,
                coverageUnits = CoverageUnit.LINE,
                aggregationForGroup = AggregationType.COVERED_PERCENTAGE,
            )
        }
    }
}
