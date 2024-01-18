import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.hilt, Plugins.Ksp)

        dependencies {
            add("implementation", libs.findLibrary("hilt-android").get())
            add("ksp", libs.findLibrary("hilt-android-compiler").get())
        }
    },
)
