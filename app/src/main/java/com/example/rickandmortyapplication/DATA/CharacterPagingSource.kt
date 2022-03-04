package com.example.rickandmortyapplication.DATA

import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapplication.API.ApiFactory
import com.example.rickandmortyapplication.POJO.Character
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 20

class CharacterPagingSource(): PagingSource<Int, Character>()  {

    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {

            val characters = mutableListOf<Character>()

            ApiFactory.apiService.getCharacters()
                .subscribeOn(Schedulers.io())
                .subscribe({
                    if (it.characters!=null) {
                        for (character in it.characters) {
                            characters.add(character)
                        }
                        Log.i("MyRes", "adding characters succeded"+characters.toString())
                    } else {
                        Log.i("MyRes", "adding characters failed, characters = null")}
                }, {
                    Log.i("MyRes", "loading characters failed: "+it.message)
                })

            val nextKey =
                if (characters.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = characters.toList(),
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            Log.i("MyRes", "IOException here")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("MyRes", "HttpException here")
            return LoadResult.Error(exception)
        }
    }
}