package org.qweco.dndproject.adapter.itemTouchHelper

import android.support.v7.widget.RecyclerView
import android.support.v7.widget.helper.ItemTouchHelper


class MyItemTouchHelperCallback(private var mAdapter: ItemTouchHelperAdapter): ItemTouchHelper.Callback() {

    override fun getMovementFlags(arg0: RecyclerView, arg1: RecyclerView.ViewHolder): Int {
        val dragFlags = ItemTouchHelper.UP or ItemTouchHelper.DOWN
        val swipeFlags = ItemTouchHelper.START or ItemTouchHelper.END
        return makeMovementFlags(dragFlags, swipeFlags)
    }

    override fun onMove(recyclerView: RecyclerView, viewHolder: RecyclerView.ViewHolder, target: RecyclerView.ViewHolder): Boolean {
        mAdapter.onItemMove(viewHolder.getAdapterPosition(), target.getAdapterPosition())
        return true
    }

    override fun onSwiped(viewHolder: RecyclerView.ViewHolder, direction: Int) {
        mAdapter.onItemDismiss(viewHolder.adapterPosition)
    }

    override fun isLongPressDragEnabled(): Boolean {
        return mAdapter.isLongPressDragEnabled()
    }

    override fun isItemViewSwipeEnabled(): Boolean {
        return true
    }
}