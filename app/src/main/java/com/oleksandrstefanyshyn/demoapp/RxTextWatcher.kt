package com.oleksandrstefanyshyn.demoapp

import android.text.Editable
import android.text.TextWatcher
import io.reactivex.Observable
import io.reactivex.subjects.BehaviorSubject
import java.util.concurrent.TimeUnit

class RxTextWatcher : TextWatcher {
    private val textChangeSubject = BehaviorSubject.create<String>()

    override fun afterTextChanged(s: Editable?) {
        if (s.isNullOrEmpty()) textChangeSubject.onNext("0")
        else textChangeSubject.onNext(s.toString())
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
    }

    override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
    }

    val textChangeObserver: Observable<String> =
        textChangeSubject.debounce(200, TimeUnit.MILLISECONDS)
            .distinctUntilChanged()
}
