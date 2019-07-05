package dajver.com.pointerpopupwindowexample

import android.content.Context
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.WindowManager
import android.widget.ListView
import android.widget.RelativeLayout
import androidx.appcompat.app.AppCompatActivity
import dajver.com.pointerpopupwindowexample.adapter.PointerPopupAdapter
import dajver.com.pointerpopupwindowexample.adapter.model.UsersModel
import dajver.com.pointerpopupwindowexample.popup.PointerPopupWindow
import kotlinx.android.synthetic.main.activity_main.*
import java.util.*

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        openPopup.setOnClickListener {
            val wm = getSystemService(Context.WINDOW_SERVICE) as WindowManager
            val width = wm.defaultDisplay.width

            val popupWindow = PointerPopupWindow(this, width)
            popupWindow.setPointerImageRes(R.mipmap.ic_popup_pointer)
            popupWindow.setAlignMode(PointerPopupWindow.AlignMode.CENTER_FIX)

            val usersModels = ArrayList<UsersModel>()
            usersModels.add(UsersModel("John Wick", "Lorem Ipsum is simply."))
            usersModels.add(UsersModel("David Hasselhoff", "It is a long established fact."))
            usersModels.add(UsersModel("Elizabeth Shaw", "Contrary to popular belief."))

            val adapter = PointerPopupAdapter(this, usersModels)
            val listView = ListView(this)
            listView.divider = ColorDrawable(resources.getColor(R.color.gray_darkest))
            listView.dividerHeight = 1
            listView.adapter = adapter
            popupWindow.contentView = listView
            popupWindow.showAsPointer(openPopup)
        }

        changeButtonPosition.setOnClickListener {
            val params = openPopup.layoutParams as RelativeLayout.LayoutParams
            params.setMargins((1..1099).random(), (1..499).random(), 10, 0) //substitute parameters for left, top, right, bottom
            openPopup.layoutParams = params
        }
    }
}
