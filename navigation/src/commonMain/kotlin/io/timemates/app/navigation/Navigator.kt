package io.timemates.app.navigation

import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ProvidableCompositionLocal
import androidx.compose.runtime.State
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.Modifier
import com.arkivanov.decompose.ComponentContext
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.Children
import com.arkivanov.decompose.extensions.compose.jetbrains.stack.animation.StackAnimation
import com.arkivanov.decompose.extensions.compose.jetbrains.subscribeAsState
import com.arkivanov.decompose.router.stack.ChildStack
import com.arkivanov.decompose.router.stack.StackNavigationSource
import com.arkivanov.decompose.router.stack.childStack
import kotlinx.serialization.KSerializer
import kotlinx.serialization.builtins.nullable

val LocalComponentContext: ProvidableCompositionLocal<ComponentContext> =
    staticCompositionLocalOf { error("Root component context was not provided") }

@Composable
fun ProvideComponentContext(componentContext: ComponentContext, content: @Composable () -> Unit) {
    CompositionLocalProvider(LocalComponentContext provides componentContext, content = content)
}

@Composable
inline fun <reified C : Screen> ChildStack(
    source: StackNavigationSource<C>,
    noinline initialStack: () -> List<C>,
    modifier: Modifier = Modifier,
    key: String = "DefaultChildStack",
    animation: StackAnimation<C, ComponentContext>? = null,
    noinline content: @Composable (C) -> Unit,
) {
    ChildStack(
        stack = rememberChildStack(
            source = source,
            initialStack = initialStack,
            key = key,
        ),
        modifier = modifier,
        animation = animation,
        content = content,
    )
}

@Composable
fun <C : Any> ChildStack(
    stack: State<ChildStack<C, ComponentContext>>,
    modifier: Modifier = Modifier,
    animation: StackAnimation<C, ComponentContext>? = null,
    content: @Composable (C) -> Unit,
) {
    Children(
        stack = stack.value,
        modifier = modifier,
        animation = animation,
    ) { child ->
        ProvideComponentContext(child.instance) {
            content(child.configuration)
        }
    }
}

@Composable
inline fun <reified C : Screen> rememberChildStack(
    source: StackNavigationSource<C>,
    noinline initialStack: () -> List<C>,
    key: String = "DefaultChildStack",
): State<ChildStack<C, ComponentContext>> {
    val componentContext = LocalComponentContext.current

    return remember {
        componentContext.childStack(
            source = source,
            initialStack = initialStack,
            handleBackButton = true,
            childFactory = { _, childComponentContext -> childComponentContext },
            serializer = Screen.serializer() as KSerializer<C>,
        )
    }.subscribeAsState()
}