package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.SubcomposeLayout
import androidx.compose.ui.tooling.preview.Preview
import com.example.myapplication.ui.theme.MyApplicationTheme
import kotlinx.coroutines.delay

class MainActivity : ComponentActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContent {
			MyApplicationTheme {
				Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
					Greeting()
				}
			}
		}
	}
}

@Composable
fun Greeting() {
	var itemsCount by remember { mutableIntStateOf(1000) }
	val state = rememberLazyListState()
	LaunchedEffect(itemsCount) {
		delay(1)
		itemsCount = 1000 + (itemsCount + 1) % 2000
	}
	LaunchedEffect(itemsCount) {
		state.animateScrollToItem(itemsCount)
	}
	val texts = arrayOf("Hello", "World", "!")
	LazyColumn(state = state) {
		items(itemsCount) {
			Box {
				SubcomposeLayout {
					val placeable = subcompose("text") {
						Text(texts.random())
					}.first().measure(it)
					layout(placeable.width, placeable.height) {
						placeable.place(0, 0)
					}
				}
			}
		}
	}
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
	MyApplicationTheme {
		Greeting()
	}
}