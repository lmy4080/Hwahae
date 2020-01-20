package com.lmy.hwahae.ui.adpaters

import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import androidx.core.net.toUri
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lmy.hwahae.R
import com.lmy.hwahae.datasoruce.model.IndexViewProduct
import kotlinx.android.synthetic.main.layout_index_view_items.view.*
import java.text.NumberFormat
import java.util.*

class IndexViewAdapter: PagedListAdapter<IndexViewProduct, IndexViewAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        val layoutView: LinearLayout = LayoutInflater.from(parent.context).inflate(R.layout.layout_index_view_items, parent, false) as LinearLayout
        resizeViewHolder(layoutView, parent)
        return ItemViewHolder(layoutView)
    }

    /**
     * Resize ViewHolder's height
     * 2.5 items per page in row
     */
    private fun resizeViewHolder(layoutView: LinearLayout, parent: ViewGroup) {

        var recyclerViewHeight = parent.findViewById<RecyclerView>(R.id.rv_product).layoutManager?.height!!
        var appBarLayoutHeight = Resources.getSystem().displayMetrics.heightPixels / 15

        recyclerViewHeight += appBarLayoutHeight
        layoutView.layoutParams.height = (recyclerViewHeight / 2.5).toInt()
        layoutView.findViewById<ImageView>(R.id.iv_thumbnail).layoutParams.height = (layoutView.layoutParams.height / 1.5).toInt()
    }

    /**
     * Bind data to viewHolder
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) { holder.bind(getItem(position)) }

    /**
     * ItemViewHolder
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        fun bind(product: IndexViewProduct?) {
            itemView.tv_title.text = product?.title
            itemView.tv_price.text = formatPrice(product?.price)
            Glide.with(itemView)
                .asBitmap()
                .load(product?.thumbnail_image?.toUri())
                .into(itemView.iv_thumbnail)
        }

        /* Format, ex) 10000 -> 10,000원 */
        private fun formatPrice(price: String?): String =
            NumberFormat.getNumberInstance(Locale.KOREA).format(price?.toInt()) + "원"

    }

    /**
     * Compare to decide whether data should be fetched or not
     */
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IndexViewProduct>() {
            override fun areItemsTheSame(oldItem: IndexViewProduct, newItem: IndexViewProduct): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: IndexViewProduct, newItem: IndexViewProduct): Boolean = oldItem == newItem
        }
    }
}
