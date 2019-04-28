package com.sample.searchnewsandroidapplication.model

import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import androidx.lifecycle.MutableLiveData
import com.bumptech.glide.Glide
import com.sample.searchnewsandroidapplication.ui.NewsDetailActivity
import java.io.Serializable

data class NewsArticleItem(
    val source: Article?,
    val author: String?,
    val title: String?,
    val description: String?,
    val url: String?,
    val urlToImage: String?,
    val publishedAt: String?,
    val content: String?
) : Serializable, Parcelable {

    fun onArticleItemClicked(view: View, item: NewsArticleItem) {
        val intent = Intent(view.context, NewsDetailActivity::class.java)
        intent.putExtra("news_item", item as Serializable)
        view.context.startActivity(intent)
    }

    fun onArticleUrlClicked(view: View, url: String) {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    fun getNewsPublishedTime(publishedTime: String): String = if (publishedTime.contains('T')) {
        val splitTime: List<String>? = publishedTime.split('T')
        val date: String? = splitTime?.get(0)
        val timeSplit: List<String>? = splitTime?.get(1)?.split(':')
        String.format(date + " " + timeSplit?.get(0) + ":" + timeSplit?.get(1))
    } else
        publishedTime

    constructor() : this(Article(""), "", "", "", "", "", "", "")

    constructor(parcel: Parcel) : this(
        parcel.readSerializable() as Article,
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeSerializable(source)
        parcel.writeString(author)
        parcel.writeString(title)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(urlToImage)
        parcel.writeString(publishedAt)
        parcel.writeString(content)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsArticleItem> {

        @JvmStatic
        @BindingAdapter("app:imageUrl") // custom xml field
        fun loadImage(imageView: ImageView, url: String?) {
            url?.let {
                // url is not null
                Glide.with(imageView.context).load(it).into(imageView)
                imageView.visibility = View.VISIBLE
            }
        }

        override fun createFromParcel(parcel: Parcel): NewsArticleItem {
            return NewsArticleItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsArticleItem?> {
            return arrayOfNulls(size)
        }
    }
}