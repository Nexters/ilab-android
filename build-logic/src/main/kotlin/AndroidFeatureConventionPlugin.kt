import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.implementation
import com.nexters.ilab.android.libs
import com.nexters.ilab.android.project
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(
            "ilab.android.library",
            "ilab.android.library.compose",
            "ilab.android.hilt",
        )

        dependencies {
            implementation(project(path = ":core:common"))
            implementation(project(path = ":core:designsystem"))
            implementation(project(path = ":core:domain"))
            implementation(project(path = ":core:ui"))
            implementation(project(path = ":feature:navigator"))

            implementation(libs.androidx.hilt.navigation.compose)
            implementation(libs.bundles.androidx.lifecycle)
            implementation(libs.bundles.orbit)
        }
    },
)
