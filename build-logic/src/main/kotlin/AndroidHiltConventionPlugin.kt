import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.implementation
import com.nexters.ilab.android.ksp
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidHiltConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.hilt, Plugins.Ksp)

        dependencies {
            implementation(libs.hilt.android)
            ksp(libs.hilt.android.compiler)
        }
    },
)
