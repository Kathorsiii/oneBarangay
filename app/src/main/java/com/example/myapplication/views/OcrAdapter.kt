package com.example.myapplication.views

import android.annotation.SuppressLint
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.example.myapplication.R
import com.example.myapplication.data.model.ocr.FamilyItem
import kotlinx.android.synthetic.main.ocr_family_form.view.*

class OcrAdapter(
    private val familyList: List<FamilyItem>,
    private val clickListener: (FamilyItem) -> Unit,
) : RecyclerView.Adapter<OcrAdapter.OcrViewHolder>() {

    override fun onCreateViewHolder(
        parent: ViewGroup,
        viewType: Int,
    ): OcrViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(
            R.layout.ocr_family_form, parent, false
        )

        return OcrViewHolder(view)
    }

    override fun onBindViewHolder(holder: OcrViewHolder, position: Int) {
        holder.bind(familyList[position], clickListener, position)
    }

    override fun getItemCount(): Int = familyList.size

    class OcrViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        @SuppressLint("SetTextI18n")
        fun bind(family: FamilyItem, clickListener: (FamilyItem) -> Unit, position: Int) {
            if (position == 0) {
                itemView.family_member.text = "Family Head"
            } else {
                itemView.family_member.text = "Family Member $position"
            }

            itemView.first_name.setText(family.first_name.text)
            itemView.material_firstName.helperText =
                "Confidence Level: ${family.first_name.confidence}"
            itemView.first_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.first_name.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.middle_name.setText(family.middle_name.text)
            itemView.material_middleName.helperText =
                "Confidence Level: ${family.middle_name.confidence}"
            itemView.middle_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.middle_name.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.last_name.setText(family.last_name.text)
            itemView.material_lastName.helperText =
                "Confidence Level: ${family.last_name.confidence}"
            itemView.last_name.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.last_name.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.ext.setText(family.ext.text)
            itemView.material_extension.helperText =
                "Confidence Level: ${family.ext.confidence}"
            itemView.ext.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.ext.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.birth_place.setText(family.birth_place.text)
            itemView.material_birthPlace.helperText =
                "Confidence Level: ${family.birth_place.confidence}"
            itemView.birth_place.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.birth_place.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.birth_date.setText(family.date_of_birth.text)
            itemView.material_birthDate.helperText =
                "Confidence Level: ${family.date_of_birth.confidence}"
            itemView.birth_date.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.date_of_birth.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.gender.setText(family.sex.text)
            itemView.material_gender.helperText =
                "Confidence Level: ${family.sex.confidence}"
            itemView.gender.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.sex.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.civil_status.setText(family.civil_status.text)
            itemView.material_civilStatus.helperText =
                "Confidence Level: ${family.civil_status.confidence}"
            itemView.civil_status.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.civil_status.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.citizenship.setText(family.citizenship.text)
            itemView.material_citizenship.helperText =
                "Confidence Level: ${family.citizenship.confidence}"
            itemView.citizenship.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.citizenship.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.monthly_income.setText(family.monthly_income.text)
            itemView.material_monthlyIncome.helperText =
                "Confidence Level: ${family.monthly_income.confidence}"
            itemView.monthly_income.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.monthly_income.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.remarks.setText(family.remarks.text)
            itemView.material_remarks.helperText =
                "Confidence Level: ${family.remarks.confidence}"
            itemView.remarks.addTextChangedListener(object : TextWatcher {
                override fun afterTextChanged(s: Editable?) {
                    family.remarks.text = s.toString()
                }

                override fun beforeTextChanged(
                    s: CharSequence?,
                    start: Int,
                    count: Int,
                    after: Int,
                ) {
                }

                override fun onTextChanged(
                    s: CharSequence?,
                    start: Int,
                    before: Int,
                    count: Int,
                ) {
                }
            })

            itemView.setOnClickListener {
                clickListener(family)
            }
        }
    }
}