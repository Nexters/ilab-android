import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(
            "ilab.android.library",
            "ilab.android.library.compose",
            "ilab.android.hilt",
        )

        dependencies {
            add("implementation", project(":core:common"))
            add("implementation", project(":core:designsystem"))
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:ui"))
            add("implementation", project(":feature:navigator"))

            add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())

            add("implementation", libs.findBundle("androidx.lifecycle").get())
            add("implementation", libs.findBundle("orbit").get())
        }
    },
)
