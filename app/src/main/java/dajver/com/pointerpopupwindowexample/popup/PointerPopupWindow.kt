package dajver.com.pointerpopupwindowexample.popup

import android.content.Context
import android.graphics.Rect
import android.graphics.drawable.ColorDrawable
import android.graphics.drawable.Drawable
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.PopupWindow
import dajver.com.pointerpopupwindowexample.R

class PointerPopupWindow private constructor(context: Context, width: Int, height: Int) : PopupWindow(width, height) {

    private val mContainer: LinearLayout
    private val mAnchorImage: ImageView
    private val mContent: FrameLayout
    private var mAlignMode = AlignMode.DEFAULT

    constructor(context: Context, width: Int) : this(context, width, ViewGroup.LayoutParams.WRAP_CONTENT) {}

    init {
        if (width < 0) {
            throw RuntimeException("You must specify the window width explicitly(do not use WRAP_CONTENT or MATCH_PARENT)!!!")
        }

        mContainer = LinearLayout(context)
        mContainer.orientation = LinearLayout.VERTICAL
        mAnchorImage = ImageView(context)
        mContent = FrameLayout(context)

        setBackgroundDrawable(context.resources.getDrawable(R.drawable.background_popup_style))
        isOutsideTouchable = true
        isFocusable = true
    }

    fun setAlignMode(mAlignMode: AlignMode) {
        this.mAlignMode = mAlignMode
    }

    fun setPointerImageRes(res: Int) {
        mAnchorImage.setImageResource(res)
    }

    override fun setContentView(contentView: View?) {
        if (contentView != null) {
            mContainer.removeAllViews()
            mContainer.addView(mAnchorImage, ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            mContainer.addView(mContent, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            mContent.addView(contentView, ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
            super.setContentView(mContainer)
        }
    }

    override fun setBackgroundDrawable(background: Drawable) {
        mContent.setBackgroundDrawable(background)
        super.setBackgroundDrawable(ColorDrawable())
    }

    fun showAsPointer(anchor: View) {
        showAsPointer(anchor, 0,
            Y_OFFSET_FROM_THE_ANCHOR
        )
    }

    private fun showAsPointer(anchor: View, xOffset: Int, yOffset: Int) {
        var xOffset = xOffset
        val displayFrame = Rect()
        anchor.getWindowVisibleDisplayFrame(displayFrame)
        val displayFrameWidth = displayFrame.right - displayFrame.left

        val loc = IntArray(2)
        anchor.getLocationInWindow(loc)

        if (mAlignMode == AlignMode.AUTO_OFFSET) {
            val offCenterRate = (displayFrame.centerX() - loc[0]) / displayFrameWidth.toFloat()
            xOffset = ((anchor.width - width) / 2 + offCenterRate * width / 2).toInt()
        } else if (mAlignMode == AlignMode.CENTER_FIX) {
            xOffset = (anchor.width - width) / 2
        }

        val left = loc[0] + xOffset
        val right = left + width

        if (right > displayFrameWidth) {
            xOffset = displayFrameWidth - width - loc[0]
        }

        if (left < displayFrame.left) {
            xOffset = displayFrame.left - loc[0]
        }

        computePointerLocation(anchor, xOffset)
        super.showAsDropDown(anchor, xOffset, yOffset, Gravity.CENTER)
    }

    private fun computePointerLocation(anchor: View, xOffset: Int) {
        val aw = anchor.width
        val dw = mAnchorImage.drawable.intrinsicWidth
        mAnchorImage.setPadding((aw - dw) / 2 - xOffset, 0, 0, 0)
    }

    enum class AlignMode {
        DEFAULT,
        CENTER_FIX,
        AUTO_OFFSET
    }

    companion object {
        private const val Y_OFFSET_FROM_THE_ANCHOR = -20
    }
}