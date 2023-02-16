package com.waleed.asteroidradarapp

import android.widget.ImageView
import android.widget.TextView
import androidx.core.net.toUri
import androidx.databinding.BindingAdapter
import androidx.recyclerview.widget.RecyclerView
import com.squareup.picasso.Picasso
import com.waleed.asteroidradarapp.main.MainFragmentAdapter


@BindingAdapter("listData")
fun bindRecyclerView(recyclerView: RecyclerView, data: List<Asteroid>?) {
    val mainAdapter = recyclerView.adapter as MainFragmentAdapter
    mainAdapter.setAsteroidsList(data)
}


@BindingAdapter("imageUrl")
fun bindImage(imgView: ImageView, picture: PictureOfDay?) {
    picture?.url?.let {
        if (picture.mediaType != "image")
            return
        val imgUri = picture.url.toUri()
        Picasso.get().load(imgUri).into(imgView)

    }
}


@BindingAdapter("setStatus")
fun bindStatus(imgView: ImageView, isPotentiallyHazardous: Boolean) {
    imgView.apply {
        contentDescription = if (isPotentiallyHazardous) {
            setImageResource(R.drawable.ic_status_potentially_hazardous)
            context.getString(R.string.potentially_hazardous_status)
        } else {
            setImageResource(R.drawable.ic_status_normal)
            context.getString(R.string.safe_status)
        }
    }

}

@BindingAdapter("asteroidStatusImage")
fun bindDetailsStatusImage(imageView: ImageView, isHazardous: Boolean) {
    imageView.apply {
        contentDescription = if (isHazardous) {
            imageView.setImageResource(R.drawable.asteroid_hazardous)
            context.getString(R.string.potentially_hazardous_asteroid_image)
        } else {
            imageView.setImageResource(R.drawable.asteroid_safe)
            context.getString(R.string.not_hazardous_asteroid_image)
        }
    }
}

@BindingAdapter("astronomicalUnitText")
fun bindTextViewToAstronomicalUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.astronomical_unit_format), number)
}

@BindingAdapter("kmUnitText")
fun bindTextViewToKmUnit(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_unit_format), number)
}

@BindingAdapter("velocityText")
fun bindTextViewToDisplayVelocity(textView: TextView, number: Double) {
    val context = textView.context
    textView.text = String.format(context.getString(R.string.km_s_unit_format), number)
}

