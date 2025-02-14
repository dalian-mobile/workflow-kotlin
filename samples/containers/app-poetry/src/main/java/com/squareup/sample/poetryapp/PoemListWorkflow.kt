package com.squareup.sample.poetryapp

import com.squareup.sample.poetry.model.Poem
import com.squareup.workflow1.StatelessWorkflow

/**
 * Renders a given ordered list of [Poem]s. Reports the index of any that are clicked.
 */
object PoemListWorkflow : StatelessWorkflow<List<Poem>, Int, PoemListRendering>() {

  override fun render(
    renderProps: List<Poem>,
    context: RenderContext
  ): PoemListRendering {
    return PoemListRendering(
      poems = renderProps,
      onPoemSelected = context.eventHandler { index -> setOutput(index) }
    )
  }
}
