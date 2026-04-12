package io.github.untoastedtoast.foldio.persistence.loader

import io.github.untoastedtoast.foldio.PASSES
import io.github.untoastedtoast.foldio.loadJson
import org.junit.Assert
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class JsonLoaderTest(
    private val passName: String,
) {
    @Test
    fun testPass() {
        val jsonString = loadJson(passName)
        Assert.assertNotNull(jsonString)
        val json = JsonLoader.load(jsonString!!)
        Assert.assertNotNull(json) // Failures would throw an exception
    }

    companion object {
        @JvmStatic
        @Parameterized.Parameters(name = "{0}")
        fun data(): List<Array<String>> = PASSES.map { pass -> Array(1) { pass } }
    }
}
