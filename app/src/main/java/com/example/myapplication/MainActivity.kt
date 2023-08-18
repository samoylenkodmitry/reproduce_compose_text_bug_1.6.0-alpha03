package com.example.myapplication

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.platform.ComposeView
import kotlinx.coroutines.delay

class MainActivity : AppCompatActivity() {
	override fun onCreate(savedInstanceState: Bundle?) {
		super.onCreate(savedInstanceState)
		setContentView(ComposeView(this).apply {
			setContent {
				Greeting()
			}
		})
	}
}

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
				BasicText(
					text = texts.random()
				)
			}
		}
	}
}