# reproduce_compose_text_bug_1.6.0-alpha03
This is a minimal compose layout to reproduce the bug:

* https://issuetracker.google.com/issues/295518534
* https://issuetracker.google.com/issues/295214720

```
Fatal Exception: java.lang.IllegalArgumentException: no paragraph
       at androidx.compose.foundation.text.modifiers.TextStringSimpleNode.draw(TextStringSimpleNode.kt:391)
       at androidx.compose.ui.node.LayoutNodeDrawScope.drawDirect-x_KDEd0$ui_release(LayoutNodeDrawScope.kt:105)
       at androidx.compose.ui.node.LayoutNodeDrawScope.draw-x_KDEd0$ui_release(LayoutNodeDrawScope.kt:86)
```

To reproduce the problem wrap text into a Box and LazyList, do scroll and recompose simulteneusly:

```
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
			Box { // <-- crucial for the crash
				Text(
					text = texts.random()
				)
			}
		}
	}
}
```
