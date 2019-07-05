package dajver.com.pointerpopupwindowexample.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import dajver.com.pointerpopupwindowexample.R
import dajver.com.pointerpopupwindowexample.adapter.model.UsersModel
import kotlinx.android.synthetic.main.item_users.view.*
import java.util.*

class PointerPopupAdapter(context: Context, private val usersModels: ArrayList<UsersModel>) : ArrayAdapter<UsersModel>(context, 0, usersModels) {

    override fun getCount(): Int {
        return usersModels.size
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup): View {
        val view = LayoutInflater.from(context).inflate(R.layout.item_users, null)

        val holder = PopupTitleViewHolder(view)
        holder.bind(usersModels[position])

        return view
    }

    inner class PopupTitleViewHolder(private var view: View) {

        fun bind(usersModel: UsersModel) {
            view.name.text = usersModel.title
            view.content.text = usersModel.description
        }
    }
}