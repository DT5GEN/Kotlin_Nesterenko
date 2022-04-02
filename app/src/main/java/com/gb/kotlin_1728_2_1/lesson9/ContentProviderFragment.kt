package com.gb.kotlin_1728_2_1.lesson9

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.provider.ContactsContract
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.app.AlertDialog
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.gb.kotlin_1728_2_1.databinding.FragmentContentProviderBinding



class ContentProviderFragment : Fragment() {


    var _binding: FragmentContentProviderBinding? = null
    private val binding: FragmentContentProviderBinding
        get() {
            return _binding!!
        }


    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {

        _binding = FragmentContentProviderBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        checkPermission()


    }


    private fun checkPermission() {
        context?.let {
            when {
                ContextCompat.checkSelfPermission(
                    it,
                    Manifest.permission.READ_CONTACTS
                ) == PackageManager.PERMISSION_GRANTED -> {
                    getContacts()
                }

                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showDialog()
                }
                else -> {
                    myRequestPermission()
                }
            }
        }
    }

    val REQUEST_CODE = 999
    private fun myRequestPermission() {
        requestPermissions(arrayOf(Manifest.permission.READ_CONTACTS), REQUEST_CODE)

    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        // super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CODE) {
            when {
                (grantResults[0] == PackageManager.PERMISSION_GRANTED) -> {
                    getContacts()
                }
                shouldShowRequestPermissionRationale(Manifest.permission.READ_CONTACTS) -> {
                    showDialog()
                }
                else -> {
                    Log.d(
                        "TAG",
                        "onRequestPermissionsResult() called with: requestCode = $requestCode, permissions = $permissions, grantResults = $grantResults"
                    )
                }
            }
        }

    }



    private fun getContacts() {
        context?.let {
            val contentResolver = it.contentResolver  // некий агрегатор всех контент провайдеров
            val cursor = contentResolver.query(
                ContactsContract.Contacts.CONTENT_URI,
                null,
                null,
                null,
                ContactsContract.Contacts.DISPLAY_NAME + " ASC"
            )
            cursor?.let { cursorCaretta ->
                 for (i in 0 until cursorCaretta.count) {
                    cursorCaretta.moveToPosition(i)
                    val name = cursorCaretta.getString(cursorCaretta.getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME))
                    addView(name)
                }
            }
            cursor?.close()
        }
    }


    private fun addView(name:String){
binding.containerForContacts.addView(TextView(requireContext()).apply {
    text = name
    textSize = 30F
})
    }

    private fun showDialog() {
        AlertDialog.Builder(requireContext())
            .setTitle("Доступ к контактам")
            .setMessage("Объяснение")
            .setPositiveButton("Предоставить доступ") { _, _ ->
                myRequestPermission()
            }
            .setNegativeButton("Не надо") { dialog, _ -> dialog.dismiss() }
            .create()
            .show()
    }

    companion object {

        @JvmStatic
        fun newInstance() =
            ContentProviderFragment()

    }
}