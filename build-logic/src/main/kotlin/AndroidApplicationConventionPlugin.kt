import com.android.build.api.dsl.ApplicationExtension
import com.nexters.ilab.android.ApplicationConfig
import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.configureAndroid
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.AndroidApplication, Plugins.KotlinAndroid)

        extensions.configure<ApplicationExtension> {
            configureAndroid(this)

            defaultConfig {
                targetSdk = ApplicationConfig.TargetSdk
                versionCode = ApplicationConfig.VersionCode
                versionName = ApplicationConfig.VersionName
            }
        }
    },
)
