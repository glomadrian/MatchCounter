package com.github.glomadrian.feed

import com.github.glomadrian.mvi.Intent

sealed class FeedIntent : Intent{
    object InitView : FeedIntent()
    object AddMovieActionIntent : FeedIntent()
    object RefreshIntent : FeedIntent()
}