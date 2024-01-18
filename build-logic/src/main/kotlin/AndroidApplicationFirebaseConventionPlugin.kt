import com.nexters.ilab.android.Plugins
import com.nexters.ilab.android.applyPlugins
import com.nexters.ilab.android.libs
import org.gradle.kotlin.dsl.dependencies

internal class AndroidApplicationFirebaseConventionPlugin : BuildLogicConventionPlugin(
    {
        applyPlugins(Plugins.GoogleServices, Plugins.FirebaseCrashlytics)

        dependencies {
            val bom = libs.findLibrary("firebase-bom").get()
            add("implementation", platform(bom))
            "implementation"(libs.findLibrary("firebase.analytics").get())
            "implementation"(libs.findLibrary("firebase.crashlytics").get())
        }
    },
)
