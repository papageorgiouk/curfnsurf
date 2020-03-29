package com.papageorgiouk.curfnsurf.ui.about

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.mikepenz.aboutlibraries.entity.Library
import com.papageorgiouk.curfnsurf.R

const val VIEW_TITLE = 0
const val VIEW_LIBRARY = 1

class LibrariesAdapter(val libs: List<Library>) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {

    override fun getItemViewType(position: Int): Int {
        return when (position) {
            0 -> VIEW_TITLE
            else -> VIEW_LIBRARY
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            VIEW_TITLE -> LibrariesTitleVH(inflater.inflate(R.layout.item_libraries_title, parent, false))
            VIEW_LIBRARY -> LibraryVH(
                LayoutInflater.from(parent.context)
                    .inflate(R.layout.item_library, parent, false)
            )
            else -> throw IllegalStateException()

        }
    }

    override fun getItemCount(): Int = libs.size

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        if (getItemViewType(position) == VIEW_LIBRARY) (holder as  LibraryVH).bind(libs[position])
    }

    inner class LibraryVH(itemView: View)  : RecyclerView.ViewHolder(itemView) {

        private val txt_lib_title = itemView.findViewById<TextView>(R.id.txt_lib_title)
        private val txt_lib_copyrighter = itemView.findViewById<TextView>(R.id.txt_lib_copyrighter)
        private val txt_descr = itemView.findViewById<TextView>(R.id.txt_descr)
        private val txt_license = itemView.findViewById<TextView>(R.id.txt_license)

        fun bind(lib: Library) {
            txt_lib_title.text = lib.libraryName
            txt_lib_copyrighter.text = lib.author
            txt_descr.text  = lib.libraryDescription
            txt_license.text = lib.license?.licenseName ?: ""
        }
    }

    inner class LibrariesTitleVH(itemView: View) : RecyclerView.ViewHolder(itemView)

}