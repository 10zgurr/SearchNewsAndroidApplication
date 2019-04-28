package com.sample.searchnewsandroidapplication.model

import android.content.Intent
import android.net.Uri
import android.os.Parcel
import android.os.Parcelable
import android.view.View
import com.sample.searchnewsandroidapplication.ui.NewsDetailActivity
import java.io.Serializable

data class NewsSourceItem(
    var id: String?,
    var name: String?,
    var description: String?,
    var url: String?,
    var category: String?,
    var language: String?,
    var country: String?
) : Serializable, Parcelable {

    fun onSourceNewsClicked(view: View, item: NewsSourceItem) {
        val intent = Intent(view.context, NewsDetailActivity::class.java)
        intent.putExtra("news_item", item as Serializable)
        view.context.startActivity(intent)
    }

    fun onSourceNewsUrlClicked(view: View, url: String) {
        view.context.startActivity(Intent(Intent.ACTION_VIEW, Uri.parse(url)))
    }

    constructor() : this("", "", "", "", "", "", "")

    constructor(parcel: Parcel) : this(
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString(),
        parcel.readString()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(id)
        parcel.writeString(name)
        parcel.writeString(description)
        parcel.writeString(url)
        parcel.writeString(category)
        parcel.writeString(language)
        parcel.writeString(country)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<NewsSourceItem> {
        override fun createFromParcel(parcel: Parcel): NewsSourceItem {
            return NewsSourceItem(parcel)
        }

        override fun newArray(size: Int): Array<NewsSourceItem?> {
            return arrayOfNulls(size)
        }
    }
}