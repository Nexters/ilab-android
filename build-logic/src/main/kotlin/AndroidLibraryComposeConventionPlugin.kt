import com.android.build.gradle.LibraryExtension
import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.configureCompose
import org.gradle.kotlin.dsl.configure

internal class AndroidLibraryComposeConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.AndroidLibrary)

        extensions.configure<LibraryExtension> {
            configureCompose(this)
        }
    },
)


