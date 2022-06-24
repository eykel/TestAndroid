package com.android.test.testandroid.ui.helper

import android.app.Activity
import android.content.Context
import android.location.Geocoder
import android.view.View
import android.view.inputmethod.InputMethodManager
import android.widget.ImageView
import androidx.annotation.StringRes
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import com.android.test.testandroid.ui.helper.Constants.EMPTY_STRING
import com.android.test.testandroid.R
import com.android.test.testandroid.di.KoinInjector
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.RequestOptions
import org.koin.core.component.get


fun getString(@StringRes id: Int) : String = runCatching {
    KoinInjector.get<Context>().getString(id)
}.getOrNull() ?: EMPTY_STRING

fun View.gone() {
    visibility = View.GONE
}

fun View.visible() {
    visibility = View.VISIBLE
}

inline fun <reified T> Fragment.observe(
    liveData: LiveData<T>,
    crossinline execution: (T) -> Unit
){
    liveData.observe(viewLifecycleOwner) {
        execution(it)
    }
}

fun Fragment.getAddress(latitude: Double, longitude: Double) : String{
    val address = Geocoder(requireContext()).getFromLocation(latitude,longitude, 1).firstOrNull()
    return address?.let {
        getString(
            R.string.address_text,
            it.thoroughfare,
            it.featureName,
            it.subLocality,
            it.subAdminArea,
            it.adminArea)
    } ?: EMPTY_STRING
}

fun ImageView.loadImageUrl(url: String) {

    val requestOptions = RequestOptions().run {
        diskCacheStrategy(DiskCacheStrategy.ALL)
    }

    Glide.with(context)
        .load(url)
        .circleCrop()
        .placeholder(R.drawable.ic_resource_default)
        .error(R.drawable.ic_resource_default)
        .apply(requestOptions)
        .into(this)
}

fun Fragment.hideKeyboard() {
    view?.let {
        val inputMethodManager = it.context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(it.windowToken, 0)
    }
}

fun String.isValidEmail() = this.isNotEmpty() && email.matches(this)

private val email = (
        "[a-zA-Z0-9\\+\\.\\_\\%\\-\\+]{1,256}\\@[a-zA-Z0-9]" +
                "[a-zA-Z0-9\\-]{0,64}(\\.[a-zA-Z0-9\\-]{0,25})+"
        ).toRegex()