package com.nexters.ilab.android.feature.myalbum.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.nexters.ilab.android.core.common.MyAlbumModel
import com.nexters.ilab.android.core.domain.repository.FileRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.collections.immutable.toImmutableList
import kotlinx.coroutines.launch
import org.orbitmvi.orbit.ContainerHost
import org.orbitmvi.orbit.syntax.simple.intent
import org.orbitmvi.orbit.syntax.simple.postSideEffect
import org.orbitmvi.orbit.syntax.simple.reduce
import org.orbitmvi.orbit.viewmodel.container
import javax.inject.Inject

@HiltViewModel
class MyAlbumViewModel @Inject constructor(
    private val fileRepository: FileRepository,
    // savedStateHandle: SavedStateHandle,
) : ViewModel(), ContainerHost<MyAlbumState, MyAlbumSideEffect> {
    override val container = container<MyAlbumState, MyAlbumSideEffect>(MyAlbumState())

//    private val imageStyle: String =
//        requireNotNull(savedStateHandle.get<String>(STYLE_NAME)) { "imageStyle is required." }
//    private val myAlbumImageUrlList: Array<String> =
//        requireNotNull(savedStateHandle.get<Array<String>>(MY_ALBUM_IMAGE_URL_LIST)) { "myAlbumImageUrlList is required." }

//    init {
//        Timber.d("imageUrlList: $myAlbumImageUrlList")
//        Timber.d("imageUrlList.toList(): ${listOf(*myAlbumImageUrlList)}")
//        intent {
//            reduce {
//                state.copy(
//                    styleName = imageStyle,
//                    myAlbumImageUrlList = listOf(*myAlbumImageUrlList).toImmutableList(),
//                )
//            }
//        }
//    }

    fun initMyAlbum(myAlbum: MyAlbumModel) = intent {
        reduce {
            state.copy(
                styleName = myAlbum.styleName,
                myAlbumImageUrlList = myAlbum.myAlbumImageUrlList.toImmutableList(),
            )
        }
    }

    fun shareMyAlbum() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            val imageList: MutableList<String> = mutableListOf()
            state.myAlbumImageUrlList.forEach { imageUrl ->
                imageList.add(imageUrl)
            }
            val imageUriList = fileRepository.getImageUriList(imageList)
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(MyAlbumSideEffect.ShareMyAlbum(imageUriList))
        }
    }

    fun saveMyAlbumImage() = intent {
        viewModelScope.launch {
            reduce {
                state.copy(isLoading = true)
            }
            val imageList: MutableList<String> = mutableListOf()
            state.myAlbumImageUrlList.forEach { imageUrl ->
                imageList.add(imageUrl)
            }
            fileRepository.saveImageFile(imageList.toList())
            reduce {
                state.copy(isLoading = false)
            }
            postSideEffect(MyAlbumSideEffect.SaveMyAlbumSuccess)
        }
    }

    fun deleteCacheDir() = intent {
        fileRepository.deleteCacheDir()
    }
}
