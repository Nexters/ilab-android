import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidFeatureConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(
            "ilab.android.library",
            "ilab.android.hilt",
        )

        dependencies {
            add("implementation", project(":core:designsystem"))
            add("implementation", project(":core:domain"))
            add("implementation", project(":core:ui"))

            add("implementation", libs.findLibrary("androidx.lifecycle.runtime.compose").get())
            add("implementation", libs.findLibrary("androidx.lifecycle.viewmodel.compose").get())
            add("implementation", libs.findLibrary("androidx.hilt.navigation.compose").get())

            add("implementation", libs.findLibrary("orbit.viewmodel").get())
            add("implementation", libs.findLibrary("orbit.compose").get())
        }
    },
)
