package com.example.currencyexchange.ui.graph

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.example.currencyexchange.R
import com.example.currencyexchange.databinding.FragmentGraphBinding
import com.example.currencyexchange.network.fxhistory.model.FxHistoryData
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import java.util.*

class GraphFragment : Fragment() {

    lateinit var binding: FragmentGraphBinding
    lateinit var lineDataSet: LineDataSet
    lateinit var chart: LineChart
    private val graphViewModel: GraphViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.getParcelable<FxHistoryData>("fxData")?.let {
            graphViewModel.initHistoryData(it)
        }
        arguments?.getString("currency")?.let {
            graphViewModel.setCurrency(it)
        }

    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentGraphBinding.inflate(
            inflater, container, false
        )
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        chart = binding.chart
        createChart(chart)
        updateDataSet(graphViewModel.getHistoryData())
        chart.data = LineData(lineDataSet)
        chart.invalidate()

    }

    private fun updateDataSet(fxHistoryData: FxHistoryData?) {
        lineDataSet = LineDataSet(getEntries(fxHistoryData, graphViewModel.getCurrency()), "")
        lineDataSet.setDrawFilled(true)
        lineDataSet.fillDrawable = context?.getDrawable(R.drawable.blue_to_white_gradient)
        lineDataSet.valueTextColor = Color.BLACK
        lineDataSet.valueTextSize = 12f
        lineDataSet.label = ""
        lineDataSet.setDrawValues(false)
    }

    private fun createChart(lineChart: LineChart) {
        lineChart.axisLeft.setDrawGridLines(false)
        lineChart.axisLeft.setDrawLabels(false)
        lineChart.xAxis.setDrawGridLines(false)
        lineChart.xAxis.setDrawLabels(false)
        lineChart.axisRight.setDrawGridLines(false)
        lineChart.legend.isEnabled = false
        lineChart.axisRight.setDrawLabels(false)
        lineChart.description.isEnabled = false
        lineChart.isDoubleTapToZoomEnabled = false
        lineChart.isDragEnabled = false
        lineChart.setDrawBorders(false)
    }

    private fun getEntries(
        fxHistoryData: FxHistoryData?,
        currency: String
    ): ArrayList<Entry> {
        val lineEntries = ArrayList<Entry>()
        var counter = 0f
        fxHistoryData?.apply {
            rates.values.forEach { currencyMap ->
                currencyMap.get(currency)?.let {
                    counter++
                    lineEntries.add(Entry(counter, it.toFloat()))
                }
            }
        }
        return lineEntries
    }
}
