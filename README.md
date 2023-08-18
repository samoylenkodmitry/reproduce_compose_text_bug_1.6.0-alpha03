# Reproducing Text Rendering Bug in Compose 1.6.0-alpha03

## Bug has been fixed

You can check it in a snapshot version: https://github.com/samoylenkodmitry/reproduce_compose_text_bug_1.6.0-alpha03/tree/snapshot_version

## Introduction

This article presents a concise layout that reproduces a bug within the Compose framework version 1.6.0-alpha03. The bug is related to rendering text elements within a complex layout structure. Links to the relevant bug reports are provided for further investigation.

## Bug Description

When attempting to render text elements within a Compose layout that involves nested structures such as `Box` and `LazyList`, an exception is thrown, leading to a fatal crash. The exception message indicates a `java.lang.IllegalArgumentException` with the error message "no paragraph".

```
Fatal Exception: java.lang.IllegalArgumentException: no paragraph
       at androidx.compose.foundation.text.modifiers.TextStringSimpleNode.draw(TextStringSimpleNode.kt:391)
       at androidx.compose.ui.node.LayoutNodeDrawScope.drawDirect-x_KDEd0$ui_release(LayoutNodeDrawScope.kt:105)
       at androidx.compose.ui.node.LayoutNodeDrawScope.draw-x_KDEd0$ui_release(LayoutNodeDrawScope.kt:86)
```

## Links to Bug Reports

For additional context and information, refer to the following bug reports:

- [Issue 295518534](https://issuetracker.google.com/issues/295518534)
- [Issue 295214720](https://issuetracker.google.com/issues/295214720)

## Reproduction Steps

To recreate the problematic scenario, follow these steps:

1. Implement a Composable function named `Greeting` as demonstrated below.
2. Within the `Greeting` function, define the number of items to display using `itemsCount` and create a `LazyListState` instance named `state`.
3. Utilize `LaunchedEffect` to adjust the `itemsCount` with a slight delay, thereby simulating scrolling and recomposition actions.
4. Use a second `LaunchedEffect` to trigger an animated scroll to the updated `itemsCount`.
5. Create an array of sample text strings to display.
6. Construct a `LazyColumn` with the defined `state` and iterate over `itemsCount`.
7. For each item, encapsulate a `Text` element within a `Box`.

```kotlin
@Composable
fun Greeting() {
    var itemsCount by remember { mutableIntStateOf(100) }
    val state = rememberLazyListState()
    
    LaunchedEffect(itemsCount) {
        delay(1)
        itemsCount = 100 + (itemsCount + 1) % 200
    }
    
    LaunchedEffect(itemsCount) {
        state.animateScrollToItem(itemsCount)
    }
    
    val texts = arrayOf("Hello", "World", "!")
    
    LazyColumn(state = state) {
        items(itemsCount) {
            Box {
                Text(
                    text = texts.random()
                )
            }
        }
    }
}
```

## Conclusion

The provided layout, utilizing the Compose framework version 1.6.0-alpha03, exposes a bug that causes the rendering of text elements to fail when placed within a Box and LazyList combination. By following the reproduction steps, developers can observe the issue and refer to the linked bug reports for further updates and resolutions from the Compose team.



