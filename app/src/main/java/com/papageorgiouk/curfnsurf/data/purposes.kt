package com.papageorgiouk.curfnsurf.data

import android.content.Context
import com.papageorgiouk.curfnsurf.R

data class Purpose(val purpose: String, val number: Int)

class PurposeProvider(context: Context) {

    val purposes: List<Purpose> = listOf(
        Purpose(context.getString(R.string.purpose_1), 1),
        Purpose(context.getString(R.string.purpose_2), 2),
        Purpose(context.getString(R.string.purpose_3), 3),
        Purpose(context.getString(R.string.purpose_4), 4),
        Purpose(context.getString(R.string.purpose_5), 5),
        Purpose(context.getString(R.string.purpose_6), 6),
        Purpose(context.getString(R.string.purpose_7), 7),
        Purpose(context.getString(R.string.purpose_8), 8)
    )

}