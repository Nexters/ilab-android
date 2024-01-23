import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.implementation
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidRetrofitConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.KotlinxSerialization)

        dependencies {
            implementation(libs.retrofit)
            implementation(libs.retrofit.kotlinx.serialization.converter)
            implementation(libs.okhttp.logging.interceptor)
            implementation(libs.kotlinx.serialization.json)
        }
    },
)
