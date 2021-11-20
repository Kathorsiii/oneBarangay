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

            family.first_name?.let {
                itemView.first_name.setText(it.text)
                itemView.material_firstName.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.first_name.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.middle_name?.let {
                itemView.middle_name.setText(it.text)
                itemView.material_middleName.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.middle_name.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.last_name?.let {
                itemView.last_name.setText(it.text)
                itemView.material_lastName.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.last_name.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.ext?.let {
                itemView.ext.setText(it.text)
                itemView.material_extension.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.ext.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.birth_place?.let {
                itemView.birth_place.setText(it.text)
                itemView.material_birthPlace.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.birth_place.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.date_of_birth?.let {
                itemView.birth_date.setText(it.text)
                itemView.material_birthDate.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.birth_date.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.sex?.let {
                itemView.gender.setText(it.text)
                itemView.material_gender.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.gender.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.civil_status?.let {
                itemView.civil_status.setText(it.text)
                itemView.material_civilStatus.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.civil_status.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.citizenship?.let {
                itemView.citizenship.setText(it.text)
                itemView.material_citizenship.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.citizenship.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.monthly_income?.let {
                itemView.monthly_income.setText(it.text)
                itemView.material_monthlyIncome.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.monthly_income.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            family.remarks?.let {
                itemView.remarks.setText(it.text)
                itemView.material_remarks.helperText =
                    "Confidence Level: ${it.confidence}"
                itemView.remarks.addTextChangedListener(object : TextWatcher {
                    override fun afterTextChanged(s: Editable?) {
                        it.text = s.toString()
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
            }

            itemView.setOnClickListener {
                clickListener(family)
            }
        }
    }
}