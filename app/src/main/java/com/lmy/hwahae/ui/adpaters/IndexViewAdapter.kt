package com.lmy.hwahae.ui.adpaters

import android.content.res.Configuration
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
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import kotlinx.android.synthetic.main.layout_index_view_items.view.*
import java.text.NumberFormat
import java.util.*

class IndexViewAdapter(onIndexViewAdapterListener: IndexViewAdapterListener): PagedListAdapter<IndexViewItem, IndexViewAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    /**
     * Save the parent view to resize items according to orientation
     */
    private lateinit var parentViewGroup: ViewGroup

    /**
     * Call back when user clicks the product item to check it in detail
     */
    private val onIndexViewAdapterListener: IndexViewAdapterListener = onIndexViewAdapterListener

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemViewHolder {
        parentViewGroup = parent

        val layoutView: LinearLayout = LayoutInflater.from(parent.context).inflate(R.layout.layout_index_view_items, parent, false) as LinearLayout
        return ItemViewHolder(layoutView)
    }

    /**
     * Bind data to viewHolder
     */
    override fun onBindViewHolder(holder: ItemViewHolder, position: Int) { holder.bind(getItem(position)) }

    /**
     * ItemViewHolder
     */
    inner class ItemViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private var productId: Int? = null

        init {
            var recyclerViewHeight = parentViewGroup.findViewById<RecyclerView>(R.id.rv_product).layoutManager?.height!!
            var appBarLayoutHeight = (Resources.getSystem().displayMetrics.heightPixels / 12.5).toInt()
            recyclerViewHeight += appBarLayoutHeight

            if(Configuration.ORIENTATION_LANDSCAPE == Resources.getSystem().configuration.orientation) {
                /**
                 * Orientation Landscape
                 * Resize ViewHolder's height
                 * 1 item per page in row
                 */
                itemView.layoutParams.height = (recyclerViewHeight / 1)
                itemView.findViewById<ImageView>(R.id.iv_thumbnail).layoutParams.height = (itemView.layoutParams.height / 1.5).toInt()
            }
            else{
                /**
                 * Orientation Portrait
                 * Resize ViewHolder's height
                 * 2.5 items per page in row
                 */
                itemView.layoutParams.height = (recyclerViewHeight / 2.5).toInt()
                itemView.findViewById<ImageView>(R.id.iv_thumbnail).layoutParams.height = (itemView.layoutParams.height / 1.5).toInt()
            }
        }

        fun bind(product: IndexViewItem?) {
            productId = product?.id
            itemView.tv_title.text = product?.title
            itemView.tv_price.text = formatPrice(product?.price)
            Glide.with(itemView)
                .asBitmap()
                .load(product?.thumbnail_image?.toUri())
                .into(itemView.iv_thumbnail)

            itemView.ll_item.setOnClickListener {
                onIndexViewAdapterListener.sendProductId(productId)
            }
        }

        /* Format, ex) 10000 -> 10,000원 */
        private fun formatPrice(price: String?): String =
            NumberFormat.getNumberInstance(Locale.KOREA).format(price?.toInt()) + "원"
    }

    /**
     * Compare to decide whether data should be fetched or not
     */
    companion object {
        private val DIFF_CALLBACK = object : DiffUtil.ItemCallback<IndexViewItem>() {
            override fun areItemsTheSame(oldItem: IndexViewItem, newItem: IndexViewItem): Boolean = oldItem.id == newItem.id
            override fun areContentsTheSame(oldItem: IndexViewItem, newItem: IndexViewItem): Boolean = oldItem == newItem
        }
    }
}
