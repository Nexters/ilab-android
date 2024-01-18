import com.android.build.api.dsl.ApplicationExtension
import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidApplicationComposeConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.AndroidApplication)

        extensions.configure<ApplicationExtension> {
            configureCompose(this)
        }
    },
)
