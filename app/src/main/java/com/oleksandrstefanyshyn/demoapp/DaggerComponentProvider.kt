package com.oleksandrstefanyshyn.demoapp

import android.app.Activity
import androidx.fragment.app.Fragment

interface DaggerComponentProvider {
    val appComponent: ApplicationComponent
}

val Activity.injector get() = (application as DaggerComponentProvider).appComponent

val Fragment.injector get() = requireActivity().injector
