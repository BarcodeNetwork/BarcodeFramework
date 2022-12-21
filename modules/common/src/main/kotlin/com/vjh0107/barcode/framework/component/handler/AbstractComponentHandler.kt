package com.vjh0107.barcode.framework.component.handler

import com.vjh0107.barcode.framework.component.*
import com.vjh0107.barcode.framework.exceptions.ConstructorNotAllowedException
import com.vjh0107.barcode.framework.utils.uncheckedCast
import kotlin.jvm.internal.Reflection
import kotlin.reflect.KClass
import kotlin.reflect.full.findAnnotation

abstract class AbstractComponentHandler<T : IBarcodeComponent> : ComponentHandler {
    private val componentClasses: MutableMap<Int, MutableList<KClass<T>>> = mutableMapOf()
    private val instances: MutableSet<T> = mutableSetOf()

    /**
     * 컴포넌트를 register 합니다.
     */
    protected fun registerComponent(clazz: KClass<T>) {
        val order = getOrder(clazz)
        val componentList = componentClasses[order]
        if (componentList == null) {
            componentClasses[order] = mutableListOf(clazz)
        } else {
            componentList.add(clazz)
        }
    }

    /**
     * 컴포넌트 우선순위를 가져옵니다.
     * 타겟 클래스의 순서가 의존한 클래스들보다 더 우선순위에 있다면,
     * 의존한 클래스들의 순서또한 구해서 더한다.
     */
    private fun getOrder(clazz: KClass<*>): Int {
        var order = clazz.findAnnotation<BarcodeComponent>()!!.order

        clazz.findAnnotation<Depends>()?.let {
            it.depends.forEach { dependClass ->
                val dependClassOrder = getOrder(dependClass)
//                if (order <= dependClassOrder) {
//                    order += (dependClassOrder - order + 1)
//                }
                order += dependClassOrder
            }
        }
        return order
    }

    /**
     * 등록되어 인스턴스화되어 있는 컴포넌트들을 전부 가져옵니다.
     */
    fun getComponents(): Set<T> {
        return instances
    }

    /**
     * 컴포넌트들을 검색할 scope 를 구합니다.
     */
    abstract fun findTargetClasses(): MutableSet<Class<*>>

    /**
     * 컴포넌트 구현 규칙에 부합하는지 검증합니다.
     */
    abstract fun validate(clazz: KClass<T>): Boolean

    /**
     * 컴포넌트 클래스를 인자로 받아 인스턴스를 생성합니다.
     */
    abstract fun createInstance(clazz: KClass<T>): T

    /**
     * 인스턴스가 생성된 후
     */
    open fun onInstanceCreated(instance: T) {}

    // #3
    /**
     * enable 되고난 후
     */
    abstract fun onPostEnable()

    // #1
    private fun processAnnotations() {
        val classes: Set<Class<*>> = findTargetClasses()
        classes.forEach clazz@{ clazz ->
            for (annotation in clazz.annotations) {
                if (annotation is BarcodeComponent) {
                    val klass = Reflection.createKotlinClass(clazz).uncheckedCast<KClass<T>>() ?: return@clazz
                    if (validate(klass)) {
                        registerComponent(klass)
                    }
                }
            }
        }
    }

    // #2
    private fun createInstances() {
        try {
            componentClasses.toSortedMap().forEach { (_, classList) ->
                classList.forEach { clazz ->
                    val instance = createInstance(clazz)
                    onInstanceCreated(instance)
                    instances.add(instance)
                }
            }
        } catch (exception: NoSuchMethodException) {
            exception.printStackTrace()
            throw ConstructorNotAllowedException()
        }
    }

    // #1 -> #2 -> #3
    final override fun onEnable() {
        // 1. 어노테이션이 달린 클래스들을 클래스 컬렉션에 담는다.
        processAnnotations()
        // 2. 그 클래스들을 기반으로 인스턴스를 생성하여 인스턴스 컬렉션에 담는다.
        createInstances()
        // 3. 그 인스턴스 컬렉션을 통해 행해질 process 들이 담긴 abstract method 를 호출한다.
        onPostEnable()
    }

    override fun onReload() {
        instances.run {
            forEach {
                if (it is Reloadable) {
                    it.close()
                }
            }
            forEach {
                if (it is Reloadable) {
                    it.load()
                }
            }
        }
    }
}