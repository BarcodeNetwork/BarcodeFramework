package com.vjh0107.barcode.framework

import com.vjh0107.barcode.framework.utils.print
import io.kotest.core.spec.style.AnnotationSpec
import io.kotest.matchers.shouldBe
import io.kotest.matchers.shouldNotBe
import io.kotest.matchers.types.shouldNotBeSameInstanceAs
import org.bukkit.potion.PotionEffectType

class KotlinTest : AnnotationSpec() {
    @Test
    fun calcTest() {
        1.1 * 2 shouldBe 2.2
    }

    @Test
    fun packageNameTest() {
        this.javaClass.packageName.print() shouldBe "com.vjh0107.barcode.framework"
    }

    @Test
    fun test() {
        val classes = listOf(ExampleClass1::class, ExampleClass2::class)

        classes.forEach {
            it.constructors.size.print()
        }
    }


    interface ExampleClass

    class ExampleClass1(val a: String) : ExampleClass {

    }

    class ExampleClass2() : ExampleClass {

    }

    @Test
    fun splitTest() {
        val string = "a\nb\nc"
        val list = string.split("\n")
        list.size.print()
        assert(list.size == 3)
    }

    @Test
    fun collectionTest() {
        val list = mutableListOf<String>()
        list[2] = "1"
    }

    @Test
    fun intToByteTest() {
        val target = 4
        target.toByte().print()
    }

    @Test
    fun copyTest() {
        data class A(
            val potionType: MutableList<VALUE>
        )
        val a = A(mutableListOf(VALUE.VALUE_A, VALUE.VALUE_B))
        a.print()
        a.potionType.forEach {
            it.valueParameter.print()
            it.valueParameter.a.print()
        }
        val b = a.copy()
        b.print()
        b.potionType.forEach {
            it.valueParameter.print()
            it.valueParameter.a.print()
        }
        a.toString()
        a shouldNotBeSameInstanceAs b
    }

    enum class VALUE(val valueParameter: ValueParameter) {
        VALUE_A(ValueParameter("A")),
        VALUE_B(ValueParameter("B")),
        VALUE_C(ValueParameter("C"))
    }

    class ValueParameter(val a: String)
}