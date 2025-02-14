@file:OptIn(WorkflowUiExperimentalApi::class)

package com.squareup.sample.compose.textinput

import androidx.compose.material.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.tooling.preview.Preview
import com.squareup.workflow1.ui.ViewEnvironment
import com.squareup.workflow1.ui.ViewRegistry
import com.squareup.workflow1.ui.WorkflowUiExperimentalApi
import com.squareup.workflow1.ui.compose.WorkflowRendering
import com.squareup.workflow1.ui.compose.renderAsState

private val viewRegistry = ViewRegistry(TextInputViewFactory)

private val viewEnvironment = ViewEnvironment(mapOf(ViewRegistry to viewRegistry))

@Composable fun TextInputApp() {
  MaterialTheme {
    val rendering by TextInputWorkflow.renderAsState(props = Unit, onOutput = {})
    WorkflowRendering(rendering, viewEnvironment)
  }
}

@Preview(showBackground = true)
@Composable internal fun TextInputAppPreview() {
  TextInputApp()
}
