package com.lmy.hwahae.ui.adpaters

import android.content.res.Configuration
import android.content.res.Resources
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.core.net.toUri
import androidx.paging.PagedListAdapter
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.lmy.hwahae.R
import com.lmy.hwahae.datasoruce.remote.model.IndexViewItem
import com.lmy.hwahae.ui.utils.FormatPlainToPrice

class IndexViewAdapter(onIndexViewAdapterListener: IndexViewAdapterListener): PagedListAdapter<IndexViewItem, IndexViewAdapter.ItemViewHolder>(DIFF_CALLBACK) {

    /**
     * Save the parent view to resize items according to orientation
     */
    private lateinit var parentViewGroup: ViewGroup

    /**
     * Prevent multiple clicks
     */
    private var mLastClickTime = System.currentTimeMillis()
    private val CLICK_TIME_INTERVAL: Long = 3000

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
        private val llMain: LinearLayout
        private val ivThumbnail: ImageView
        private val tvTitle: TextView
        private val tvPrice: TextView

        init {
            llMain = itemView.findViewById(R.id.ll_item)
            ivThumbnail = itemView.findViewById(R.id.iv_thumbnail)
            tvTitle = itemView.findViewById(R.id.tv_title)
            tvPrice = itemView.findViewById(R.id.tv_price)

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
                ivThumbnail.layoutParams.height = (itemView.layoutParams.height / 1.5).toInt()
            }
            else{
                /**
                 * Orientation Portrait
                 * Resize ViewHolder's height
                 * 3 items per page in row
                 */
                itemView.layoutParams.height = (recyclerViewHeight / 2.5).toInt()
                ivThumbnail.layoutParams.height = (itemView.layoutParams.height / 1.5).toInt()
            }
        }

        fun bind(product: IndexViewItem?) {
            productId = product?.id
            tvTitle.text = product?.title
            tvPrice.text = FormatPlainToPrice.start(product?.price)
            Glide.with(itemView)
                .asBitmap()
                .load(product?.thumbnail_image?.toUri())
                .into(ivThumbnail)

            llMain.setOnClickListener { // Prevent from multiple clicking
                val now = System.currentTimeMillis()
                if (now - mLastClickTime < CLICK_TIME_INTERVAL) {
                }
                else {
                    mLastClickTime = now
                    onIndexViewAdapterListener.sendProductId(productId)
                }
            }
        }
    }

    /**
     * Reset the list click time
     */
    fun resetLastClickTime() {
        mLastClickTime -= CLICK_TIME_INTERVAL - 1000
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
