package com.example.myapplication

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.liveData
import com.example.myapplication.data.api.RetrofitInterface
import com.example.myapplication.data.model.data_viz.DataViz
import com.example.myapplication.data.api.RetrofitInstance
import com.example.myapplication.databinding.ActivityDataVizBinding
import com.github.mikephil.charting.animation.Easing
import com.github.mikephil.charting.charts.BarChart
import com.github.mikephil.charting.charts.PieChart
import com.github.mikephil.charting.components.AxisBase
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.components.XAxis
import com.github.mikephil.charting.data.*
import com.github.mikephil.charting.formatter.PercentFormatter
import com.github.mikephil.charting.formatter.ValueFormatter
import kotlinx.android.synthetic.main.activity_data_viz.view.*
import retrofit2.Response


class DataVizActivity : AppCompatActivity() {
    private lateinit var binding: ActivityDataVizBinding
    private lateinit var dataViz: DataViz
    private val colors: ArrayList<Int> =
        arrayListOf(
            Color.parseColor("#309967"),
            Color.parseColor("#476567"),
            Color.parseColor("#890567"),
            Color.parseColor("#a35567"),
            Color.parseColor("#ff5f67"),
            Color.parseColor("#3ca567")
        )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDataVizBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)

        val retService = RetrofitInstance
            .getRetrofitInstance()
            .create(RetrofitInterface::class.java)
        val responseLiveData: LiveData<Response<DataViz>> = liveData {
            val response = retService.getDataViz()
            emit(response)
        }
        responseLiveData.observe(this, Observer {
            dataViz = it.body()!!
            setDataToAgeGroupChart(view.pie_chart_ageGroup)
            setDataToSocialClassChart(view.bar_chart_socialClass)
            setDataToCivilStatusChart(view.bar_chart_civilStatus)
        })
    }

    private fun setDataToCivilStatusChart(civilBarChart: BarChart) {
        val civilStatusEntries: ArrayList<BarEntry> = ArrayList()

        initCivilStatusChart(civilBarChart, dataViz.civil_status.labels)

        val civilStatusVal = dataViz.civil_status.values

        civilStatusVal.forEachIndexed { index, value ->
            civilStatusEntries.add(BarEntry(index.toFloat(), value.toFloat()))
        }

        val civilStatusBarDataSet = BarDataSet(civilStatusEntries, "")
        civilStatusBarDataSet.colors = colors

        // set the data and list of labels into chart
        val civilStatusBarData = BarData(civilStatusBarDataSet)
        civilBarChart.data = civilStatusBarData

        civilBarChart.invalidate()
    }

    private fun initCivilStatusChart(civilBarChart: BarChart, civilStatusLabel: List<String>) {
        val width: Int = civilBarChart.width
        val height: Int = civilBarChart.height

        val socialClassAxisX: XAxis = civilBarChart.xAxis
        // hide grid lines
        civilBarChart.axisLeft.setDrawGridLines(false)
        socialClassAxisX.setDrawGridLines(false)
        socialClassAxisX.setDrawAxisLine(false)
        // draw label on xAxis
        socialClassAxisX.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        socialClassAxisX.setDrawLabels(true)
        socialClassAxisX.granularity = 1f
        socialClassAxisX.labelRotationAngle = +90f

        //remove right y-axis
        civilBarChart.isEnabled = false

        //remove legend
        civilBarChart.legend.isEnabled = false

        // Description label
        civilBarChart.description.text = "Civil Status Distribution"
        civilBarChart.description.setPosition((width / 2).toFloat(), (height).toFloat())

        // add animation
        civilBarChart.animateY(1400, Easing.EaseInOutQuad)

        val civilStatusFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return civilStatusLabel[value.toInt()]
            }

        }
        socialClassAxisX.valueFormatter = civilStatusFormatter
    }


    private fun setDataToSocialClassChart(socialBarChart: BarChart) {
        // Social Class
        val socialClassEntries: ArrayList<BarEntry> = ArrayList<BarEntry>()
        val socialClassVal = dataViz.social_class.values
        socialClassVal.forEachIndexed { index, value ->
            socialClassEntries.add(BarEntry(index.toFloat(), value.toFloat()))
        }

        initSocialClassChart(socialBarChart, dataViz.social_class.labels)

        val socialClassBarDataSet = BarDataSet(socialClassEntries, "")
        socialClassBarDataSet.colors = colors

        // set the data and list of labels into chart
        val socialClassBarData = BarData(socialClassBarDataSet)
        socialBarChart.data = socialClassBarData

        socialBarChart.invalidate()
    }


    private fun initSocialClassChart(socialBarChart: BarChart, socialClassLabel: List<String>) {
        val width: Int = socialBarChart.width
        val height: Int = socialBarChart.height

        val socialClassAxisX: XAxis = socialBarChart.xAxis
        // hide grid lines
        socialBarChart.axisLeft.setDrawGridLines(false)
        socialClassAxisX.setDrawGridLines(false)
        socialClassAxisX.setDrawAxisLine(false)
        // draw label on xAxis
        socialClassAxisX.position = XAxis.XAxisPosition.BOTTOM_INSIDE
        socialClassAxisX.setDrawLabels(true)
        socialClassAxisX.granularity = 1f
        socialClassAxisX.labelRotationAngle = +90f

        //remove right y-axis
        socialBarChart.isEnabled = false

        //remove legend
        socialBarChart.legend.isEnabled = false

        // Description label
        socialBarChart.description.text = "Social Class Distribution"
        socialBarChart.description.setPosition((width / 2).toFloat(), (height).toFloat())

        // add animation
        socialBarChart.animateY(1400, Easing.EaseInOutQuad)

        val socialClassFormatter: ValueFormatter = object : ValueFormatter() {
            override fun getAxisLabel(value: Float, axis: AxisBase): String {
                return socialClassLabel[value.toInt()]
            }

        }
        socialClassAxisX.valueFormatter = socialClassFormatter
    }

    private fun initAgeGroupChart(pieChart: PieChart) {
        pieChart.setUsePercentValues(true);
        pieChart.description.text = ""
        //hollow pie chart
        pieChart.isDrawHoleEnabled = false
        pieChart.setTouchEnabled(false)
        pieChart.setDrawEntryLabels(false)
        //adding padding
        pieChart.setExtraOffsets(20f, 0f, 20f, 20f)
        pieChart.setUsePercentValues(true)
        pieChart.isRotationEnabled = false
        pieChart.setDrawEntryLabels(false)
        pieChart.legend.orientation = Legend.LegendOrientation.VERTICAL
        pieChart.legend.isWordWrapEnabled = true
    }

    private fun setDataToAgeGroupChart(pieChart: PieChart) {
        initAgeGroupChart(pieChart)
        val ageGroupEntries: ArrayList<PieEntry> = ArrayList()

        val ageGroupLabel = dataViz.age_group.labels
        val ageGroupVal = dataViz.age_group.values
        ageGroupLabel.zip(ageGroupVal).forEach { (label, value) ->
            ageGroupEntries.add(PieEntry(value.toFloat(), label))
        }

        val ageGroupDataSet = PieDataSet(ageGroupEntries, "")
        val ageGroupPieData = PieData(ageGroupDataSet)

        // In Percentage
        ageGroupPieData.setValueFormatter(PercentFormatter())
        ageGroupDataSet.sliceSpace = ageGroupVal.count().toFloat()
        ageGroupDataSet.colors = colors
        pieChart.data = ageGroupPieData;
        ageGroupPieData.setValueTextSize(15f)
        pieChart.setExtraOffsets(5f, 10f, 5f, 5f)
        pieChart.animateY(1400, Easing.EaseInOutQuad)

        //create hole in center
        pieChart.holeRadius = 58f
        pieChart.transparentCircleRadius = 61f
        pieChart.isDrawHoleEnabled = true
        pieChart.setHoleColor(Color.WHITE)

        //add text in center
        pieChart.setDrawCenterText(true);
        pieChart.centerText = "Age Group Distribution"

        pieChart.invalidate()
    }
}