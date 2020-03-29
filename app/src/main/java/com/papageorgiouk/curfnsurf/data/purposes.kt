package com.papageorgiouk.curfnsurf.data

import android.content.Context
import com.papageorgiouk.curfnsurf.R
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

data class Purpose(val purpose: String, val number: Int)

class PurposeProvider(val context: Context) {

    fun getPurposes(): Flow<List<Purpose>> = flow {
        val list = listOf(
            Purpose(context.getString(R.string.purpose_1), 1),
            Purpose(context.getString(R.string.purpose_2), 2),
            Purpose(context.getString(R.string.purpose_3), 3),
            Purpose(context.getString(R.string.purpose_4), 4),
            Purpose(context.getString(R.string.purpose_5), 5),
            Purpose(context.getString(R.string.purpose_6), 6),
            Purpose(context.getString(R.string.purpose_7), 7),
            Purpose(context.getString(R.string.purpose_8), 8)
        )

        emit(list)
    }.flowOn(Dispatchers.IO)

}