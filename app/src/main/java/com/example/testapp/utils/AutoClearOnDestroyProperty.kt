package com.example.testapp.utils

import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import kotlin.properties.ReadWriteProperty
import kotlin.reflect.KProperty

/**
 * Delegate for Lifecycle-aware references that automatically clears out the reference to the
 * object when the Fragment's View is destroyed or when the Activity is destroyed.
 *
 * This helps prevent the Fragment leaking it's Views, as Fragments can outlive their Views.
 */
class AutoClearOnDestroyProperty<T : Any> : ReadWriteProperty<LifecycleOwner, T>, DefaultLifecycleObserver {
    var value: T? = null
    private var registeredObserver: Boolean = false

    override fun getValue(thisRef: LifecycleOwner, property: KProperty<*>): T {
        return value ?: when (thisRef) {
            is Fragment -> throw FragmentViewLifecyclePropertyAccessException(property)
            is AppCompatActivity -> throw ActivityLifecyclePropertyAccessException(property)
            else -> throw ActivityLifecyclePropertyAccessException(property)
        }
    }

    override fun setValue(thisRef: LifecycleOwner, property: KProperty<*>, value: T) {
        this.value = value

        if (!registeredObserver) {
            when (thisRef) {
                is Fragment -> thisRef.viewLifecycleOwner.lifecycle.addObserver(this)
                is AppCompatActivity -> thisRef.lifecycle.addObserver(this)
                else -> thisRef.lifecycle.addObserver(this)
            }
            registeredObserver = true
        }
    }

    /**
     * For [Fragment], [onDestroy] will be called before [Fragment.onDestroyView].
     * For [AppCompatActivity], [onDestroy] will be called before [AppCompatActivity.onDestroy]
     * For other [LifecycleOwner], [onDestroy] will be called before onDestroy() of the respective
     * [LifecycleOwner]
     */
    override fun onDestroy(owner: LifecycleOwner) {
        value = null
        owner.lifecycle.removeObserver(this)
        registeredObserver = false
    }
}

/**
 * This exception indicates that a lifecycle-aware property was accessed outside of the Fragment's
 * view lifecycle- either before onCreateView() or after onDestroyView().
 *
 * This may be caused by a late callback or an RxJava stream that was not properly disposed of.
 */
class FragmentViewLifecyclePropertyAccessException(property: KProperty<*>) : RuntimeException(
    "ViewLifecycleProperty ${property.name} accessed outside of the Fragment's " +
            "view lifecycle (before onViewCreated() or after onDestroyView())."
)

/**
 * This exception indicates that a lifecycle-aware property was accessed outside of the Activity's
 * view lifecycle- either before onCreate() or after onDestroy().
 *
 * This may be caused by a late callback or an RxJava stream that was not properly disposed of.
 */
class ActivityLifecyclePropertyAccessException(property: KProperty<*>) : RuntimeException(
    "ViewLifecycleProperty ${property.name} accessed outside of the Activity's " +
            "lifecycle (before onCreate() or after onDestroy())."
)
