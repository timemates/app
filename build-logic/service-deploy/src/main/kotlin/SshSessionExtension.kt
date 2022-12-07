import org.gradle.api.Project
import org.gradle.kotlin.dsl.delegateClosureOf
import org.gradle.kotlin.dsl.the
import org.hidetake.groovy.ssh.core.Remote
import org.hidetake.groovy.ssh.core.RunHandler
import org.hidetake.groovy.ssh.core.Service
import org.hidetake.groovy.ssh.session.SessionHandler


open class SshSessionExtension(
    private val project: Project,
    private val remote: Remote
) {
    operator fun invoke(handler: SessionHandler.() -> Unit) = project.the<Service>().apply {
        run(delegateClosureOf<RunHandler> { session(remote, delegateClosureOf(handler)) })
    }
}
