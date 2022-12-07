open class TargetConfiguration {
    var mainClass: String? = null

    /**
     * Jar-file destination on remote server (directory).
     * Example: `/opt/prod/foo`
     */
    var destination: String? = null
    var archiveName: String? = null

    var host: String? = null
    var user: String? = null
    var password: String? = null
    var knownHostsFile: String? = null
    var implementationTitle: String? = mainClass
    var serviceName: String? = null
}
