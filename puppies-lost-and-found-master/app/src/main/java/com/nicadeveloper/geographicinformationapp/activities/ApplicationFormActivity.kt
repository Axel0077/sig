package com.nicadeveloper.geographicinformationapp.activities

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Bundle
import android.provider.MediaStore
import android.support.v7.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_application_form.*
import android.widget.EditText
import java.text.SimpleDateFormat
import java.util.*
import android.widget.DatePicker
import android.app.DatePickerDialog
import android.R
import android.view.View


class ApplicationFormActivity : AppCompatActivity() {
    var lostDateDog: EditText? = null
    var calendar = Calendar.getInstance()

    private val SELECT_GALLERY = 2783

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(com.nicadeveloper.geographicinformationapp.R.layout.activity_application_form)

        //OnClickListener of the img placeholder
        imageButton.setOnClickListener {
            val goToGallery = Intent().apply {
                type = "image/*"
                action = Intent.ACTION_GET_CONTENT
            }
            startActivityForResult(goToGallery, SELECT_GALLERY)
        }

        //OnClickListener of the date Edit Text
        dog_date_edit_text.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@ApplicationFormActivity, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

        //OnClickListener of the date Button
        dateButton.setOnClickListener(object : View.OnClickListener {
            override fun onClick(view: View) {
                DatePickerDialog(
                    this@ApplicationFormActivity, date, calendar
                        .get(Calendar.YEAR), calendar.get(Calendar.MONTH),
                    calendar.get(Calendar.DAY_OF_MONTH)
                ).show()
            }
        })

    }


    //This metod do the convertion of the placeholder into the picked img
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {

        if (resultCode == Activity.RESULT_OK && requestCode == SELECT_GALLERY) {

            if (data != null) {

                val path = data.data.toString()
                val bitmap = BitmapFactory
                    .decodeStream(
                        this.contentResolver
                            .openInputStream(Uri.parse(path))
                    )

                imageButton.setImageBitmap(bitmap)
            }
        }

        super.onActivityResult(requestCode, resultCode, data)
    }


//Set the calendar
    var date: DatePickerDialog.OnDateSetListener =
        DatePickerDialog.OnDateSetListener { view, year, monthOfYear, dayOfMonth ->
            // TODO Auto-generated method stub
            calendar.set(Calendar.YEAR, year)
            calendar.set(Calendar.MONTH, monthOfYear)
            calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
            actualizarInput()
        }


//Update the edit text with the selected date
    private fun actualizarInput() {
        val formatoDeFecha = "dd/MM/yy" //In which you need put here
        val sdf = SimpleDateFormat(formatoDeFecha, Locale.US)

        lostDateDog = dog_date_edit_text
        dog_date_edit_text.setText(sdf.format(calendar.getTime()))
    }
}