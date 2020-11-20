package com.squareup.sample.helloworkflow

import com.squareup.sample.helloworkflow.HelloWorkflow.Rendering
import com.squareup.sample.helloworkflow.HelloWorkflow.State
import com.squareup.sample.helloworkflow.HelloWorkflow.State.Goodbye
import com.squareup.sample.helloworkflow.HelloWorkflow.State.Hello
import com.squareup.workflow1.Snapshot
import com.squareup.workflow1.StatefulWorkflow
import com.squareup.workflow1.action
import com.squareup.workflow1.parse

object HelloWorkflow : StatefulWorkflow<Unit, State, Nothing, Rendering>() {
  enum class State {
    Hello,
    Goodbye
  }

  data class Rendering(
    val message: String,
    val onClick: () -> Unit
  )

  override fun initialState(
    props: Unit,
    snapshot: Snapshot?
  ): State = snapshot?.bytes?.parse { source -> if (source.readInt() == 1) Hello else Goodbye }
      ?: Hello

  override fun render(
    props: Unit,
    state: State,
    context: RenderContext
  ): Rendering {
    return Rendering(
        message = state.name,
        onClick = { context.actionSink.send(helloAction) }
    )
  }

  override fun snapshotState(state: State): Snapshot = Snapshot.of(if (state == Hello) 1 else 0)

  private val helloAction = action {
    state = when (state) {
      Hello -> Goodbye
      Goodbye -> Hello
    }
  }
}
