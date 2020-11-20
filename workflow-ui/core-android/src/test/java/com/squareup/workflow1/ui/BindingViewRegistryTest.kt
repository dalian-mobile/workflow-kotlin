package com.squareup.workflow1.ui

import com.google.common.truth.Truth.assertThat
import org.junit.Test
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

@OptIn(WorkflowUiExperimentalApi::class)
class BindingViewRegistryTest {

  @Test fun `keys from bindings`() {
    val factory1 = TestViewFactory(FooRendering::class)
    val factory2 = TestViewFactory(BarRendering::class)
    val registry = TypedViewRegistry(factory1, factory2)

    assertThat(registry.keys).containsExactly(factory1.type, factory2.type)
  }

  @Test fun `constructor throws on duplicates`() {
    val factory1 = TestViewFactory(FooRendering::class)
    val factory2 = TestViewFactory(FooRendering::class)

    val error = assertFailsWith<IllegalStateException> {
      TypedViewRegistry(factory1, factory2)
    }
    assertThat(error).hasMessageThat()
        .endsWith("must not have duplicate entries.")
    assertThat(error).hasMessageThat()
        .contains(FooRendering::class.java.name)
  }

  @Test fun `getFactoryFor works`() {
    val fooFactory = TestViewFactory(FooRendering::class)
    val registry = TypedViewRegistry(fooFactory)

    val factory = registry.getFactoryFor(FooRendering::class)
    assertThat(factory).isSameInstanceAs(fooFactory)
  }

  @Test fun `getFactoryFor throws on missing binding`() {
    val fooFactory = TestViewFactory(FooRendering::class)
    val registry = TypedViewRegistry(fooFactory)

    val error = assertFailsWith<IllegalArgumentException> {
      registry.getFactoryFor(BarRendering::class)
    }
    assertThat(error).hasMessageThat()
        .isEqualTo(
            "A ${ViewFactory::class.java.name} should have been registered to display a ${BarRendering::class}."
        )
  }

  @Test fun `ViewRegistry with no arguments infers type`() {
    val registry = ViewRegistry()
    assertTrue(registry.keys.isEmpty())
  }

  private object FooRendering
  private object BarRendering
}
