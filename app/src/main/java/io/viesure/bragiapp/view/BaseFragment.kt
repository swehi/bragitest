package io.viesure.bragiapp.view

import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.fragment.app.Fragment
import io.viesure.bragiapp.R
import io.viesure.bragiapp.databinding.ToastPopupBinding
import io.viesure.bragiapp.data.model.ConnectionState


open class BaseFragment : Fragment() {

    fun showNetworkPopup(connectionState: ConnectionState) {
        val binding = ToastPopupBinding.inflate(LayoutInflater.from(activity))
        val view: View = binding.root
        val toast = Toast(activity)
        binding.popupText.text = connectionState.name
        toast.view = view
        toast.setGravity(Gravity.TOP, 0, 50)
        toast.duration = Toast.LENGTH_SHORT
        toast.show()
    }

    fun showMessageSentPopup(connectionState: ConnectionState) {
        if (connectionState == ConnectionState.CONNECTION_ESTABILISHED) {
            Toast.makeText(activity, R.string.message_sent, Toast.LENGTH_SHORT).show()
        }
    }



}

