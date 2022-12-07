open class DeployExtension {
    internal val targets: MutableMap<String, TargetConfiguration> = mutableMapOf()

    fun target(name: String, block: TargetConfiguration.() -> Unit) {
        val configuration = TargetConfiguration().apply(block)
        targets[name] = configuration
    }
}

/**
 * Default configuration that used as fallback when some value wasn't specified.
 * Use it to avoid code-repeating.
 */
fun DeployExtension.default(block: TargetConfiguration.() -> Unit) = target("default", block)
