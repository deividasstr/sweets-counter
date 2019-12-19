/**
 * Precompiled [base.gradle.kts][Base_gradle] script plugin.
 *
 * @see Base_gradle
 */
class BasePlugin : org.gradle.api.Plugin<org.gradle.api.Project> {
    override fun apply(target: org.gradle.api.Project) {
        try {
            Class
                .forName("Base_gradle")
                .getDeclaredConstructor(org.gradle.api.Project::class.java)
                .newInstance(target)
        } catch (e: java.lang.reflect.InvocationTargetException) {
            throw e.targetException
        }
    }
}
