import com.android.build.gradle.LibraryExtension
import com.nexters.ilab.android.ApplicationConfig
import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.configureAndroid
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryConventionPlugin : BuildLogicConventionPlugin({
    applyPlugins(Plugins.AndroidLibrary, Plugins.KotlinAndroid)

    extensions.configure<LibraryExtension> {
        configureAndroid(this)

        defaultConfig.apply {
            targetSdk = ApplicationConfig.TargetSdk
        }
    }
})
