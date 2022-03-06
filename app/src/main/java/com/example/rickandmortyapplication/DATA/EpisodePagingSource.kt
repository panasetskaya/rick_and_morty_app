package com.example.rickandmortyapplication.DATA

import android.content.Context
import android.util.Log
import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.example.rickandmortyapplication.API.ApiPagingService
import com.example.rickandmortyapplication.POJO.Character
import com.example.rickandmortyapplication.POJO.Episode
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

private const val STARTING_PAGE_INDEX = 1

class EpisodePagingSource(private val api: ApiPagingService, val context: Context, val url: String): PagingSource<Int, Episode>()  {


    override fun getRefreshKey(state: PagingState<Int, Episode>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            state.closestPageToPosition(anchorPosition)?.prevKey?.plus(1)
                ?: state.closestPageToPosition(anchorPosition)?.nextKey?.minus(1)
        }
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Episode> {
        val pageIndex = params.key ?: STARTING_PAGE_INDEX

        return try {
            var result = mutableListOf<Episode>()
            val allEpisodes = listOf<Episode>()

            api.getPagingEpisodeExample(pageIndex).let {
                it.episodes?.let {
                    for (i in it) {
                        i.characters?.let {
                            if (it.contains(url)) {
                                result.add(i)
                            }
                        }
                    }
                }
                Log.i("MyRes", "episodes loaded")
            }

            val nextKey =
                if (result.isEmpty()) {
                    null
                } else { pageIndex+1 }

            LoadResult.Page(
                data = result,
                prevKey = if (pageIndex == STARTING_PAGE_INDEX) null else pageIndex-1,
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