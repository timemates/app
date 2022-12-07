open class DeployExtension {
    var ignore = false

    // Required
    var host: String? = null
    var componentName: String? = null
    var group: String? = null
    var artifactId: String? = null
    var version: String? = null
    var name: String? = null
    var description: String? = null
    var deployPath: String? = null

    // Optional
    var user: String? = null
    var password: String? = null
}