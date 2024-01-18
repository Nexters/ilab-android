import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidRetrofitConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.KotlinxSerialization)

        dependencies {
            add("implementation", libs.findLibrary("retrofit").get())
            add("implementation", libs.findLibrary("retrofit.kotlinx.serialization.converter").get())
            add("implementation", libs.findLibrary("okhttp.logging.interceptor").get())
            add("implementation", libs.findLibrary("kotlinx.serialization.json").get())
        }
    },
)
