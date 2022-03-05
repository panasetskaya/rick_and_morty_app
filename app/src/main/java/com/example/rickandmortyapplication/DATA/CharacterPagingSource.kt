package com.example.rickandmortyapplication.DATA

import android.util.Log
import android.widget.Toast
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapplication.API.ApiFactory
import com.example.rickandmortyapplication.API.ApiPagingService
import com.example.rickandmortyapplication.POJO.Character
import io.reactivex.rxjava3.disposables.CompositeDisposable
import io.reactivex.rxjava3.schedulers.Schedulers
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1
const val NETWORK_PAGE_SIZE = 20

class CharacterPagingSource(private val api: ApiPagingService): PagingSource<Int, Character>()  {


    override fun getRefreshKey(state: PagingState<Int, Character>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Character> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            var result = mutableListOf<Character>()

            api.getPagingCharactersExample(pageIndex).characters?.let {
                result = it.toMutableList()
                Log.i("MyRes", "characters loaded")
            }

            val nextKey =
                if (result.isEmpty()) {
                    null
                } else {
                    // By default, initial load size = 3 * NETWORK PAGE SIZE
                    // ensure we're not requesting duplicating items at the 2nd request
                    pageIndex + (params.loadSize / NETWORK_PAGE_SIZE)
                }
            LoadResult.Page(
                data = result,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex,
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            Log.i("MyRes", "IOException here")
            return LoadResult.Error(exception)
        } catch (exception: HttpException) {
            Log.i("MyRes", "HttpException here")
            exception.printStackTrace()
            return LoadResult.Error(exception)
        }
    }
}